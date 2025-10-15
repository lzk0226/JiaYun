// ==================== API 配置 ====================
const API_BASE_URL = 'http://localhost:8080/user';

// ==================== 全局数据 ====================
let userData = {};
let progressData = { stats: [], timeline: [] };
let coursesData = [];
let examsData = [];
let currentAvatar = null; // 存储当前头像

// ==================== 工具函数 ====================

// 获取 Token（与导航栏保持一致）
function getToken() {
  return sessionStorage.getItem('token');
}

// API 请求封装
async function apiRequest(url, options = {}) {
  const token = getToken();

  if (!token) {
    alert('请先登录');
    window.location.href = 'login.html';
    return null;
  }

  const defaultOptions = {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    }
  };

  try {
    console.log('发送请求:', url, options);

    const response = await fetch(url, { ...defaultOptions, ...options });

    console.log('响应状态:', response.status);

    // 检查响应类型
    const contentType = response.headers.get('content-type');
    if (!contentType || !contentType.includes('application/json')) {
      console.error('服务器返回的不是 JSON 格式');
      throw new Error('服务器响应格式错误');
    }

    const result = await response.json();
    console.log('响应数据:', result);

    if (result.code === 200) {
      return result.data;
    } else {
      alert(result.message || result.msg || '请求失败');
      if (result.message && result.message.includes('Token')) {
        sessionStorage.clear();
        window.location.href = 'login.html';
      }
      return null;
    }
  } catch (error) {
    console.error('API请求失败:', error);
    alert('网络请求失败，请检查后端服务是否启动');
    return null;
  }
}

// 格式化日期
function formatDate(dateString) {
  if (!dateString) return '未设置';
  const date = new Date(dateString);
  return date.toLocaleDateString('zh-CN');
}

// 格式化日期时间
function formatDateTime(dateString) {
  if (!dateString) return '未安排';
  const date = new Date(dateString);
  return date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  }).replace(/\//g, '-');
}

// ==================== 数据加载 ====================

// 加载个人中心数据
async function loadProfileData() {
  console.log('开始加载个人中心数据...');

  const data = await apiRequest(`${API_BASE_URL}/profile`);

  if (data) {
    console.log('成功获取数据:', data);

    userData = data.userData || {};
    progressData.stats = data.stats || [];
    progressData.timeline = data.timeline || [];
    coursesData = data.courses || [];
    examsData = data.exams || [];

    // 保存当前头像
    currentAvatar = userData.avatar || null;

    // 渲染各个模块
    renderInfo();
    renderProgress();
    renderCourses();
    renderExams();

    console.log('页面渲染完成');
  } else {
    console.error('获取数据失败');
  }
}

// ==================== 渲染函数 ====================

// 处理头像URL
function getAvatarUrl(avatar) {
  if (!avatar) {
    return "assets/img/default-avatar.jpg";
  }

  // 如果已经是 base64 格式（包含 data:image），直接使用
  if (avatar.startsWith('data:image')) {
    return avatar;
  }
  // 如果是纯 base64（不包含前缀），添加前缀
  else if (avatar.length > 100 && !avatar.startsWith('http')) {
    return `data:image/png;base64,${avatar}`;
  }
  // 否则当作普通 URL 使用
  else {
    return avatar;
  }
}

