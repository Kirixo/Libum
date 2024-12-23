<template>
  <header class="header">
    <div class="logo">
      <img :src="require('@/assets/Logo.svg')" alt="Logo" class="logo-image" />
    </div>
    <nav class="navigation">
      <button class="books-button">Книги ↓</button>
      <button class="library-button">Бібліотека</button>
    </nav>
    <div class="search-profile">
      <div class="search-container">
        <div class="search-wrapper">
          <input id="search-input" type="text" class="search-input" placeholder="Пошук"
            aria-label="Введіть текст для пошуку" @focus="onSearchFocus" @blur="onSearchBlur" />
          <img :src="isSearchActive ? require('@/assets/active_search.svg') : require('@/assets/inactive_search.svg')"
            alt="Іконка" class="search-icon" />
        </div>
      </div>
    </div>
    <div class="auth-section">
      <template v-if="this.$store.state.userInfo">
        <button class="profile-menu">
          <span class="menu-icon">☰</span>
          <div class="profile-picture"></div>
        </button>
      </template>
      <template v-else>
        <button class="auth-button auth-button-login" @click="showLoginModal">Авторизація</button>
        <LoginModal v-if="isLoginModalVisible" :isVisible="isLoginModalVisible" @close="isLoginModalVisible = false"
          @login="handleLogin" />
        <button class="auth-button auth-button-register" @click="showRestorPasswordModal">Реєстрація</button>
        <Signup v-if="isSignupModalVisible" :isVisible="isSignupModalVisible" @close="isSignupModalVisible = false"
          @signup="handleSignup" />
      </template>
    </div>
  </header>
</template>

<script>
import LoginModal from './LoginModalComponent.vue';
import Signup from './SignupModalComponent.vue';

export default {
  components: {
    LoginModal,
    Signup,
  },
  name: 'HeaderComponent',
  data() {
    return {
      userId: null, // Ідентифікатор користувача, якщо користувач авторизований
      isLoginModalVisible: false,
      isSignupModalVisible: false,
      isRestorPasswordModalVisible: false,
      isSearchActive: false, // Флаг для отслеживания активности поиска
    };
  },
  methods: {
    showLoginModal() {
      this.isLoginModalVisible = true;
    },
    showRestorPasswordModal() {
      this.isRestorPasswordModalVisible = true;
    },
    onSearchFocus() {
      this.isSearchActive = true; // Когда поле поиска активировано
    },
    onSearchBlur() {
      this.isSearchActive = false; // Когда поле поиска теряет фокус
    },
    login() {
      console.log('Перехід до сторінки авторизації');
      this.$router.push({ name: 'LoginPage' });
    },
    register() {
      console.log('Перехід до сторінки реєстрації');
      this.$router.push({ name: 'RegisterPage' });
    },
    fetchUserId() {
      // Імітація отримання userId (зазвичай отримується через Vuex або localStorage)
      const token = localStorage.getItem('authToken');
      if (token) {
        this.userId = null; // Замініть на логіку отримання userId
      } else {
        this.userId = null;
      }
    },
  },
  created() {
    this.fetchUserId(); // Перевіряємо наявність userId при завантаженні компонента
  },
};
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 5%;
  background-color: white;
  color: #333;
  border-bottom: 1px solid #000;
}

.logo-image {
  width: auto;
  height: 60px;
  max-width: 100%;
}

.navigation {
  display: flex;
  align-items: center;
  gap: 10px;
  justify-content: center;
  flex: 2;
}

/* Стиль для кнопок навигации (Книги и Бібліотека) */
.books-button,
.library-button {
  padding: 10px 20px;
  background-color: transparent;
  color: #333;
  font-size: 16px;
  cursor: pointer;
  border: none;
  position: relative;
  transition: color 0.3s ease;
}

.books-button {
  margin-right: 20px;
  /* Увеличиваем отступ справа */
}

.books-button::before,
.library-button::before {
  content: '';
  position: absolute;
  top: -42px;
  /* Подсветка выходит за верхний край */
  bottom: -33px;
  /* Подсветка выходит за нижний край */
  left: -10px;
  /* Немного расширяем область слева */
  right: -10px;
  /* Немного расширяем область справа */
  background-color: #6E85B7;
  opacity: 0;
  transition: opacity 0.3s ease;
  border-radius: 10px;
  z-index: -1;
  /* Псевдоэлемент будет за текстом */
}

.books-button:hover::before,
.library-button:hover::before {
  opacity: 1;
  /* Показываем подсветку при наведении */
}

.books-button:hover,
.library-button:hover {
  color: white;
  z-index: 1;
  /* Текст будет выше псевдоэлемента */
}

/* Общий стиль для всех кнопок авторизации */
.auth-button {
  padding: 10px 20px;
  background-color: #6E85B7;
  color: white;
  font-size: 16px;
  cursor: pointer;
  border: 2px solid #6E85B7;
  border-radius: 10px;
  transition: background-color 0.3s ease;
}

/* Кнопка авторизации */
.auth-button-login {
  margin-right: 15px;
  /* Устанавливаем отступ для кнопки авторизации */
}

/* Кнопка регистрации */
.auth-button-register {
  margin-left: 0;
  /* Убираем лишние отступы для кнопки регистрации */
}

/* Ховер для всех кнопок авторизации */
.auth-button:hover {
  background-color: #5A6D9A;
  color: white;
}

/* Стиль для профиля */
.profile-menu {
  display: flex;
  align-items: center;
  gap: 5px;
  background-color: #6E85B7;
  border: 2px solid #6E85B7;
  border-radius: 10px;
  padding: 10px 15px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.profile-menu:hover {
  background-color: #6E85B7;
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

/* Стиль для поиска */
.search-container {
  display: flex;
  align-items: center;
  max-width: 300px;
  flex-grow: 1;
  margin-right: 20px;
}

.search-wrapper {
  position: relative;
  width: 100%;
}

.search-input {
  width: 100%;
  height: 45px;
  border: 2px solid #0E0E0E;
  border-radius: 20px;
  padding: 5px 10px;
  padding-left: 40px;
  box-sizing: border-box;
  color: #000;
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

/* Добавляем стили для модальных окон */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
  /* Убедитесь, что модальные окна будут поверх всего */
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  width: 400px;
  z-index: 1000;
  /* Важно, чтобы контент окна был на верхнем слое */
}
</style>
