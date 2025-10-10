// ==================== 自定义弹窗组件 ====================

class Modal {
  constructor() {
    this.createModalHTML();
  }

  // 创建弹窗HTML结构
  createModalHTML() {
    if (document.getElementById('customModal')) return;

    const modalHTML = `
      <div id="customModal" class="custom-modal">
        <div class="modal-overlay"></div>
        <div class="modal-container">
          <div class="modal-header">
            <h3 class="modal-title"></h3>
            <button class="modal-close" onclick="modal.close()">
              <i class="fas fa-times"></i>
            </button>
          </div>
          <div class="modal-body">
            <div class="modal-icon"></div>
            <p class="modal-message"></p>
          </div>
          <div class="modal-footer">
            <button class="modal-btn modal-btn-cancel" onclick="modal.cancel()">取消</button>
            <button class="modal-btn modal-btn-confirm" onclick="modal.confirm()">确定</button>
          </div>
        </div>
      </div>
    `;

    const styleHTML = `
      <style>
        .custom-modal {
          display: none;
          position: fixed;
          top: 0;
          left: 0;
          width: 100%;
          height: 100%;
          z-index: 10001;
          animation: fadeIn 0.2s ease;
        }

        .custom-modal.show {
          display: flex;
          align-items: center;
          justify-content: center;
        }

        .modal-overlay {
          position: absolute;
          top: 0;
          left: 0;
          width: 100%;
          height: 100%;
          background: rgba(0, 0, 0, 0.5);
          backdrop-filter: blur(4px);
        }

        .modal-container {
          position: relative;
          background: white;
          border-radius: 12px;
          box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
          max-width: 450px;
          width: 90%;
          animation: slideUp 0.3s ease;
          overflow: hidden;
        }

        .modal-header {
          display: flex;
          align-items: center;
          justify-content: space-between;
          padding: 1.5rem;
          border-bottom: 1px solid #e5e7eb;
        }

        .modal-title {
          font-size: 1.25rem;
          font-weight: 600;
          color: #1f2937;
          margin: 0;
        }

        .modal-close {
          background: none;
          border: none;
          font-size: 1.5rem;
          color: #6b7280;
          cursor: pointer;
          padding: 0;
          width: 32px;
          height: 32px;
          display: flex;
          align-items: center;
          justify-content: center;
          border-radius: 6px;
          transition: all 0.2s;
        }

        .modal-close:hover {
          background: #f3f4f6;
          color: #374151;
        }

        .modal-body {
          padding: 2rem 1.5rem;
          text-align: center;
        }

        .modal-icon {
          font-size: 3rem;
          margin-bottom: 1rem;
        }

        .modal-icon.success { color: #10b981; }
        .modal-icon.error { color: #ef4444; }
        .modal-icon.warning { color: #f59e0b; }
        .modal-icon.info { color: #3b82f6; }
        .modal-icon.question { color: #8b5cf6; }

        .modal-icon.success::before { content: "✓"; }
        .modal-icon.error::before { content: "✕"; }
        .modal-icon.warning::before { content: "⚠"; }
        .modal-icon.info::before { content: "ℹ"; }
        .modal-icon.question::before { content: "?"; }

        .modal-message {
          font-size: 1rem;
          line-height: 1.6;
          color: #4b5563;
          margin: 0;
          white-space: pre-line;
        }

        .modal-footer {
          display: flex;
          gap: 0.75rem;
          padding: 1rem 1.5rem;
          background: #f9fafb;
          border-top: 1px solid #e5e7eb;
        }

        .modal-btn {
          flex: 1;
          padding: 0.75rem 1.5rem;
          font-size: 1rem;
          font-weight: 500;
          border: none;
          border-radius: 8px;
          cursor: pointer;
          transition: all 0.2s;
        }

        .modal-btn-cancel {
          background: white;
          color: #374151;
          border: 1px solid #d1d5db;
        }

        .modal-btn-cancel:hover {
          background: #f3f4f6;
        }

        .modal-btn-confirm {
          background: #3b82f6;
          color: white;
        }

        .modal-btn-confirm:hover {
          background: #2563eb;
        }

        .modal-btn-confirm.success {
          background: #10b981;
        }

        .modal-btn-confirm.success:hover {
          background: #059669;
        }

        .modal-btn-confirm.danger {
          background: #ef4444;
        }

        .modal-btn-confirm.danger:hover {
          background: #dc2626;
        }

        @keyframes fadeIn {
          from { opacity: 0; }
          to { opacity: 1; }
        }

        @keyframes slideUp {
          from {
            opacity: 0;
            transform: translateY(20px);
          }
          to {
            opacity: 1;
            transform: translateY(0);
          }
        }
      </style>
    `;

    document.body.insertAdjacentHTML('beforeend', modalHTML);
    document.head.insertAdjacentHTML('beforeend', styleHTML);

    this.modalElement = document.getElementById('customModal');
    this.resolveCallback = null;
  }

  // 显示弹窗
  show(options = {}) {
    const {
      title = '提示',
      message = '',
      type = 'info', // success, error, warning, info, question
      showCancel = false,
      confirmText = '确定',
      cancelText = '取消',
      confirmClass = ''
    } = options;

    this.modalElement.querySelector('.modal-title').textContent = title;
    this.modalElement.querySelector('.modal-message').textContent = message;

    const icon = this.modalElement.querySelector('.modal-icon');
    icon.className = `modal-icon ${type}`;

    const footer = this.modalElement.querySelector('.modal-footer');
    const cancelBtn = this.modalElement.querySelector('.modal-btn-cancel');
    const confirmBtn = this.modalElement.querySelector('.modal-btn-confirm');

    if (showCancel) {
      cancelBtn.style.display = 'block';
      cancelBtn.textContent = cancelText;
    } else {
      cancelBtn.style.display = 'none';
    }

    confirmBtn.textContent = confirmText;
    confirmBtn.className = `modal-btn modal-btn-confirm ${confirmClass}`;

    this.modalElement.classList.add('show');

    return new Promise((resolve) => {
      this.resolveCallback = resolve;
    });
  }

  // 确认
  confirm() {
    this.close();
    if (this.resolveCallback) {
      this.resolveCallback(true);
    }
  }

  // 取消
  cancel() {
    this.close();
    if (this.resolveCallback) {
      this.resolveCallback(false);
    }
  }

  // 关闭弹窗
  close() {
    this.modalElement.classList.remove('show');
  }

  // Alert - 提示框
  alert(message, title = '提示', type = 'info') {
    return this.show({
      title,
      message,
      type,
      showCancel: false,
      confirmText: '知道了'
    });
  }

  // Success - 成功提示
  success(message, title = '成功') {
    return this.show({
      title,
      message,
      type: 'success',
      showCancel: false,
      confirmText: '好的',
      confirmClass: 'success'
    });
  }

  // Error - 错误提示
  error(message, title = '错误') {
    return this.show({
      title,
      message,
      type: 'error',
      showCancel: false,
      confirmText: '知道了',
      confirmClass: 'danger'
    });
  }

  // Warning - 警告提示
  warning(message, title = '警告') {
    return this.show({
      title,
      message,
      type: 'warning',
      showCancel: false,
      confirmText: '知道了'
    });
  }

  // Confirm - 确认框
  confirmDialog(message, title = '确认操作') {
    return this.show({
      title,
      message,
      type: 'question',
      showCancel: true,
      confirmText: '确定',
      cancelText: '取消'
    });
  }
}

// 创建全局实例
const modal = new Modal();

// 导出供其他模块使用
window.modal = modal;