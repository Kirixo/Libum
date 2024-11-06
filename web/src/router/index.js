import {
  createRouter,
  createWebHistory,
} from 'vue-router';
import MainPage from '@/pages/MainPage.vue';
import BookPage from '@/pages/BookPage.vue';

export default createRouter({
  history: createWebHistory(),
  routes: [{
    path: '/',
    name: 'MainPage',
    components: {
      default: MainPage,
    },
  }, {
    path: '/book',
    name: 'BookPage',
    component: BookPage,
  }],
});
