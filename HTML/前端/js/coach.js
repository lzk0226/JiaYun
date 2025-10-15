// 存储所有教练数据（从后端获取）
let allCoaches = [];
let currentCoaches = [];
let selectedCoachData = null;
let availableCourses = []; // 可选择教练的课程列表

// 页面加载时初始化
document.addEventListener('DOMContentLoaded', () => {
  loadAllCoaches();
});

// 从后端加载所有教练（只调用一次）
async function loadAllCoaches() {
  try {
    const response = await fetch('http://localhost:8080/user/coaches');
    const result = await response.json();

    if (result.code === 200) {
      allCoaches = result.data.map(coach => ({
        ...coach,
        students: coach.studentsCount,
        reviews: coach.reviewsCount,
        subjects: coach.subjectsName ? coach.subjectsName.split(',') : []
      }));
      currentCoaches = [...allCoaches];
      renderCoaches(currentCoaches);
      console.log('成功加载教练数据:', allCoaches.length, '条');
    } else {
      console.error('教练加载失败:', result.message);
      showCustomAlert('加载教练失败：' + result.message, 'error');
    }
  } catch (error) {
    console.error('接口调用出错:', error);
    showCustomAlert('服务器连接失败！', 'error');
  }
}

// 从后端加载教练详情
async function loadCoachDetail(coachId) {
  try {
    const response = await fetch(`http://localhost:8080/user/coaches/${coachId}`);
    const result = await response.json();

    if (result.code === 200) {
      const coach = result.data;
      coach.students = coach.studentsCount;
      coach.reviewsList = (result.data.reviews || []).map(review => ({
        name: review.studentName,
        date: review.reviewDate,
        rating: review.rating,
        text: review.content
      }));
      coach.reviews = coach.reviewsCount;
      coach.subjects = coach.subjectsName ? coach.subjectsName.split(',') : [];
      return coach;
    } else {
      console.error('教练详情加载失败:', result.message);
      return null;
    }
  } catch (error) {
    console.error('接口调用出错:', error);
    return null;
  }
}

// 加载学生的可选课程列表
async function loadAvailableCourses() {
  const token = sessionStorage.getItem('token');
  if (!token) {
    showCustomAlert('请先登录', 'warning');
    setTimeout(() => {
      window.location.href = 'login.html';
    }, 1500);
    return null;
  }

  try {
    const response = await fetch('http://localhost:8080/user/coaches/my-courses', {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });
    const result = await response.json();

    if (result.code === 200) {
      // 过滤出没有教练的课程（coachId为null）
      const coursesWithoutCoach = result.data.filter(course => course.coachId === null);
      return coursesWithoutCoach;
    } else if (result.code === 401) {
      showCustomAlert('登录已过期，请重新登录', 'warning');
      setTimeout(() => {
        window.location.href = 'login.html';
      }, 1500);
      return null;
    } else {
      showCustomAlert('获取课程列表失败：' + result.message, 'error');
      return null;
    }
  } catch (error) {
    console.error('接口调用出错:', error);
    showCustomAlert('服务器连接失败！', 'error');
    return null;
  }
}

// 渲染教练列表
function renderCoaches(coaches) {
  const grid = document.getElementById('coachesGrid');

  if (coaches.length === 0) {
    grid.innerHTML = `
      <div class="empty-state">
        <i class="fas fa-user-slash"></i>
        <p>暂无符合条件的教练</p>
      </div>
    `;
    return;
  }

  grid.innerHTML = coaches.map(coach => `
    <div class="coach-card" onclick="showCoachDetail(${coach.id})">
      <div class="coach-header" style="background-image: url('${coach.avatar || 'assets/img/default-coach.jpg'}');">
        <div class="coach-badge">
          <i class="fas fa-medal"></i>
          ${coach.badge}
        </div>
      </div>

      <div class="coach-body">
        <h3 class="coach-name">${coach.name}</h3>
        <p class="coach-title">${coach.title}</p>

        <div class="coach-stats">
          <div class="stat-item">
            <div class="stat-value">${coach.experience}</div>
            <div class="stat-label">年教龄</div>
          </div>
          <div class="stat-item">
            <div class="stat-value">${coach.students}+</div>
            <div class="stat-label">学员</div>
          </div>
          <div class="stat-item">
            <div class="stat-value">${coach.passRate}</div>
            <div class="stat-label">通过率</div>
          </div>
        </div>

        <div class="coach-features">
          ${coach.features.map(f => `<span class="feature-tag">${f}</span>`).join('')}
        </div>

        <div class="coach-rating">
          <div class="stars">
            ${'★'.repeat(Math.floor(coach.rating))}${'☆'.repeat(5 - Math.floor(coach.rating))}
          </div>
          <span class="rating-count">${coach.rating} (${coach.reviews}条评价)</span>
        </div>

        <div class="coach-actions">
          <button class="btn-secondary" onclick="event.stopPropagation(); showCoachDetail(${coach.id})">
            <i class="fas fa-info-circle"></i> 查看详情
          </button>
          <button class="btn-primary" onclick="event.stopPropagation(); quickSelect(${coach.id})">
            <i class="fas fa-check"></i> 选择
          </button>
        </div>
      </div>
    </div>
  `).join('');
}

