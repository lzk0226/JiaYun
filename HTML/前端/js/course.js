// 存储所有课程数据（从后端获取）
let allCourses = [];
let currentCourses = [];

// 页面加载时初始化课程列表
document.addEventListener('DOMContentLoaded', () => {
  loadAllCourses(); // 一次性加载所有课程
});

// 从后端加载所有课程（只调用一次）
async function loadAllCourses() {
  try {
    const response = await fetch('http://localhost:8080/user/courses');
    const result = await response.json();

    if (result.code === 200) {
      allCourses = result.data;
      currentCourses = [...allCourses]; // 复制一份用于显示
      renderCourses(currentCourses);
      console.log('成功加载课程数据:', allCourses.length, '条');
    } else {
      console.error('课程加载失败:', result.msg);
      modal.error('加载课程失败：' + result.msg, '加载失败');
    }
  } catch (error) {
    console.error('接口调用出错:', error);
    modal.error('服务器连接失败，请检查后端服务！', '网络错误');
  }
}

// 渲染课程列表
function renderCourses(courses) {
  const grid = document.getElementById('coursesGrid');

  if (courses.length === 0) {
    grid.innerHTML = `
      <div class="empty-state">
        <i class="fas fa-book-open"></i>
        <p>暂无符合条件的课程</p>
      </div>
    `;
    return;
  }

  grid.innerHTML = courses.map(course => `
    <div class="course-card">
      <div class="course-header badge-${course.badge}">
        <div class="course-badge">${course.badge}</div>
        <h3 class="course-title">${course.name}</h3>
        <p class="course-subtitle">${course.subtitle}</p>
      </div>
      <div class="course-body">
        <div class="course-info">
          <div class="info-row">
            <i class="fas fa-clock"></i>
            <span>培训时长：${course.duration}课时</span>
          </div>
          <div class="info-row">
            <i class="fas fa-calendar"></i>
            <span>完成周期：${course.period}</span>
          </div>
          <div class="info-row">
            <i class="fas fa-users"></i>
            <span>已报名：${course.studentsCount}人</span>
          </div>
        </div>
        <div class="course-features">
          ${course.features.map(f => `<span class="feature-tag">${f}</span>`).join('')}
        </div>
        <div class="course-footer">
          <div>
            <div class="course-price">¥${course.price}</div>
            <div class="price-label">培训费用</div>
          </div>
          <button class="btn-select" onclick="selectCourse(${course.id})">
            <i class="fas fa-check"></i> 立即报名
          </button>
        </div>
      </div>
    </div>
  `).join('');
}

// 前端本地筛选课程
function filterCourses() {
  const subject = document.getElementById('subjectFilter').value;
  const type = document.getElementById('typeFilter').value;
  const sort = document.getElementById('sortFilter').value;

  console.log('筛选条件 - 科目:', subject, '类型:', type, '排序:', sort);

  // 从所有课程开始筛选
  let filtered = [...allCourses];

  // 科目筛选
  if (subject) {
    filtered = filtered.filter(course => {
      if (subject === 'all') {
        // 科目二三联报：is_combined = true
        return course.isCombined === true;
      } else {
        // 单科目筛选
        const subjectMap = {
          'subject1': 1,
          'subject2': 2,
          'subject3': 3,
          'subject4': 4
        };
        const subjectId = subjectMap[subject];

        // 匹配单科课程 或 联报课程包含该科目
        if (course.isCombined) {
          // 联报课程：检查 subjectsName 是否包含对应科目
          const subjectNames = {
            1: '科目一',
            2: '科目二',
            3: '科目三',
            4: '科目四'
          };
          return course.subjectsName &&
            course.subjectsName.includes(subjectNames[subjectId]);
        } else {
          // 单科课程：直接匹配 subject_id
          return course.subjectId === subjectId;
        }
      }
    });
  }

  // 类型筛选
  if (type) {
    filtered = filtered.filter(c => c.type === type);
  }

  // 排序
  if (sort === 'price-asc') {
    filtered.sort((a, b) => a.price - b.price);
  } else if (sort === 'price-desc') {
    filtered.sort((a, b) => b.price - a.price);
  } else if (sort === 'popular') {
    filtered.sort((a, b) => b.popular - a.popular);
  }
  // default: 保持原有顺序（后端已按 popular DESC 排序）

  console.log('筛选结果:', filtered.length, '条课程');
  currentCourses = filtered;
  renderCourses(currentCourses);
}

// 选择课程（报名）- 调用真实后端接口
async function selectCourse(courseId) {
  const course = allCourses.find(c => c.id === courseId);

  if (!course) {
    modal.error('课程信息未找到', '错误');
    return;
  }

  // 确认报名
  const confirmed = await modal.confirmDialog(
    `确定要报名 ${course.name} 吗？\n\n费用：¥${course.price}\n时长：${course.duration}课时`,
    '确认报名'
  );

  if (!confirmed) {
    return;
  }

  // 获取token (改用sessionStorage与profile.js保持一致)
  const token = sessionStorage.getItem('token');
  if (!token) {
    await modal.warning('请先登录后再报名课程！', '未登录');
    window.location.href = '/login.html';
    return;
  }

  try {
    // 调用后端报名接口
    const response = await fetch(`http://localhost:8080/user/courses/enroll?courseId=${courseId}`, {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    });

    const result = await response.json();

    if (result.code === 200) {
      // 报名成功
      await modal.success(
        `您已成功报名 ${course.name}\n请前往"车辆预约"或"教练选择"页面预约课程。`,
        '报名成功'
      );

      // 刷新课程列表（更新报名人数）
      await loadAllCourses();

    } else if (result.code === 401) {
      // Token过期
      await modal.warning('登录已过期，请重新登录！', '登录过期');
      sessionStorage.removeItem('token');
      window.location.href = '/login.html';

    } else {
      // 报名失败（包括科目冲突）
      await modal.error(
        result.message || result.msg || '未知错误',
        '报名失败'
      );
    }

  } catch (error) {
    console.error('报名接口调用失败:', error);
    modal.error('网络错误，报名失败，请稍后重试！', '网络错误');
  }
}

// 加载导航栏
fetch('/component/navbar.html')
  .then(response => response.text())
  .then(data => {
    const placeholder = document.getElementById('navbar-placeholder');
    const tempContainer = document.createElement('div');
    tempContainer.innerHTML = data;
    const nav = tempContainer.querySelector('nav');
    if (nav) placeholder.appendChild(nav);
    const style = tempContainer.querySelector('style');
    if (style) document.head.appendChild(style);
    const scriptContent = tempContainer.querySelector('script');
    if (scriptContent) {
      const newScript = document.createElement('script');
      newScript.textContent = scriptContent.textContent;
      document.body.appendChild(newScript);
    }
  })
  .catch(error => console.error('导航栏加载失败:', error));