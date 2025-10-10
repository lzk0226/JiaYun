// 动态加载导航栏
fetch('/component/navbar.html')
  .then(response => response.text())
  .then(data => {
    const placeholder = document.getElementById('navbar-placeholder');

    // 创建一个临时容器来解析 HTML
    const tempContainer = document.createElement('div');
    tempContainer.innerHTML = data;

    // 提取 nav 元素
    const nav = tempContainer.querySelector('nav');
    if (nav) {
      placeholder.appendChild(nav);
    }

    // 提取并应用 style
    const style = tempContainer.querySelector('style');
    if (style) {
      document.head.appendChild(style);
    }

    // 提取并执行 script
    const scriptContent = tempContainer.querySelector('script');
    if (scriptContent) {
      const newScript = document.createElement('script');
      newScript.textContent = scriptContent.textContent;
      document.body.appendChild(newScript);
    }

    // 导航栏加载完成后再绑定滚动事件
    setTimeout(() => {
      bindNavbarScroll();
      bindSmoothScroll();
    }, 300);
  })
  .catch(error => console.error('导航栏加载失败:', error));

// 导航栏滚动效果
function bindNavbarScroll() {
  window.addEventListener('scroll', () => {
    const navbar = document.querySelector('.navbar');
    if (!navbar) return;

    if (window.scrollY > 100) {
      navbar.style.background = 'rgba(255, 255, 255, 0.98)';
      navbar.style.boxShadow = '0 4px 20px rgba(0, 0, 0, 0.1)';
    } else {
      navbar.style.background = 'rgba(255, 255, 255, 0.95)';
      navbar.style.boxShadow = 'none';
    }
  });
}

// 平滑滚动
function bindSmoothScroll() {
  document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
      e.preventDefault();
      const target = document.querySelector(this.getAttribute('href'));
      if (target) {
        target.scrollIntoView({
          behavior: 'smooth',
          block: 'start'
        });
      }
    });
  });
}

// 功能卡片入场动画
const observerOptions = {
  threshold: 0.1,
  rootMargin: '0px 0px -50px 0px'
};

const observer = new IntersectionObserver((entries) => {
  entries.forEach(entry => {
    if (entry.isIntersecting) {
      entry.target.style.opacity = '1';
      entry.target.style.transform = 'translateY(0)';
    }
  });
}, observerOptions);

document.querySelectorAll('.feature-card').forEach((card, index) => {
  card.style.opacity = '0';
  card.style.transform = 'translateY(30px)';
  card.style.transition = `opacity 0.6s ease ${index * 0.1}s, transform 0.6s ease ${index * 0.1}s`;
  observer.observe(card);
});

// 教练卡片入场动画
document.querySelectorAll('.coach-card').forEach((card, index) => {
  card.style.opacity = '0';
  card.style.transform = 'translateY(30px)';
  card.style.transition = `opacity 0.6s ease ${index * 0.15}s, transform 0.6s ease ${index * 0.15}s`;
  observer.observe(card);
});

// 设备卡片入场动画
document.querySelectorAll('.equipment-card').forEach((card, index) => {
  card.style.opacity = '0';
  card.style.transform = 'translateY(30px)';
  card.style.transition = `opacity 0.6s ease ${index * 0.15}s, transform 0.6s ease ${index * 0.15}s`;
  observer.observe(card);
});

// 按钮点击效果
document.querySelectorAll('.btn').forEach(btn => {
  btn.addEventListener('click', function (e) {
    const ripple = document.createElement('span');
    const rect = this.getBoundingClientRect();
    const size = Math.max(rect.width, rect.height);
    const x = e.clientX - rect.left - size / 2;
    const y = e.clientY - rect.top - size / 2;

    ripple.style.width = ripple.style.height = size + 'px';
    ripple.style.left = x + 'px';
    ripple.style.top = y + 'px';
    ripple.style.position = 'absolute';
    ripple.style.background = 'rgba(255, 255, 255, 0.3)';
    ripple.style.borderRadius = '50%';
    ripple.style.transform = 'scale(0)';
    ripple.style.animation = 'ripple 0.6s linear';
    ripple.style.pointerEvents = 'none';

    this.style.position = 'relative';
    this.style.overflow = 'hidden';
    this.appendChild(ripple);

    setTimeout(() => {
      ripple.remove();
    }, 600);
  });
});

// 添加波纹动画CSS
const style = document.createElement('style');
style.textContent = `
  @keyframes ripple {
    to {
      transform: scale(4);
      opacity: 0;
    }
  }
`;
document.head.appendChild(style);

// 测试登录函数
window.testLogin = function () {
  if (typeof window.updateNavbar === 'function') {
    window.login = true;
    localStorage.setItem('login', 'true');
    window.updateNavbar();
  } else {
    window.login = true;
    localStorage.setItem('login', 'true');
    location.reload();
  }
};

window.testLogout = function () {
  if (typeof window.updateNavbar === 'function') {
    window.login = false;
    localStorage.removeItem('login');
    window.updateNavbar();
  } else {
    window.login = false;
    localStorage.removeItem('login');
    location.reload();
  }
};