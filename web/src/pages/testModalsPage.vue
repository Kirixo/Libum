<template>
  <div>
    <button @click="showModal">Открыть окно авторизации</button>
    <LoginModal v-if="isModalVisible" :isVisible="isModalVisible"
      @close="isModalVisible = false"
      @login="handleLogin" />
  </div>
</template>

<script>
// import LoginModal from './components/LoginModal.vue';
import LoginModal from '../components/LoginModalComponent.vue';

export default {
  components: { LoginModal },
  data() {
    return {
      isModalVisible: false,
    };
  },
  methods: {
    showModal() {
      this.isModalVisible = true;
    },
    handleLogin(credentials) {
      console.log('Вход выполнен с данными:', credentials);
      // Здесь можно добавить логику для обработки входа, например, отправку данных на сервер
      this.isModalVisible = false;
    },
  },
};
</script>