// 渲染个人信息
function renderInfo() {
  const avatarUrl = getAvatarUrl(userData.avatar);

  // 更新顶部信息（包括头像上传功能）
  document.getElementById('avatarImg').src = avatarUrl;
  document.getElementById('userName').textContent = userData.name || '未设置';
  document.getElementById('userId').innerHTML = `<i class="fas fa-id-card"></i> 学员编号：${userData.userId || '尚未有编号'}`;
  document.getElementById('registerDate').innerHTML = `<i class="fas fa-calendar"></i> 注册时间：${formatDate(userData.registerDate)}`;
  document.getElementById('license').innerHTML = `<i class="fas fa-car"></i> 驾照类型：${userData.licenseType || '尚未有驾照'}`;

  // 渲染表单（不包含头像上传）
  const infoDiv = document.getElementById('info');
  infoDiv.innerHTML = `
    <div class="info-card">
      <h3 class="card-title"><i class="fas fa-user-edit"></i> 基本信息</h3>
      
      <div class="form-grid">
        <div class="form-group">
          <label>姓名</label>
          <input type="text" id="name" value="${userData.name || ''}" placeholder="请输入姓名">
        </div>
        <div class="form-group">
          <label>性别</label>
          <select id="gender">
            <option value="male" ${userData.gender === "male" ? "selected" : ""}>男</option>
            <option value="female" ${userData.gender === "female" ? "selected" : ""}>女</option>
          </select>
        </div>
        <div class="form-group">
          <label>出生日期</label>
          <input type="date" id="birthdate" value="${userData.birthdate || ''}">
        </div>
        <div class="form-group">
          <label>手机号码</label>
          <input type="tel" id="phone" value="${userData.phone || ''}" placeholder="请输入手机号">
        </div>
        <div class="form-group">
          <label>身份证号</label>
          <input type="text" id="idcard" value="${userData.idcard || ''}" placeholder="请输入身份证号">
        </div>
      </div>
      <div class="form-group" style="margin-top:1.5rem;">
        <label>联系地址</label>
        <textarea id="address" placeholder="请输入联系地址">${userData.address || ''}</textarea>
      </div>
      <div class="btn-group">
        <button class="btn btn-primary" onclick="saveInfo()">
          <i class="fas fa-save"></i> 保存修改
        </button>
        <button class="btn btn-secondary" onclick="resetInfo()">
          <i class="fas fa-undo"></i> 重置
        </button>
      </div>
    </div>
  `;
}

// 渲染学习进度
function renderProgress() {
  const progressDiv = document.getElementById('progress');

  // 渲染统计卡片
  const statsHtml = progressData.stats.map(stat => `
    <div class="stat-card">
      <div class="stat-icon ${stat.color}"><i class="fas ${stat.icon}"></i></div>
      <div class="stat-value">${stat.value}</div>
      <div class="stat-label">${stat.label}</div>
    </div>
  `).join('');

  // 渲染时间线
  const timelineHtml = progressData.timeline.length > 0
    ? progressData.timeline.map(item => `
      <div class="timeline-item">
        <div class="timeline-dot ${item.status === "进行中" ? "current" : "completed"}"></div>
        <div class="timeline-content">
          <div class="timeline-header">
            <h4 class="timeline-title">${item.title}</h4>
            <span class="timeline-status ${item.status === "进行中" ? "status-progress" : "status-completed"}">${item.status}</span>
          </div>
          <div class="timeline-details">
            <p>考试时间：${formatDateTime(item.time)}</p>
            ${item.score ? `<p>考试成绩：${item.score}分</p>` : ''}
            ${item.completedLessons ? `<p>已完成课时：${item.completedLessons}</p>` : ''}
            ${item.progress ? `<p>学习进度：${item.progress}</p>` : ''}
            ${item.location ? `<p>考试地点：${item.location}</p>` : ''}
          </div>
        </div>
      </div>
    `).join('')
    : '<p style="text-align:center;color:#999;padding:2rem;">暂无学习记录</p>';

  progressDiv.innerHTML = `
    <div class="stats-grid">
      ${statsHtml}
    </div>
    <div class="info-card">
      <h3 class="card-title"><i class="fas fa-route"></i> 学习时间线</h3>
      <div class="progress-timeline">
        ${timelineHtml}
      </div>
    </div>
  `;
}

