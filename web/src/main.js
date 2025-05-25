import { createApp } from 'vue';
import axios from 'axios';
import App from './App.vue';
import router from './router';
import store from './store';

// Configure axios for API requests
axios.defaults.validateStatus = (status) => status >= 200 && status < 300;

// Add request interceptor
axios.interceptors.request.use(
  (config) => {
    const updatedConfig = { ...config };
    return updatedConfig;
  },
  (error) => Promise.reject(error),
);

// Add response interceptor
axios.interceptors.response.use(
  (response) => response,
  (error) => Promise.reject(error),
);

// createApp(App).mount('#app');
const app = createApp(App);

app.use(router)
  .use(store)
  .mount('#app');
