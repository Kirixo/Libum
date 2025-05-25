<template>
  <div id="app">
    <router-view></router-view>
  </div>
  <header class="header">
    <div class="logo" @click="goToHomePage" @keydown.enter="goToHomePage" @keydown.space="goToHomePage" tabindex="0"
      role="button">
      <img :src="require('@/assets/Logo.svg')" alt="Logo" class="logo-image" />
    </div>
    <nav class="navigation">
      <button class="books-button" @click="toggleDropdownMenu" ref="booksButton" :class="{ active: isDropdownMenuVisible }">
        {{ selectedGenreText }}
      </button>
      <div v-if="isDropdownMenuVisible" class="dropdown-menu" :style="dropdownMenuPosition">
        <ul>
          <li>Жанри</li>
          <li
            @click="selectGenre(null)"
            @keydown.enter="selectGenre(null)"
            :class="{ active: !$store.state.selectedGenreId }"
            tabindex="0"
            role="button"
          >
            Всі жанри
          </li>
          <li
            v-for="genre in $store.state.genres"
            :key="genre.id"
            @click="selectGenre(genre.id)"
            @keydown.enter="selectGenre(genre.id)"
            :class="{ active: $store.state.selectedGenreId === genre.id }"
            tabindex="0"
            role="button"
          >
            {{ genre.name }}
          </li>
        </ul>
      </div>
      <button class="library-button">Бібліотека</button>
    </nav>
    <div class="search-profile">
      <div class="search-container">
        <div class="search-wrapper">
          <input
            id="search-input"
            type="text"
            class="search-input"
            placeholder="Пошук"
            v-model="searchQuery"
            @input="onSearchInput"
            aria-label="Введіть текст для пошуку"
            @focus="onSearchFocus"
            @blur="onSearchBlur"
          />
          <img
            :src="isSearchActive ? require('@/assets/active_search.svg') : require('@/assets/inactive_search.svg')"
            alt="Іконка"
            class="search-icon"
          />
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
        <!-- Кнопка "Реєстрація" -->
        <button class="auth-button auth-button-register" @click="showSignupModal">Реєстрація</button>
        <!-- Вызов компонента Signup -->
        <Signup v-if="isSignupModalVisible" :isVisible="isSignupModalVisible" @close="isSignupModalVisible = false"
          @signup="handleSignup" />
      </template>
    </div>
  </header>
</template>

<script>
import { mapState, mapActions, mapGetters } from 'vuex';
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
      isDropdownMenuVisible: false,
      dropdownMenuPosition: { top: '0px', left: '0px' },
      isProfileDropdownVisible: false,
      profileDropdownPosition: { top: '0px', left: '0px' },
      searchQuery: '',
    };
  },
  computed: {
    ...mapState(['selectedGenreId']),
    ...mapGetters(['selectedGenreName']),
    selectedGenreText() {
      return this.selectedGenreName || 'Книги';
    },
  },
  methods: {
    ...mapActions(['fetchGenres', 'selectGenre']),
    selectGenre(genreId) {
      this.$store.dispatch('selectGenre', genreId);
      this.isDropdownMenuVisible = false;
      this.$router.push({ name: 'MainPage', query: { genre: genreId || undefined } });
    },
    goToHomePage() {
      this.$router.push('/');
    },
    showLoginModal() {
      this.isLoginModalVisible = true;
    },
    showSignupModal() {
      // Открытие модального окна регистрации
      this.isSignupModalVisible = true;
    },
    toggleDropdownMenu() {
      this.isDropdownMenuVisible = !this.isDropdownMenuVisible;

      if (this.isDropdownMenuVisible) {
        // Отримуємо координати кнопки "Книги ↓"
        const buttonRect = this.$refs.booksButton.getBoundingClientRect();
        this.dropdownMenuPosition = {
          top: `${buttonRect.bottom + window.scrollY + 10}px`,
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
          top: `${buttonRect.bottom + 10}px`,
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
      this.$store.dispatch('logout');
      this.$router.push({ name: 'MainPage' });
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
    goToCart() {
      console.log('Перехід до кошика');
      this.$router.push({ name: 'CartPage' });
    },
    onSearchInput() {
      // Емітимо подію пошуку до батьківського компонента
      this.$parent.updateSearchQuery(this.searchQuery);
    },
  },
  async created() {
    await this.fetchGenres();
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
  border: 1px solid #dde4f0;
  box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  width: 250px;
  z-index: 9999;
  padding: 10px 0;
}

.dropdown-menu ul {
  list-style: none;
  margin: 0;
  padding: 0;
}

.dropdown-menu ul li {
  padding: 12px 20px;
  cursor: pointer;
  transition: background-color 0.2s;
  color: #333;
  font-size: 16px;
  display: flex;
  align-items: center;
}

.dropdown-menu ul li:first-child {
  font-weight: 600;
  border-bottom: 1px solid #dde4f0;
  margin-bottom: 5px;
}

.dropdown-menu ul li:hover {
  background-color: #f5f7fa;
}

.dropdown-menu ul li.active {
  background-color: #dde4f0;
  color: #333;
}

.dropdown-menu ul li.active:hover {
  background-color: #d0d7e3;
}

.books-button {
  padding: 10px 20px;
  background-color: transparent;
  color: #333;
  font-size: 16px;
  cursor: pointer;
  border: none;
  position: relative;
  transition: color 0.3s ease;
  display: flex;
  align-items: center;
  gap: 5px;
}

.books-button::after {
  content: '↓';
  margin-left: 5px;
  transition: transform 0.3s;
}

.books-button.active::after {
  transform: rotate(180deg);
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
}

.profile-info .name {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  transform: translateY(-3px);
}

.profile-dropdown-menu ul li:last-child {
  border-bottom: none;
}
</style>
