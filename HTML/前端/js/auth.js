// 后端API基础URL，根据实际情况修改
const API_BASE_URL = 'http://localhost:8080/user/student';

// ============== 工具函数 ==============
// Token管理工具
const TokenManager = {
  // 保存登录信息
  saveLoginData(data) {
    sessionStorage.setItem('token', data.token);
    sessionStorage.setItem('refreshToken', data.refreshToken);
    sessionStorage.setItem('userInfo', JSON.stringify(data.user));
    // 保存登录时间，用于判断token有效期
    sessionStorage.setItem('loginTime', Date.now().toString());
  },

  // 获取token
  getToken() {
    return sessionStorage.getItem('token');
  },

  // 获取用户信息
  getUserInfo() {
    const userInfo = sessionStorage.getItem('userInfo');
    return userInfo ? JSON.parse(userInfo) : null;
  },

  // 清除所有登录信息
  clearLoginData() {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('refreshToken');
    sessionStorage.removeItem('userInfo');
    sessionStorage.removeItem('loginTime');
  },

  // 检查是否已登录
  isLoggedIn() {
    return !!this.getToken();
  }
};

// ============== 页面加载时检查登录状态 ==============
window.addEventListener('DOMContentLoaded', function () {
  // 如果已登录，直接跳转到首页
  if (TokenManager.isLoggedIn()) {
    console.log('检测到已登录，正在跳转...');
    window.location.href = 'index.html';
    return; // 阻止后续代码执行
  }
});

// ============== UI交互逻辑 ==============
const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

signUpButton.addEventListener('click', () => {
  container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
  container.classList.remove("right-panel-active");
});

// ============== 注册逻辑 ==============
document.getElementById("registerForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const name = document.getElementById("regName").value.trim();
  const email = document.getElementById("regEmail").value.trim();
  const phone = document.getElementById("regPhone").value.trim();
  const password = document.getElementById("regPassword").value.trim();

  // 表单验证
  if (!name || !email || !phone || !password) {
    alert("请填写完整信息！");
    return;
  }

  // 邮箱格式验证
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email)) {
    alert("请输入有效的邮箱地址！");
    return;
  }

  // 手机号格式验证
  const phoneRegex = /^1[3-9]\d{9}$/;
  if (!phoneRegex.test(phone)) {
    alert("请输入有效的手机号码！");
    return;
  }

  // 密码长度验证
  if (password.length < 6) {
    alert("密码长度至少为6位！");
    return;
  }

  // 禁用按钮，防止重复提交
  const submitBtn = e.target.querySelector('button[type="submit"]');
  const originalText = submitBtn.textContent;
  submitBtn.textContent = '注册中...';
  submitBtn.disabled = true;

  try {
    const response = await fetch(`${API_BASE_URL}/register`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        name: name,
        userId: email,
        phone: phone,
        password: password
      })
    });

    const result = await response.json();

    if (result.code === 200) {
      alert("注册成功！请登录。");
      // 切换到登录面板
      container.classList.remove("right-panel-active");
      // 清空表单
      document.getElementById("registerForm").reset();
      // 自动填充邮箱到登录表单
      document.getElementById("loginEmail").value = email;
    } else {
      alert(result.message || "注册失败，请重试！");
    }
  } catch (error) {
    console.error('注册错误:', error);
    alert("网络错误，请检查后端服务是否启动！");
  } finally {
    // 恢复按钮状态
    submitBtn.textContent = originalText;
    submitBtn.disabled = false;
  }
});

// ============== 登录逻辑 ==============
document.getElementById("loginForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const email = document.getElementById("loginEmail").value.trim();
  const password = document.getElementById("loginPassword").value.trim();

  if (!email || !password) {
    alert("请填写邮箱和密码！");
    return;
  }

  // 禁用按钮，防止重复提交
  const submitBtn = e.target.querySelector('button[type="submit"]');
  const originalText = submitBtn.textContent;
  submitBtn.textContent = '登录中...';
  submitBtn.disabled = true;

  try {
    const response = await fetch(`${API_BASE_URL}/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email: email,
        password: password
      })
    });

    const result = await response.json();

    if (result.code === 200) {
      // 保存登录信息到sessionStorage
      TokenManager.saveLoginData(result.data);

      console.log('登录成功，用户信息:', result.data.user);

      // 显示欢迎消息
      alert(`欢迎回来，${result.data.user.name}！`);

      // 延迟跳转，确保数据已保存
      setTimeout(() => {
        window.location.href = "index.html";
      }, 100);
    } else {
      alert(result.message || "邮箱或密码错误！");
    }
  } catch (error) {
    console.error('登录错误:', error);
    alert("网络错误，请检查后端服务是否启动！");
  } finally {
    // 恢复按钮状态
    submitBtn.textContent = originalText;
    submitBtn.disabled = false;
  }
});

// ============== 开发调试工具（生产环境请删除） ==============
// 在控制台输入 TokenManager.clearLoginData() 可以清除登录信息
window.TokenManager = TokenManager;