// 前端本地筛选
function applyFilters() {
  const level = document.getElementById('levelFilter').value;
  const subject = document.getElementById('subjectFilter').value;
  const sort = document.getElementById('sortFilter').value;

  console.log('筛选条件 - 等级:', level, '科目:', subject, '排序:', sort);

  let filtered = [...allCoaches];

  if (level) {
    filtered = filtered.filter(c => c.level === level);
  }

  const subjectMap = {
    subject1: '科目一',
    subject2: '科目二',
    subject3: '科目三',
    subject4: '科目四',
    all: 'all'
  };

  if (subject && subject !== 'all') {
    filtered = filtered.filter(c => c.subjects.includes(subjectMap[subject]));
  } else if (subject === 'all') {
    filtered = filtered.filter(c => c.subjects.includes('科目二') && c.subjects.includes('科目三'));
  }


  if (sort === 'rating') {
    filtered.sort((a, b) => b.rating - a.rating);
  } else if (sort === 'students') {
    filtered.sort((a, b) => b.students - a.students);
  } else if (sort === 'experience') {
    filtered.sort((a, b) => b.experience - a.experience);
  }

  console.log('筛选结果:', filtered.length, '条教练');
  currentCoaches = filtered;
  renderCoaches(currentCoaches);
}

// 显示教练详情
async function showCoachDetail(coachId) {
  const modal = document.getElementById('coachModal');
  modal.classList.add('active');

  const basicCoach = allCoaches.find(c => c.id === coachId);
  if (basicCoach) {
    displayCoachDetail(basicCoach, true);
  }

  const coach = await loadCoachDetail(coachId);
  if (coach) {
    selectedCoachData = coach;
    displayCoachDetail(coach, false);
  } else {
    showCustomAlert('加载教练详情失败', 'error');
    closeModal();
  }
}

// 显示教练详情到模态框
function displayCoachDetail(coach, isLoading) {
  const modalHeader = document.getElementById('modalHeader');
  modalHeader.style.backgroundImage = `url('${coach.avatar || 'assets/img/default-coach.jpg'}')`;

  document.getElementById('modalName').textContent = coach.name;
  document.getElementById('modalTitle').textContent = coach.title;
  document.getElementById('modalExperience').textContent = `${coach.experience}年教学经验`;
  document.getElementById('modalStudents').textContent = `${coach.students}+名学员`;
  document.getElementById('modalSubjects').textContent = coach.subjects.join('、');
  document.getElementById('modalPassRate').textContent = coach.passRate;
  document.getElementById('modalDescription').textContent = coach.description;

  const reviewsContainer = document.getElementById('modalReviews');
  if (isLoading) {
    reviewsContainer.innerHTML = '<div style="text-align: center; color: #94a3b8;">加载评价中...</div>';
  } else {
    if (coach.reviewsList && coach.reviewsList.length > 0) {
      const reviewsHtml = coach.reviewsList.map(review => `
        <div class="review-item">
          <div class="review-header">
            <span class="reviewer-name">${review.name}</span>
            <span class="review-date">${review.date}</span>
          </div>
          <div class="review-stars">${'★'.repeat(review.rating)}${'☆'.repeat(5 - review.rating)}</div>
          <div class="review-text">${review.text}</div>
        </div>
      `).join('');
      reviewsContainer.innerHTML = reviewsHtml;
    } else {
      reviewsContainer.innerHTML = '<div style="text-align: center; color: #94a3b8;">暂无评价</div>';
    }
  }
}

// 关闭模态框
function closeModal() {
  document.getElementById('coachModal').classList.remove('active');
}

// 快速选择教练
async function quickSelect(coachId) {
  const coach = allCoaches.find(c => c.id === coachId);
  showCourseSelectionModal(coach);
}

// 从详情页选择教练
function selectCoach() {
  if (selectedCoachData) {
    closeModal();
    showCourseSelectionModal(selectedCoachData);
  }
}