// 渲染课程
function renderCourses() {
  const coursesDiv = document.getElementById('courses');

  const coursesHtml = coursesData.length > 0
    ? coursesData.map(course => `
      <div class="course-item">
        <div class="course-info">
          <h4>${course.title}</h4>
          <div class="course-meta">
            <i class="fas fa-user"></i> 教练：${course.coach || '待分配'} |
            <i class="fas fa-clock"></i> ${course.lessons}课时 |
            <i class="fas fa-calendar"></i> ${formatDate(course.start)} 开课
          </div>
        </div>
        <div class="course-progress">
          <div class="progress-bar">
            <div class="progress-fill" style="width: ${course.progress}%"></div>
          </div>
          <div class="progress-text">
            ${course.progress === 100
        ? `${course.lessons} 课时 (已完成)`
        : `学习进度 ${course.progress}%`}
          </div>
        </div>
      </div>
    `).join('')
    : '<p style="text-align:center;color:#999;padding:2rem;">暂无报名课程</p>';

  coursesDiv.innerHTML = `
    <div class="info-card">
      <h3 class="card-title"><i class="fas fa-book-open"></i> 已报名课程</h3>
      <div class="course-list">
        ${coursesHtml}
      </div>
    </div>
  `;
}

// 渲染考试安排
function renderExams() {
  const examsDiv = document.getElementById('exams');

  const examsHtml = examsData.length > 0
    ? examsData.map(exam => `
      <div class="course-item" style="border: ${exam.status === "待考试" ? "2px solid #3b82f6" : "1px solid #ddd"};">
        <div class="course-info">
          <h4>${exam.title}</h4>
          <div class="course-meta">
            <i class="fas fa-map-marker-alt"></i> ${exam.place || '待确定'} |
            <i class="fas fa-clock"></i> ${formatDateTime(exam.time)} |
            <span class="timeline-status ${exam.status === "待考试" ? "status-progress" : "status-completed"}" style="margin-left:0.5rem;">${exam.status}</span>
          </div>
        </div>
        ${exam.status === "待考试"
        ? `<button class="btn btn-primary" onclick="showExamInfo('${exam.title}')">
              <i class="fas fa-info-circle"></i> 考试须知
            </button>`
        : ""}
      </div>
    `).join('')
    : '<p style="text-align:center;color:#999;padding:2rem;">暂无考试安排</p>';

  examsDiv.innerHTML = `
    <div class="info-card">
      <h3 class="card-title"><i class="fas fa-calendar-alt"></i> 考试安排</h3>
      <div class="course-list">
        ${examsHtml}
      </div>
    </div>
  `;
}

// ==================== 交互函数 ====================

// 切换标签页
function switchTab(tabName) {
  document.querySelectorAll('.tab-btn').forEach(btn => btn.classList.remove('active'));
  document.querySelectorAll('.tab-content').forEach(content => content.classList.remove('active'));

  event.target.closest('.tab-btn').classList.add('active');
  document.getElementById(tabName).classList.add('active');
}

// 处理头像上传（顶部头像区域）- 单独上传头像
function handleAvatarUpload() {
  console.log('开始处理头像上传...');

  const input = document.createElement('input');
  input.type = 'file';
  input.accept = 'image/*';

  input.onchange = async (e) => {
    const file = e.target.files[0];
    if (!file) {
      console.log('未选择文件');
      return;
    }

    console.log('选择的文件:', file.name, file.size, file.type);

    // 验证文件大小（2MB）
    if (file.size > 2 * 1024 * 1024) {
      alert('图片大小不能超过2MB');
      return;
    }

    // 验证文件类型
    if (!file.type.startsWith('image/')) {
      alert('请上传图片文件');
      return;
    }

    // 显示加载提示
    const avatarImg = document.getElementById('avatarImg');
    const originalSrc = avatarImg.src;

    console.log('开始读取文件...');

    // 转换为base64
    const reader = new FileReader();
    reader.onload = async (event) => {
      const base64String = event.target.result;

      console.log('Base64转换完成，长度:', base64String.length);
      console.log('Base64前缀:', base64String.substring(0, 50));

      // 立即更新头像显示
      avatarImg.src = base64String;

      try {
        console.log('准备发送头像更新请求...');

        // 只上传头像字段到后端
        const result = await apiRequest(`${API_BASE_URL}/profile/avatar`, {
          method: 'PUT',
          body: JSON.stringify({
            avatar: base64String
          })
        });

        console.log('头像更新响应:', result);

        if (result !== null) {
          alert('头像上传成功！');
          currentAvatar = base64String;
          // 重新加载数据以同步
          await loadProfileData();
        } else {
          // 上传失败，恢复原头像
          console.error('头像上传失败');
          avatarImg.src = originalSrc;
        }
      } catch (error) {
        console.error('头像上传异常:', error);
        alert('头像上传失败，请重试');
        avatarImg.src = originalSrc;
      }
    };

    reader.onerror = (error) => {
      console.error('文件读取失败:', error);
      alert('文件读取失败');
    };

    reader.readAsDataURL(file);
  };

  input.click();
}

