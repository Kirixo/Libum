<template>
  <footer class="footer">
    <div class="footer-content">
      <!-- Логотип -->
      <div class="footer-logo">
        <img src="@/assets/Logo.svg" alt="Libum Logo" />
      </div>

      <!-- Контейнер для секции "Завантажити" и QR-кода -->
      <div class="footer-right">
        <!-- Секция "Завантажити" -->
        <div class="footer-download">
          <h4>Завантажити</h4>
          <p>Наш мобільний додаток</p>
        </div>

        <!-- QR-код -->
        <div class="footer-qr-code">
          <img src="@/assets/qr-code.png" alt="QR Code" />
        </div>
      </div>
    </div>
  </footer>
</template>

<style>
.footer {
  width: 100%;
  background-color: #d9d9d9;
}

.footer-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 10px;
  padding: 10px;
}

.footer-logo img {
  height: 60px;
  margin-left: 60px;
}

.footer-right {
  display: flex;
  align-items: center;
  gap: 20px;
  /* Расстояние между секцией "Завантажити" и QR-кодом */
  margin-right: 60px;
}

.footer-download {
  text-align: right;
}

.footer-qr-code img {
  height: 150px;
}
</style>