// 显示课程选择模态框
async function showCourseSelectionModal(coach) {
  // 加载可选课程
  const courses = await loadAvailableCourses();

  if (courses === null) {
    return; // 已经在loadAvailableCourses中处理了错误
  }

  if (courses.length === 0) {
    // 检查是否有课程但都已有教练
    const token = sessionStorage.getItem('token');
    const response = await fetch('http://localhost:8080/user/coaches/my-courses', {
      headers: { 'Authorization': `Bearer ${token}` }
    });
    const result = await response.json();

    if (result.data && result.data.length > 0) {
      showCustomAlert('您的所有课程都已分配教练\n如需更换教练，请联系工作人员', 'info');
    } else {
      showCustomAlert('您还没有报名课程\n请先前往课程页面报名吧', 'info');
    }
    return;
  }

  availableCourses = courses;

  // 创建课程选择模态框
  const modalHtml = `
    <div class="custom-modal-overlay" id="courseSelectionOverlay" onclick="closeCourseSelectionModal(event)">
      <div class="custom-modal-content" onclick="event.stopPropagation()">
        <div class="custom-modal-header">
          <h3><i class="fas fa-user-check"></i> 选择教练任教的课程</h3>
          <button class="custom-modal-close" onclick="closeCourseSelectionModal()">
            <i class="fas fa-times"></i>
          </button>
        </div>
        <div class="custom-modal-body">
          <div class="selected-coach-info">
            <img src="${coach.avatar || 'assets/img/default-coach.jpg'}" alt="${coach.name}">
            <div class="coach-info-text">
              <h4>${coach.name}</h4>
              <p>${coach.title}</p>
            </div>
          </div>
          <div class="course-selection-list">
            <p class="selection-tip">请选择您希望该教练任教的课程：</p>
            ${courses.map(course => `
              <div class="course-selection-item" data-course-id="${course.courseId}">
                <div class="course-item-content">
                  <div class="course-item-icon">
                    <i class="fas fa-graduation-cap"></i>
                  </div>
                  <div class="course-item-info">
                    <h5>${course.courseDetail.name}</h5>
                    <p class="course-item-meta">
                      <span><i class="fas fa-clock"></i> ${course.courseDetail.duration}学时</span>
                      <span><i class="fas fa-calendar"></i> ${course.courseDetail.period}</span>
                      <span class="course-progress">进度: ${course.progress.toFixed(0)}%</span>
                    </p>
                  </div>
                </div>
                <button class="btn-select-course" onclick="confirmCoachSelection(${coach.id}, ${course.courseId})">
                  选择
                </button>
              </div>
            `).join('')}
          </div>
        </div>
      </div>
    </div>
  `;

  document.body.insertAdjacentHTML('beforeend', modalHtml);
  setTimeout(() => {
    document.getElementById('courseSelectionOverlay').classList.add('active');
  }, 10);
}

// 关闭课程选择模态框
function closeCourseSelectionModal(event) {
  if (event && event.target.id !== 'courseSelectionOverlay') {
    return;
  }
  const overlay = document.getElementById('courseSelectionOverlay');
  if (overlay) {
    overlay.classList.remove('active');
    setTimeout(() => overlay.remove(), 300);
  }
}

// 确认选择教练
async function confirmCoachSelection(coachId, courseId) {
  const token = sessionStorage.getItem('token');
  if (!token) {
    showCustomAlert('请先登录', 'warning');
    return;
  }

  const coach = allCoaches.find(c => c.id === coachId);
  const course = availableCourses.find(c => c.courseId === courseId);

  try {
    const response = await fetch(`http://localhost:8080/user/coaches/select?courseId=${courseId}&coachId=${coachId}`, {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });

    const result = await response.json();

    if (result.code === 200) {
      closeCourseSelectionModal();
      showCustomAlert(`成功选择 ${coach.name} 教练\n任教课程：${course.courseDetail.name}`, 'success');

      // 保存选择信息到sessionStorage
      sessionStorage.setItem('selectedCoach', JSON.stringify({
        coachId: coachId,
        coachName: coach.name,
        courseId: courseId,
        courseName: course.courseDetail.name
      }));
    } else {
      showCustomAlert('选择失败：' + result.message, 'error');
    }
  } catch (error) {
    console.error('选择教练失败:', error);
    showCustomAlert('网络错误，请稍后重试', 'error');
  }
}

// 自定义提示框
function showCustomAlert(message, type = 'info') {
  const icons = {
    success: 'fa-check-circle',
    error: 'fa-times-circle',
    warning: 'fa-exclamation-triangle',
    info: 'fa-info-circle'
  };

  const colors = {
    success: '#10b981',
    error: '#ef4444',
    warning: '#f59e0b',
    info: '#3b82f6'
  };

  const alertHtml = `
    <div class="custom-alert ${type}" id="customAlert">
      <div class="custom-alert-content">
        <i class="fas ${icons[type]}"></i>
        <p>${message.replace(/\n/g, '<br>')}</p>
      </div>
    </div>
  `;

  // 移除已存在的提示框
  const existingAlert = document.getElementById('customAlert');
  if (existingAlert) {
    existingAlert.remove();
  }

  document.body.insertAdjacentHTML('beforeend', alertHtml);
  const alertElement = document.getElementById('customAlert');

  setTimeout(() => alertElement.classList.add('show'), 10);

  setTimeout(() => {
    alertElement.classList.remove('show');
    setTimeout(() => alertElement.remove(), 300);
  }, 3000);
}

// 点击模态框外部关闭
document.getElementById('coachModal').addEventListener('click', function (e) {
  if (e.target === this) {
    closeModal();
  }
});

// 动态加载导航栏
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