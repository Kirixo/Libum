import { createStore } from 'vuex';
import axios from 'axios';

export default createStore({
  state: {
    userInfo: null,
    cartBooks: [],
  },
  mutations: {
    updateUserInfo(state, userInfo) {
      state.userInfo = userInfo;
      // console.log('assigned', state.userInfo);
    },
    setCartBooks(state, books) {
      state.cartBooks = books; // Обновление списка книг в корзине
    },
    removeBookFromCart(state, bookId) {
      state.cartBooks = state.cartBooks.filter((book) => book.id !== bookId);
    },
  },
  actions: {
    async postUserInfo({ commit }, { email, password }) {
      await axios.post('/api/users/login', {
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
      await axios.post('/api/users/register', {
        login,
        email,
        password,
      })
        .then((result) => commit('updateUserInfo', result.data))
        .catch(console.error);
    },
    async fetchCartBooks({ commit }) {
      try {
        console.log(this.state.userInfo);

        const response = await axios.get(`/api/cart?user_id=${this.state.userInfo.id}`);
        // const response = await axios.get('https://literate-vastly-pony.ngrok-free.app/api/cart?user_id=3');
        console.log(response);
        const cartBooks = response.data.books;
        console.log(cartBooks);
        commit('setCartBooks', cartBooks);

        console.log(this.state.cartBooks);
      } catch (error) {
        console.error('Ошибка при загрузке корзины:', error);
        commit('setCartBooks', []);
      }
    },
    // Новый экшн для удаления книги из корзины
    async removeBook({ commit }, bookId) {
      try {
        console.log(this.state.userInfo);

        await axios.delete(`/api/cart?book_id=${bookId}&user_id=${this.state.userInfo.id}`);
        commit('removeBookFromCart', bookId);
      } catch (error) {
        console.error('Ошибка при удалении книги:', error);
      }
    },
  },
});
