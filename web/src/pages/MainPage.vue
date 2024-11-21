<template>
  <div>
    <Header />
    <h1 class="page-title">Топ у різних жанрах</h1>

    <div class="book-grid">
      <Book v-for="(book, index) in books" :key="index" :book="book" />
    </div>

    <div class="show-more">
      <button @click="loadMoreBooks">Показати ще</button>
    </div>

    <Footer />
  </div>
</template>

<script>
import Header from '../components/HeaderComponent.vue';
import Footer from '../components/FooterComponent.vue';
import Book from '../components/BookComponent.vue';

export default {
  name: 'MainPage',
  components: {
    Header,
    Footer,
    Book,
  },
  data() {
    return {
      books: [],
      page: 0,
    };
  },
  methods: {
    loadMoreBooks() {
      const newBooks = Array.from({ length: 24 }, (_, i) => ({
        id: `book-${this.page * 24 + i + 1}`,
        title: `Книга ${this.page * 24 + i + 1}`,
        cover: 'https://via.placeholder.com/150',
        price: `${(Math.random() * 200).toFixed(2)} грн`,
        rating: Math.ceil(Math.random() * 10),
        author: 'Автор',
        genres: ['Жанр 1', 'Жанр 2'],
        pages: Math.ceil(Math.random() * 500 + 100),
      }));
      this.books.push(...newBooks);
      this.page += 1;
    },
  },
  created() {
    this.loadMoreBooks();
  },
};
</script>

<style scoped>
.page-title {
  text-align: left;
  font-size: 2rem;
  margin: 1.5rem 0;
  margin-bottom: 5rem;
}

.book-grid {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  gap: 2rem;
  padding: 5rem;
  margin-top: 5rem;
}

.show-more {
  text-align: center;
  margin: 2rem 0;
}

.show-more button {
  padding: 0.8rem 1.5rem;
  font-size: 1rem;
  background-color: #ffffff;
  color: #5884C9;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.show-more button:hover {
  background-color: #f4f4f4;
}
</style>
