// ==================== API 配置 ====================
const API_BASE_URL = 'http://localhost:8080/user';

// ==================== 全局变量 ====================
let selectedTime = null;
let selectedVehicle = null;
let currentCoachId = null;
let currentDate = null;

// ==================== 工具函数 ====================

// 获取 Token
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

// ==================== 数据加载函数 ====================

// 加载教练列表
async function loadCoaches() {
  console.log('加载教练列表...');
  const coaches = await apiRequest(`${API_BASE_URL}/reservation/coaches`);

  if (coaches && coaches.length > 0) {
    const coachSelect = document.getElementById('coachSelect');
    coachSelect.innerHTML = '<option value="">请选择教练</option>';

    coaches.forEach(coach => {
      const option = document.createElement('option');
      option.value = coach.id;
      option.textContent = `${coach.name} - ${coach.title}`;
      option.dataset.coachInfo = JSON.stringify(coach);
      coachSelect.appendChild(option);
    });
  }
}

// 加载可用时段
async function loadAvailableTimeSlots(date, coachId) {
  console.log('加载时段:', date, coachId);

  if (!date || !coachId) {
    return;
  }

  const timeSlots = await apiRequest(
    `${API_BASE_URL}/reservation/available-times?date=${date}&coachId=${coachId}`
  );

  const timeGrid = document.getElementById('timeGrid');
  timeGrid.innerHTML = '';

  if (timeSlots && timeSlots.length > 0) {
    timeSlots.forEach(slot => {
      const div = document.createElement('div');
      div.className = `time-slot ${!slot.available ? 'disabled' : ''}`;
      div.dataset.time = slot.time;
      div.innerHTML = `<div>${slot.time}</div><small>${slot.period}</small>`;

      if (slot.available) {
        div.addEventListener('click', () => selectTimeSlot(div, slot.time));
      }

      timeGrid.appendChild(div);
    });
  }
}

// 加载可用车辆
async function loadAvailableVehicles(date, coachId, timeSlot) {
  console.log('加载车辆:', date, coachId, timeSlot);

  if (!date || !coachId || !timeSlot) {
    return;
  }

  const vehicles = await apiRequest(
    `${API_BASE_URL}/reservation/available-vehicles?date=${date}&coachId=${coachId}&timeSlot=${encodeURIComponent(timeSlot)}`
  );

  const vehicleGrid = document.getElementById('vehicleGrid');
  vehicleGrid.innerHTML = '';

  if (vehicles && vehicles.length > 0) {
    vehicles.forEach(vehicle => {
      const div = document.createElement('div');
      div.className = 'vehicle-item';
      div.dataset.vehicleId = vehicle.id;
      div.innerHTML = `
        <div class="vehicle-icon">🚗</div>
        <div class="vehicle-name">${vehicle.plateNumber || vehicle.model}</div>
        <div class="vehicle-status">${vehicle.status === 'available' ? '可用' : '不可用'}</div>
      `;

      div.addEventListener('click', () => selectVehicle(div, vehicle.id));
      vehicleGrid.appendChild(div);
    });
  } else {
    vehicleGrid.innerHTML = '<p style="text-align:center;color:#999;padding:2rem;">该时段暂无可用车辆</p>';
  }
}

// 加载预约历史
async function loadReservationHistory(status = null) {
  console.log('加载预约历史:', status);

  let url = `${API_BASE_URL}/reservation/history`;
  if (status) {
    url += `?status=${status}`;
  }

  const history = await apiRequest(url);
  renderHistory(history || []);
}

// ==================== 渲染函数 ====================

// 渲染历史记录
function renderHistory(list) {
  const historyList = document.getElementById('historyList');
  historyList.innerHTML = '';

  if (list.length === 0) {
    historyList.innerHTML = `<div class="empty-state"><i class="fas fa-clock"></i><div>暂无记录</div></div>`;
    return;
  }

  list.forEach(item => {
    const historyItem = document.createElement('div');
    historyItem.className = 'history-item';
    historyItem.dataset.status = item.status;

    const statusMap = {
      'upcoming': { class: 'status-upcoming', text: '即将开始' },
      'completed': { class: 'status-completed', text: '已完成' },
      'cancelled': { class: 'status-cancelled', text: '已取消' }
    };

    const status = statusMap[item.status] || { class: '', text: item.status };

    historyItem.innerHTML = `
      <div class="history-header">
        <div class="history-date">${item.reservationDate} ${item.timeSlot}</div>
        <span class="status-badge ${status.class}">${status.text}</span>
      </div>
      <div class="history-details">
        <div class="detail-item">
          <i class="fas fa-car"></i>
          <span>${item.vehicle ? (item.vehicle.plateNumber || item.vehicle.model) : '未分配'}</span>
        </div>
        <div class="detail-item">
          <i class="fas fa-user-tie"></i>
          <span>${item.coach ? item.coach.name : '未分配'}</span>
        </div>
        ${item.remarks ? `<div class="detail-item"><i class="fas fa-comment"></i><span>${item.remarks}</span></div>` : ''}
      </div>
      <div class="history-actions">
        ${item.status === 'upcoming' ? `
          <button class="action-btn danger" onclick="cancelReservation(${item.id})">取消预约</button>
          <button class="action-btn" onclick="viewDetail(${item.id})">查看详情</button>
        ` : ''}
        ${item.status === 'completed' ? `
          <button class="action-btn" onclick="viewDetail(${item.id})">查看详情</button>
        ` : ''}
        ${item.status === 'cancelled' ? `
          <button class="action-btn danger" onclick="deleteReservation(${item.id})">删除记录</button>
        ` : ''}
      </div>
    `;

    historyList.appendChild(historyItem);
  });
}

