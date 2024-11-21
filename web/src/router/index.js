import {
  createRouter,
  createWebHistory,
} from 'vue-router';
import MainPage from '@/pages/MainPage.vue';
import BookPage from '@/pages/BookPage.vue';
import TestPage from '@/pages/testModalsPage.vue';

export default createRouter({
  history: createWebHistory(),
  routes: [{
    path: '/',
    name: 'MainPage',
    components: {
      default: MainPage,
    },
  }, {
    path: '/book/:id',
    name: 'BookPage',
    props: true,
    component: BookPage,
  },
  {
    path: '/test',
    name: 'TestPage',
    component: TestPage,
  }],
});
