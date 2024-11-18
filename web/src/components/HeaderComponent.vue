<template>
  <header class="header">
    <div class="logo">
      <!-- Логотип як картинка -->
      <img :src="require('@/assets/Logo.svg')" alt="Logo" class="logo-image" />
    </div>
    <nav class="navigation">
      <button class="dropdown">Книги ▼</button>
      <button class="library-button">Бібліотека</button>
    </nav>
    <div class="search-profile">
      <div class="search-container">
        <!-- Поле з іконкою всередині -->
        <div class="search-wrapper">
          <input
            id="search-input"
            type="text"
            class="search-input"
            placeholder="Пошук"
            aria-label="Введіть текст для пошуку"
          />
          <img :src="require('@/assets/inactive_search.svg')" alt="Іконка" class="search-icon"/>
        </div>
      </div>
      <button class="profile-menu">
        <span class="menu-icon">☰</span>
        <div class="profile-picture"></div>
      </button>
    </div>
  </header>
</template>

<script>
export default {
  name: 'HeaderComponent',
};
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 5%; /* Відступи зліва та справа у відсотках для гнучкості */
  background-color: white;
  color: #333;
  border-bottom: 1px solid #000; /* Тонка чорна лінія внизу */
}

.logo {
  display: flex;
  align-items: center;
  flex: 1; /* Забезпечує, що логотип не буде займати більше місця, ніж потрібно */
}

.logo-image {
  width: auto;
  height: 40px; /* Висота логотипу може бути адаптивною */
  max-width: 100%; /* Забезпечує адаптацію логотипу до контейнера */
}

.navigation {
  display: flex;
  align-items: center;
  gap: 15px;
  justify-content: center; /* Центрує кнопки навігації */
  flex: 2; /* Забезпечує, що між кнопками та лого буде простір */
}

.dropdown, .library-button {
  background: none;
  border: none;
  font-size: 16px;
  cursor: pointer;
  text-align: center;
}

.search-profile {
  display: flex;
  align-items: center;
  gap: 20px; /* Додано відступ між полем пошуку та кнопкою профілю */
  flex-grow: 1; /* Забезпечує, що поле пошуку не буде розтягуватися занадто сильно */
  justify-content: flex-end; /* Розташовує все по правому краю */
}

.search-container {
  display: flex;
  align-items: center;
  max-width: 300px; /* Обмежує максимальну ширину для поля пошуку */
  flex-grow: 1; /* Робить поле пошуку гнучким */
  margin-right: 20px; /* Відступ справа, щоб не було занадто близько до профілю */
}

.search-wrapper {
  position: relative;
  width: 100%;
}

.search-input {
  width: 100%;
  height: 39px;
  border: 2px solid #0E0E0E;
  border-radius: 20px;
  padding: 5px 10px;
  padding-left: 40px;
  box-sizing: border-box;
  color: #000; /* Колір тексту в полі пошуку */
}

.search-icon {
  position: absolute;
  left: 10px;
  top: 50%;
  transform: translateY(-50%);
  width: 20px;
  height: 20px;
  pointer-events: none;
}

.profile-menu {
  display: flex;
  align-items: center;
  gap: 5px;
  background-color: #819CC7;
  border: none;
  border-radius: 5px;
  padding: 5px 10px;
  cursor: pointer;
}

.menu-icon {
  font-size: 20px;
}

.profile-picture {
  width: 30px;
  height: 30px;
  background-color: #333;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #fff;
  font-size: 12px;
}
</style>