// 保存个人信息（不包含头像）
async function saveInfo() {
  const userInfo = {
    name: document.getElementById('name').value.trim(),
    gender: document.getElementById('gender').value,
    birthdate: document.getElementById('birthdate').value,
    phone: document.getElementById('phone').value.trim(),
    idcard: document.getElementById('idcard').value.trim(),
    //licenseType: document.getElementById('licenseType').value,
    address: document.getElementById('address').value.trim()
    // 不包含 avatar 字段，避免清空头像
  };

  // 基本验证
  if (!userInfo.name) {
    alert('姓名不能为空');
    return;
  }

  if (!userInfo.phone) {
    alert('手机号不能为空');
    return;
  }

  // 手机号格式验证
  if (!/^1[3-9]\d{9}$/.test(userInfo.phone)) {
    alert('请输入正确的手机号码');
    return;
  }

  // 身份证格式验证（如果填写了）
  if (userInfo.idcard && !/^\d{17}[\dXx]$/.test(userInfo.idcard)) {
    alert('请输入正确的身份证号码');
    return;
  }

  console.log('提交的信息:', userInfo);

  const result = await apiRequest(`${API_BASE_URL}/profile`, {
    method: 'PUT',
    body: JSON.stringify(userInfo)
  });

  if (result !== null) {
    alert('个人信息保存成功！');
    // 重新加载数据
    await loadProfileData();
  }
}

// 重置信息
function resetInfo() {
  if (confirm('确定要重置所有修改吗？')) {
    renderInfo();
  }
}

// 显示考试须知
function showExamInfo(examTitle) {
  alert(`${examTitle} 考试须知\n\n1. 请提前30分钟到达考场\n2. 携带身份证原件\n3. 保持良好心态\n4. 遵守考场纪律`);
}

// ==================== 初始化 ====================

// 页面加载完成后初始化
document.addEventListener('DOMContentLoaded', async () => {
  console.log('页面加载完成，开始初始化...');

  // 检查登录状态
  const token = getToken();
  if (!token) {
    alert('请先登录');
    window.location.href = 'login.html';
    return;
  }

  console.log('Token存在，开始加载数据');

  // 加载个人中心数据
  await loadProfileData();

  // 绑定顶部头像上传事件
  const avatarUploadDiv = document.querySelector('.avatar-upload');
  if (avatarUploadDiv) {
    console.log('绑定头像上传事件');
    avatarUploadDiv.onclick = handleAvatarUpload;
  } else {
    console.warn('未找到头像上传元素');
  }

  // 加载导航栏
  fetch('/component/navbar.html')
    .then(response => response.text())
    .then(data => {
      const placeholder = document.getElementById('navbar-placeholder');
      if (!placeholder) return;

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
});

// 将函数暴露到全局作用域，供 HTML 调用
window.switchTab = switchTab;
window.saveInfo = saveInfo;
window.resetInfo = resetInfo;
window.showExamInfo = showExamInfo;
window.handleAvatarUpload = handleAvatarUpload;