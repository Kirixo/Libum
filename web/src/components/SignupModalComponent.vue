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
    },
    validateEmail() {
    },
    validatePassword() {
    },
    validateConfirmPassword() {
    },
    async submitForm() {
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
