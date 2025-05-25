<template>
  <div v-if="isVisible" class="modal-overlay">
    <div class="modal-content">
      <button @click="closeModal" class="close-btn">&#10005;</button>
      <h2>З поверненням</h2>
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label for="email">
            Електронна пошта
            <input type="email" id="email" v-model="email" required placeholder="Введіть ваш email"
              @blur="validateEmail" />
          </label>
          <p v-if="emailError" class="error-message">{{ emailError }}</p>
        </div>

        <div class="form-group">
          <label for="password">
            Пароль
            <input type="password" id="password" v-model="password" required placeholder="Введіть ваш пароль"
              @blur="validatePassword" />
          </label>
          <p v-if="passwordError" class="error-message">{{ passwordError }}</p>
        </div>
        <div class="extra-options">
          <a href="#">Забули пароль?</a>
          <a href="#">Не маєте аккаунту?</a>
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
// import md5 from 'md5'; // Для хеширования пароля

export default {
  props: {
    isVisible: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      email: '',
      password: '',
      emailError: null,
      passwordError: null,
    };
  },
  computed: {
    isFormValid() {
      return !this.emailError && !this.passwordError && this.email && this.password;
    },
  },
  methods: {
    validateEmail() {
      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!this.email) {
        this.emailError = 'Email є обов\'язковим полем';
      } else if (!emailPattern.test(this.email)) {
        this.emailError = 'Некорректний формат Email';
      } else {
        this.emailError = null;
      }
    },
    validatePassword() {
      if (!this.password) {
        this.passwordError = 'Пароль є обов\'язковим полем';
      } else {
        this.passwordError = null;
      }
    },
    async submitForm() {
      try {
        const response = await this.$store.dispatch('postUserInfo', {
          email: this.email,
          password: this.password,
        });

        if (response && response.id) {
          this.$emit('close');
          this.$router.push({ name: 'MainPage' });
        }
      } catch (error) {
        console.error('Login error:', error);
        if (error.response) {
          // Handle specific error messages from the server
          const errorMessage = error.response.data.message || 'Помилка авторизації. Перевірте введені дані.';
          this.emailError = errorMessage;
          this.passwordError = errorMessage;
        } else {
          this.emailError = 'Помилка з\'єднання з сервером. Спробуйте пізніше.';
          this.passwordError = 'Помилка з\'єднання з сервером. Спробуйте пізніше.';
        }
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
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.3);
  display: flex;
  align-items: center;
  justify-content: center;
  transition: opacity 0.3s ease-in-out;
  z-index: 9999;
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