// ==================== 交互函数 ====================

// 选择时段
function selectTimeSlot(element, time) {
  document.querySelectorAll('.time-slot').forEach(s => s.classList.remove('selected'));
  element.classList.add('selected');
  selectedTime = time;
  selectedVehicle = null;

  // 加载可用车辆
  if (currentDate && currentCoachId) {
    loadAvailableVehicles(currentDate, currentCoachId, time);
  }
}

// 选择车辆
function selectVehicle(element, vehicleId) {
  document.querySelectorAll('.vehicle-item').forEach(v => v.classList.remove('selected'));
  element.classList.add('selected');
  selectedVehicle = vehicleId;
}

// 取消预约
async function cancelReservation(id) {
  if (!confirm('确定取消预约吗？')) return;

  const result = await apiRequest(`${API_BASE_URL}/reservation/${id}/cancel`, {
    method: 'PUT'
  });

  if (result !== null) {
    alert('取消成功');
    loadReservationHistory();
  }
}

// 删除预约记录
async function deleteReservation(id) {
  if (!confirm('确定删除记录吗？')) return;

  const result = await apiRequest(`${API_BASE_URL}/reservation/${id}`, {
    method: 'DELETE'
  });

  if (result !== null) {
    alert('删除成功');
    loadReservationHistory();
  }
}

// 查看详情
async function viewDetail(id) {
  const detail = await apiRequest(`${API_BASE_URL}/reservation/${id}`);

  if (detail) {
    const coachInfo = detail.coach ? `${detail.coach.name} - ${detail.coach.title || ''}` : '未分配';
    const vehicleInfo = detail.vehicle ? `${detail.vehicle.plateNumber || ''} ${detail.vehicle.model || ''}` : '未分配';

    alert(`预约详情\n\n日期：${detail.reservationDate}\n时段：${detail.timeSlot}\n教练：${coachInfo}\n车辆：${vehicleInfo}\n备注：${detail.remarks || '无'}\n状态：${detail.status}`);
  }
}

// ==================== 事件绑定 ====================

// 日期选择
document.getElementById('bookingDate').addEventListener('change', function (e) {
  currentDate = e.target.value;
  selectedTime = null;
  selectedVehicle = null;

  document.querySelectorAll('.time-slot').forEach(s => s.classList.remove('selected'));
  document.getElementById('vehicleGrid').innerHTML = '';

  if (currentDate && currentCoachId) {
    loadAvailableTimeSlots(currentDate, currentCoachId);
  }
});

// 教练选择
document.getElementById('coachSelect').addEventListener('change', function (e) {
  currentCoachId = e.target.value;
  selectedTime = null;
  selectedVehicle = null;

  document.getElementById('timeGrid').innerHTML = '';
  document.getElementById('vehicleGrid').innerHTML = '';

  if (currentDate && currentCoachId) {
    loadAvailableTimeSlots(currentDate, currentCoachId);
  }
});

// 表单提交
document.getElementById('bookingForm').addEventListener('submit', async function (e) {
  e.preventDefault();

  if (!currentDate) {
    alert('请选择预约日期');
    return;
  }

  if (!currentCoachId) {
    alert('请选择教练');
    return;
  }

  if (!selectedTime) {
    alert('请选择时段');
    return;
  }

  if (!selectedVehicle) {
    alert('请选择车辆');
    return;
  }

  const remarks = document.getElementById('remarks').value.trim();

  const reservationData = {
    coachId: parseInt(currentCoachId),
    vehicleId: parseInt(selectedVehicle),
    reservationDate: currentDate,
    timeSlot: selectedTime,
    remarks: remarks
  };

  console.log('提交预约数据:', reservationData);

  const result = await apiRequest(`${API_BASE_URL}/reservation`, {
    method: 'POST',
    body: JSON.stringify(reservationData)
  });

  if (result !== null) {
    alert('预约成功！');

    // 重置表单
    this.reset();
    currentDate = null;
    currentCoachId = null;
    selectedTime = null;
    selectedVehicle = null;

    document.getElementById('timeGrid').innerHTML = '';
    document.getElementById('vehicleGrid').innerHTML = '';

    // 重新加载历史记录
    loadReservationHistory();
  }
});

// 筛选标签
document.querySelectorAll('.filter-tab').forEach(tab => {
  tab.addEventListener('click', function () {
    document.querySelectorAll('.filter-tab').forEach(t => t.classList.remove('active'));
    this.classList.add('active');

    const filter = this.dataset.filter;
    const status = filter === 'all' ? null : filter;
    loadReservationHistory(status);
  });
});

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

  // 设置日期限制
  const today = new Date().toISOString().split('T')[0];
  const dateInput = document.getElementById('bookingDate');
  dateInput.setAttribute('min', today);
  dateInput.value = today;
  currentDate = today;

  // 加载初始数据
  await loadCoaches();
  await loadReservationHistory();

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
window.cancelReservation = cancelReservation;
window.deleteReservation = deleteReservation;
window.viewDetail = viewDetail;