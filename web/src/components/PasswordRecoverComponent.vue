<template>
  <div v-if="isVisible" class="modal-overlay">
    <div class="modal-content">
      <button @click="closeModal" class="close-btn">&#10005;</button>
      <h2>Відновлення паролю</h2>
      <form @submit.prevent="submitForm">

        <div class="form-group">
          <p class="restore-email-info">
            Введіть свій адрес електронної пошти, та ми надішлемо вам інструкції по підновленню паролю
          </p>
        </div>

        <div class="form-group">
          <label for="email">
            Електронная пошта
            <input type="email" id="email" v-model="email" required placeholder="Введіть ваш email"
              @blur="validateEmail" />
          </label>
          <p v-if="emailError" class="error-message">{{ emailError }}</p>
        </div>
        <button type="submit" class="submit-btn" :disabled="!isFormValid">
          Продовжити
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
      email: '',
    };
  },
  computed: {
    isFormValid() {
      return (
        this.email
      );
    },
  },
  methods: {
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
    // async submitForm() {
    //   try {
    //     await this.$store.dispatch('postRegisterUser', {
    //       login: this.username,
    //       email: this.email,
    //       password: this.password,
    //     });
    //     // console.log('after post', this.$store.state.userInfo);

    //     if (this.$store.state.userInfo) {
    //       this.$router.push({ name: 'MainPage' });
    //     }
    //   } catch (error) {
    //     this.usernameError = 'Ошибка авторизации. Проверьте введенные данные.';
    //     this.emailError = 'Ошибка авторизации. Проверьте введенные данные.';
    //     this.passwordError = 'Ошибка авторизации. Проверьте введенные данные.';
    //   }
    // },
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

.restore-email-info{
    text-align: center;
}
</style>
