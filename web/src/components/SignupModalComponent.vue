<template>
  <div v-if="isVisible" class="modal-overlay">
    <div class="modal-content">
      <button @click="closeModal" class="close-btn">&#10005;</button>
      <h2>Створення облікового запису</h2>
      <form @submit.prevent="submitForm">

        <div class="form-group">
          <label for="username">
            Логін
            <input type="text" id="username" v-model="username"
             required placeholder="Введіть ваш логін"
              @blur="validateUsername" />
          </label>
          <p v-if="usernameError" class="error-message">{{ usernameError }}</p>
        </div>

        <div class="form-group">
          <label for="email">
            Електронная пошта
            <input type="email" id="email" v-model="email" required placeholder="Введіть ваш email"
              @blur="validateEmail" />
          </label>
          <p v-if="emailError" class="error-message">{{ emailError }}</p>
        </div>

        <div class="form-group">
          <label for="password">
            Пароль
            <input type="password" id="password" v-model="password"
             required placeholder="Введіть ваш пароль"
              @blur="validatePassword" />
          </label>
          <p v-if="passwordError" class="error-message">{{ passwordError }}</p>
        </div>

        <div class="form-group">
          <label for="confirmPassword">
            Підтвердження пароля
            <input type="password" id="confirmPassword" v-model="confirmPassword" required
              placeholder="Підтвердіть ваш пароль" @blur="validateConfirmPassword" />
          </label>
          <p v-if="confirmPasswordError" class="error-message">{{ confirmPasswordError }}</p>
        </div>

        <div class="form-group">
            Капча может будет, может нет
        </div>

        <div class="extra-options">
          <a href="#">У вас уже есть аккаунт?</a>
        </div>

        <button type="submit" class="submit-btn" :disabled="!isFormValid">
          Продовжити
        </button>
        <button type="button" class="google-btn" @click="signInWithGoogle">
          <img src="https://upload.wikimedia.org/wikipedia/commons/c/c1/Google_%22G%22_logo.svg" alt="Google logo"
            class="google-logo" />
          Продовжити з Google
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import md5 from 'md5'; // Для хеширования пароля

export default {
  props: {
    isVisible: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      username: '',
      email: '',
      password: '',
      confirmPassword: '',
      usernameError: null,
      emailError: null,
      passwordError: null,
      confirmPasswordError: null,
    };
  },
  computed: {
    isFormValid() {
      return (
        !this.usernameError
        && !this.emailError
        && !this.passwordError
        && !this.confirmPasswordError
        && this.username
        && this.email
        && this.password
        && this.confirmPassword
      );
    },
  },
  methods: {
    validateUsername() {
      if (!this.username) {
        this.usernameError = 'Логін є обов\'язковим полем';
      } else if (this.username.length < 3) {
        this.usernameError = 'Логін повинен бути не менше ніж 3 символи';
      } else {
        this.usernameError = null;
      }
    },
    validateEmail() {
      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!this.email) {
        this.emailError = 'Email є обов\'язковим полем';
      } else if (!emailPattern.test(this.email)) {
        this.emailError = 'Некоректний формат Email';
      } else {
        this.emailError = null;
      }
    },
    validatePassword() {
      if (!this.password) {
        this.passwordError = 'Пароль є обов\'язковим полем';
      } else if (this.password.length < 6) {
        this.passwordError = 'Пароль повинен бути не менше ніж 6 символів';
      } else {
        this.passwordError = null;
      }
    },
    validateConfirmPassword() {
      if (!this.confirmPassword) {
        this.confirmPasswordError = 'Підтвердження пароля є обов\'язковим полем';
      } else if (this.confirmPassword !== this.password) {
        this.confirmPasswordError = 'Паролі не співпадають';
      } else {
        this.confirmPasswordError = null;
      }
    },
    async submitForm() {
      try {
        const hashedPassword = md5(this.password);
        await this.$store.dispatch('postRegisterUser', {
          login: this.username,
          email: this.email,
          password: hashedPassword,
        });
      } catch (error) {
        this.usernameError = 'Ошибка авторизации. Проверьте введенные данные.';
        this.emailError = 'Ошибка авторизации. Проверьте введенные данные.';
        this.passwordError = 'Ошибка авторизации. Проверьте введенные данные.';
      }
    },
    signInWithGoogle() {
      // todo
      console.log('Авторизация через Google');
    },
    closeModal() {
      this.$emit('close');
    },
  },
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.3);
  display: flex;
  align-items: center;
  justify-content: center;
  transition: opacity 0.3s ease-in-out;
}

.modal-content {
  background: #fff;
  border-radius: 8px;
  padding: 40px;
  width: 350px;
  text-align: center;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  position: relative;
}

h2 {
  font-size: 1.5rem;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 20px;
  text-align: left;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input {
  width: 100%;
  padding: 10px;
  border: 2px solid #b4c3dd;
  border-radius: 12px;
  font-size: 1rem;
  box-sizing: border-box;
}

input:focus {
  border-color: #5783C8;
  outline: none;
}

button.submit-btn {
  width: 100%;
  padding: 12px;
  background-color: #5783C8;
  color: #fff;
  border: none;
  border-radius: 12px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s;
}

button.submit-btn:disabled {
  background-color: #ddd;
}

button.submit-btn:hover:not(:disabled) {
  background-color: #5783C8;
}

button.google-btn {
  width: 100%;
  padding: 12px;
  margin-top: 8px;
  background-color: #fff;
  color: #444;
  border: 2px solid #b4c3dd;
  border-radius: 12px;
  font-size: 1rem;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background-color 0.3s;
}

button.google-btn:hover {
  background-color: #5783C8;
}

.google-logo {
  width: 18px;
  height: 18px;
  margin-right: 8px;
}

button.close-btn {
  position: absolute;
  top: 10px;
  left: 10px;
  background-color: transparent;
  border: none;
  font-size: 30px;
  color: #000000;
  cursor: pointer;
  transition: color 0.3s;
}

button.close-btn:hover {
  color: #3d74cc;
}

.error-message {
  color: #f44336;
  font-size: 0.875rem;
  margin-top: 5px;
}

.extra-options {
  margin-bottom: 20px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  text-align: left;
}

.extra-options a {
  color: #4285f4;
  text-decoration: none;
  margin-bottom: 10px;
}

.extra-options a:hover {
  text-decoration: underline;
}
</style>
