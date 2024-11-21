import { createStore } from 'vuex';
import axios from 'axios';

export default createStore({
  state: {
    userInfo: null,
  },
  mutations: {
    updateUserInfo(state, userInfo) {
      state.userInfo = userInfo;
      // console.log('assigned', state.userInfo);
    },
  },
  actions: {
    async postUserInfo({ commit }, { email, password }) {
      await axios.post('/path/users/login', {
        email,
        password,
      })
      // }, Headers {"authorization: $token"})
        .then(
          (result) => {
            commit('updateUserInfo', result.data);
          },
        )
        .catch(console.error);
      // todo: if 401 (unauthorized) -> redirect to login page
    },
    async postRegisterUser({ commit }, { login, email, password }) {
      // console.log('before reg', this.state.userInfo);
      await axios.post('/path/users/register', {
        login,
        email,
        password,
      })
        .then((result) => commit('updateUserInfo', result.data))
        .catch(console.error);
    },
  },
});
