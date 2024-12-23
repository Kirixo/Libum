<template>
  <header class="header">
    <div class="logo">
      <img :src="require('@/assets/Logo.svg')" alt="Logo" class="logo-image" />
    </div>
    <nav class="navigation">
      <button class="books-button" @click="toggleDropdownMenu" ref="booksButton">Книги ↓</button>
      <!-- Використовуємо позиціонування під кнопкою -->
      <div v-if="isDropdownMenuVisible" class="dropdown-menu" :style="dropdownMenuPosition">
        <ul>
          <li>Книга 1</li>
          <li>Книга 2</li>
          <li>Книга 3</li>
        </ul>
      </div>
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
        <button class="profile-menu" @click="toggleProfileDropdown" ref="profileButton">
          <span class="menu-icon">☰</span>
          <div class="profile-picture"></div>
        </button>
        <!-- Дропдаун меню для профілю користувача -->
        <div v-if="isProfileDropdownVisible" class="profile-dropdown-menu" :style="profileDropdownPosition">
          <ul>
            <!-- Пункт з аватаром та ім'ям користувача -->
            <li class="profile-info">
              <div class="avatar"></div>
              <span class="name">Ім'я користувача</span>
            </li>
            <li @click="goToProfile" @keydown.enter="goToProfile" tabindex="0" role="button">
              Профіль
            </li>
            <li @click="goToLibrary" @keydown.enter="goToLibrary" tabindex="0" role="button">
              Бібліотека
            </li>
            <li @click="goToPurchaseHistory" @keydown.enter="goToPurchaseHistory" tabindex="0" role="button">
              Історія придбання
            </li>
            <li @click="goToCart" @keydown.enter="goToCart" tabindex="0" role="button">
              Мій кошик
            </li>
            <li @click="logout" @keydown.enter="logout" tabindex="0" role="button">
              Вихід
            </li>
          </ul>
        </div>
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
      userId: null,
      isLoginModalVisible: false,
      isSignupModalVisible: false,
      isRestorPasswordModalVisible: false,
      isSearchActive: false,
      isDropdownMenuVisible: false, // Переменная для управления дропдаун меню
      dropdownMenuPosition: { top: '0px', left: '0px' }, // Стиль для позиціонування меню
      isProfileDropdownVisible: false, // Для управління видимістю меню профілю
      profileDropdownPosition: { top: '0px', left: '0px' }, // Стиль для позиціонування меню профілю
    };
  },
  methods: {
    showLoginModal() {
      this.isLoginModalVisible = true;
    },
    showRestorPasswordModal() {
      this.isRestorPasswordModalVisible = true;
    },
    toggleDropdownMenu() {
      this.isDropdownMenuVisible = !this.isDropdownMenuVisible;

      if (this.isDropdownMenuVisible) {
        // Отримуємо координати кнопки "Книги ↓"
        const buttonRect = this.$refs.booksButton.getBoundingClientRect();
        this.dropdownMenuPosition = {
          top: `${buttonRect.bottom + window.scrollY + 10}px`, // Виправлення позиції
          left: `${buttonRect.left + window.scrollX - 30}px`,
        };
      }
    },
    toggleProfileDropdown() {
      this.isProfileDropdownVisible = !this.isProfileDropdownVisible;

      if (this.isProfileDropdownVisible) {
        // Отримуємо координати кнопки профілю
        const buttonRect = this.$refs.profileButton.getBoundingClientRect();
        this.profileDropdownPosition = {
          top: `${buttonRect.bottom + 10}px`, // Додатковий відступ
          left: `${buttonRect.left - 133}px`,
        };
      }
    },
    onSearchFocus() {
      this.isSearchActive = true;
    },
    onSearchBlur() {
      this.isSearchActive = false;
    },
    logout() {
      console.log('Вихід з профілю');
      this.$router.push({ name: 'LogoutPage' });
    },
    goToProfile() {
      console.log('Перехід до профілю');
      this.$router.push({ name: 'ProfilePage' });
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
      const token = localStorage.getItem('authToken');
      if (token) {
        this.userId = null;
      } else {
        this.userId = null;
      }
    },
    closeDropdownMenu() {
      this.isDropdownMenuVisible = false;
    },
  },
  created() {
    this.fetchUserId();
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
}

.books-button::before,
.library-button::before {
  content: '';
  position: absolute;
  top: -42px;
  bottom: -33px;
  left: -10px;
  right: -10px;
  background-color: #6E85B7;
  opacity: 0;
  transition: opacity 0.3s ease;
  border-radius: 10px;
  z-index: -1;
}

.books-button:hover::before,
.library-button:hover::before {
  opacity: 1;
}

.books-button:hover,
.library-button:hover {
  color: white;
  z-index: 1;
}

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

.auth-button-login {
  margin-right: 15px;
}

.auth-button-register {
  margin-left: 0;
}

.auth-button:hover {
  background-color: #5A6D9A;
  color: white;
}

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
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  width: 300px;
}

.dropdown-menu {
  position: absolute;
  background-color: white;
  border: 1px solid #ccc;
  box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.2);
  border-radius: 4px;
  width: 200px;
  z-index: 9999;
}

.dropdown-menu ul {
  list-style: none;
  margin: 0;
  padding: 0;
}

.dropdown-menu ul li {
  padding: 10px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.dropdown-menu ul li:hover {
  background-color: #f0f0f0;
}

.dropdown-menu ul li:last-child {
  border-bottom: none;
}

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

.profile-menu {
  margin-left: 20px;
}

.books-button::before,
.library-button::before {
  content: '';
  position: absolute;
  top: -42px;
  bottom: -33px;
  left: -10px;
  right: -10px;
  background-color: #6E85B7;
  opacity: 0;
  transition: opacity 0.3s ease;
  border-radius: 10px;
  z-index: -1;
}

.books-button:hover::before,
.library-button:hover::before {
  opacity: 1;
}

.books-button:hover,
.library-button:hover {
  color: white;
  z-index: 1;
}

.profile-dropdown-menu {
  position: absolute;
  background-color: #DDE4F0;
  border: 1px solid #ccc;
  box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.2);
  border-radius: 8px;
  width: 220px;
  z-index: 9999;
  top: 40px;
  left: -50px;
}

.profile-dropdown-menu ul {
  list-style: none;
  margin: 0;
  padding: 0;
}

.profile-dropdown-menu ul li {
  padding: 12px 15px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.profile-dropdown-menu ul li:hover {
  background-color: #e0e0e0;
}

.profile-info {
  padding: 12px 15px;
  border-bottom: 1px solid #ccc;
}

.profile-info .avatar {
  width: 35px;
  height: 35px;
  border-radius: 50%;
  background-color: #bbb;
  margin-right: 10px;
  display: inline-block;
  margin-top: 3px;
  /* Опускає аватар */
}

.profile-info .name {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  transform: translateY(-3px);
  /* Піднімає текст на 3px */
}

.profile-dropdown-menu ul li:last-child {
  border-bottom: none;
}
</style>
