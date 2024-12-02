<template>
  <div>
    <button @click="showLoginModal">Открыть окно авторизации</button>
    <LoginModal v-if="isLoginModalVisible" :isVisible="isLoginModalVisible" @close="isLoginModalVisible = false"
      @login="handleLogin" />
  </div>
  <div>
    <button @click="showSignupModal">Открыть окно регерстрации</button>
    <Signup v-if="isSignupModalVisible" :isVisible="isSignupModalVisible" @close="isSignupModalVisible = false"
      @signup="handleSignup" />
  </div>
  <div>
    <button @click="showRestorPasswordModal">Открыть окно востановления пароля</button>
    <RestorPassword v-if="isRestorPasswordModalVisible" :isVisible="isRestorPasswordModalVisible" @close="isRestorPasswordModalVisible = false"
      @signup="handleRestorPassword" />
  </div>
  <Footer />
</template>

<script>
// import LoginModal from './components/LoginModal.vue';
import LoginModal from '../components/LoginModalComponent.vue';
import Signup from '../components/SignupModalComponent.vue';
import RestorPassword from '../components/PasswordRecoverComponent.vue';

export default {
  components: {
    LoginModal,
    Signup,
    RestorPassword,
  },
  data() {
    return {
      isLoginModalVisible: false,
      isSignupModalVisible: false,
      isRestorPasswordModalVisible: false,
    };
  },
  methods: {
    showLoginModal() {
      this.isLoginModalVisible = true;
    },
    showSignupModal() {
      this.isSignupModalVisible = true;
    },
    showRestorPasswordModal() {
      this.isRestorPasswordModalVisible = true;
    },
    handleLogin(credentials) {
      console.log('Вход выполнен с данными:', credentials);
      this.isLoginModalVisible = false;
    },
    handleSignup(credentials) {
      console.log('Вход выполнен с данными:', credentials);
      this.isSignupModalVisible = false;
    },
    handleRestorPassword(credentials) {
      console.log('Вход выполнен с данными:', credentials);
      this.isRestorPasswordModalVisible = false;
    },
  },
};
</script>
