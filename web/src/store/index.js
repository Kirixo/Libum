import { createStore } from 'vuex';
import axios from 'axios';

export default createStore({
  state: {
    userInfo: JSON.parse(localStorage.getItem('userInfo')) || null,
    cartBooks: [],
    genres: [],
    selectedGenreId: null,
    searchQuery: '',
    pagination: {
      currentPage: 1,
      totalPages: 1,
      booksPerPage: 24,
      totalBooks: 0,
    },
  },
  mutations: {
    updateUserInfo(state, userInfo) {
      state.userInfo = userInfo;
      if (userInfo) {
        localStorage.setItem('userInfo', JSON.stringify(userInfo));
      } else {
        localStorage.removeItem('userInfo');
      }
    },
    setCartBooks(state, books) {
      state.cartBooks = books;
    },
    removeBookFromCart(state, bookId) {
      state.cartBooks = state.cartBooks.filter((book) => book.id !== bookId);
    },
    setGenres(state, genres) {
      state.genres = genres;
    },
    setSelectedGenreId(state, genreId) {
      state.selectedGenreId = genreId;
    },
    setSearchQuery(state, query) {
      state.searchQuery = query;
    },
    setPaginationInfo(state, { currentPage, totalPages, totalBooks }) {
      state.pagination.currentPage = currentPage;
      state.pagination.totalPages = totalPages;
      state.pagination.totalBooks = totalBooks;
    },
    setCurrentPage(state, page) {
      state.pagination.currentPage = page;
    },
  },
  getters: {
    selectedGenreName: (state) => {
      if (!state.selectedGenreId) return null;
      const genre = state.genres.find((g) => g.id === state.selectedGenreId);
      return genre ? genre.name : null;
    },
    getGenreNameById: (state) => (id) => {
      const genre = state.genres.find((g) => g.id === id);
      return genre ? genre.name : null;
    },
    isAuthenticated: (state) => !!state.userInfo,
  },
  actions: {
    async postUserInfo({ commit }, { email, password }) {
      try {
        const response = await axios.post('/api/users/login', {
          email,
          password,
        });
        commit('updateUserInfo', response.data);
        return response.data;
      } catch (error) {
        console.error('Login error:', error);
        throw error;
      }
    },
    async postRegisterUser({ commit }, { login, email, password }) {
      try {
        const response = await axios.post('/api/users/register', {
          login,
          email,
          password,
        });
        commit('updateUserInfo', response.data);
        return response.data;
      } catch (error) {
        console.error('Registration error:', error);
        throw error;
      }
    },
    logout({ commit }) {
      commit('updateUserInfo', null);
    },
    async fetchCartBooks({ commit, state }) {
      if (!state.userInfo) return;
      try {
        // Extract numeric ID from userInfo.id
        const userId = String(state.userInfo.id).split(':')[0];
        const response = await axios.get(`/api/cart?user_id=${userId}`);
        commit('setCartBooks', response.data.books);
      } catch (error) {
        console.error('Error loading cart:', error);
        commit('setCartBooks', []);
      }
    },
    async removeBook({ commit, state }, bookId) {
      if (!state.userInfo) return;
      try {
        // Extract numeric ID from userInfo.id
        const userId = String(state.userInfo.id).split(':')[0];
        await axios.delete(`/api/cart?book_id=${bookId}&user_id=${userId}`);
        commit('removeBookFromCart', bookId);
      } catch (error) {
        console.error('Error removing book:', error);
      }
    },
    async fetchGenres({ commit }) {
      try {
        const response = await axios.get('/api/books/genres');
        commit('setGenres', response.data.genres);
      } catch (error) {
        console.error('Error fetching genres:', error);
        commit('setGenres', []);
      }
    },
    selectGenre({ commit }, genreId) {
      commit('setSelectedGenreId', genreId);
    },
    setSearchQuery({ commit }, query) {
      commit('setSearchQuery', query);
    },
  },
});
