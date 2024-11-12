import { createStore } from 'vuex';
import axios from 'axios';

export default createStore({
  state: {
    userInfo: null,
  },
  mutations: {
    updateUserInfo(state, userInfo) {
      state.userInfo = userInfo;
    },
  },
  actions: {
    postUserInfo({ commit }, { email, password }) {
      axios.post('/path/users/login', {
        email,
        password,
      })
        .then((result) => commit('updateUserInfo', result.data))
        .catch(console.error);
    },
  },
});
