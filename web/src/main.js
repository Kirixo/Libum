import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';

// createApp(App).mount('#app');
const app = createApp(App);

app.use(router)
  .use(store)
  .mount('#app');
