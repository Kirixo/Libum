<template>
  <div class="main-page">
    <Header />
    <div class="content-wrapper">
      <h1 class="page-title">{{ pageTitle }}</h1>

      <div class="book-grid">
        <Book v-for="(book, index) in displayedBooks" :key="index" :book="book" />
      </div>

      <!-- Pagination Controls -->
      <div class="pagination-controls">
        <!-- Load More Button -->
        <div class="show-more" v-if="hasMoreBooksToShow">
          <div
            @click="showMoreBooks"
            @keydown.enter="showMoreBooks"
            @keydown.space.prevent="showMoreBooks"
            :class="['load-more-text', { disabled: loading }]"
            tabindex="0"
            role="button"
          >
            <img src="@/assets/loading-icon.svg" alt="Loading" class="loading-icon" :class="{ 'hidden': !loading }" />
            <span>{{ loading ? 'Завантаження...' : 'Показати ще' }}</span>
          </div>
        </div>

        <!-- Page Navigation -->
        <div class="page-navigation">
          <button
            class="page-nav-btn"
            :disabled="currentPage === 1"
            @click="changePage(currentPage - 1)"
          >
            &lt;
          </button>

          <div class="page-numbers">
            <button
              v-for="pageNum in displayedPageNumbers"
              :key="pageNum"
              :class="['page-number', { active: pageNum === currentPage }]"
              @click="changePage(pageNum)"
            >
              {{ pageNum }}
            </button>
            <span v-if="showEllipsis" class="ellipsis">...</span>
            <button
              v-if="showLastPage"
              :class="['page-number', { active: totalPages === currentPage }]"
              @click="changePage(totalPages)"
            >
              {{ totalPages }}
            </button>
          </div>

          <button
            class="page-nav-btn"
            :disabled="currentPage === totalPages"
            @click="changePage(currentPage + 1)"
          >
            &gt;
          </button>
        </div>
      </div>
    </div>
    <Footer />
  </div>
</template>

<script>
import axios from 'axios';
import { mapState, mapGetters } from 'vuex';
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
      allBooks: [],
      displayLimit: 24,
      loading: false,
      searchQuery: '',
      paginationType: 'pages', // 'load-more' or 'pages'
    };
  },
  computed: {
    ...mapState(['selectedGenreId', 'pagination']),
    ...mapGetters(['selectedGenreName']),
    pageTitle() {
      if (this.searchQuery) {
        return `Результати пошуку "${this.searchQuery}"`;
      }
      return this.selectedGenreName ? `Топ у жанрі "${this.selectedGenreName}"` : 'Топ у різних жанрах';
    },
    filteredBooks() {
      let filtered = this.allBooks;
      if (this.selectedGenreId) {
        filtered = filtered.filter((book) => book.genres.some((genre) => genre.id === this.selectedGenreId));
      }
      if (this.searchQuery) {
        const searchLower = this.searchQuery.toLowerCase();
        filtered = filtered.filter((book) => (
          book.title.toLowerCase().includes(searchLower)
          || book.author.toLowerCase().includes(searchLower)
          || book.description.toLowerCase().includes(searchLower)
        ));
      }
      return filtered;
    },
    displayedBooks() {
      if (this.paginationType === 'load-more') {
        return this.filteredBooks.slice(0, this.displayLimit);
      }
      const start = (this.currentPage - 1) * this.pagination.booksPerPage;
      return this.filteredBooks.slice(start, start + this.pagination.booksPerPage);
    },
    hasMoreBooksToShow() {
      return this.displayLimit < this.filteredBooks.length;
    },
    currentPage() {
      return this.pagination.currentPage;
    },
    totalPages() {
      return Math.ceil(this.filteredBooks.length / this.pagination.booksPerPage);
    },
    displayedPageNumbers() {
      const current = this.currentPage;
      const total = this.totalPages;
      const delta = 2;
      let range = [];

      // Always show the first page
      if (total >= 1) {
        range.push(1);
      }

      // Determine the range of pages around the current one
      for (let i = Math.max(2, current - delta); i <= Math.min(total - 1, current + delta); i += 1) {
        range.push(i);
      }

      // Add the last page if it exists and is not already in the range
      if (total > 1 && !range.includes(total)) {
        range.push(total);
      }

      // Sort and remove duplicates to handle edge cases where pages might be pushed multiple times
      range = [...new Set(range)].sort((a, b) => a - b);

      // Add ellipses where needed
      const withDots = [];
      let prevNum = null;

      range.forEach((num) => {
        if (prevNum && num - prevNum > 1) {
          withDots.push('...');
        }
        withDots.push(num);
        prevNum = num;
      });

      return withDots;
    },
    showEllipsis() {
      return this.totalPages > 7 && this.displayedPageNumbers.includes('...');
    },
    showLastPage() {
      return this.totalPages > 1 && !this.displayedPageNumbers.includes(this.totalPages);
    },
  },
  methods: {
    async fetchBooks() {
      if (this.loading) return;
      this.loading = true;
      try {
        // Завантажуємо всі книги одразу
        const response = await axios.get('api/books/list?limit=100');
        this.allBooks = response.data.books;
        // Оновлюємо інформацію про пагінацію
        this.$store.commit('setPaginationInfo', {
          currentPage: this.currentPage,
          totalPages: Math.ceil(response.data.total / this.pagination.booksPerPage),
          totalBooks: response.data.total,
        });
      } catch (error) {
        console.error('Error fetching books:', error);
        this.allBooks = [];
        this.$store.commit('setPaginationInfo', {
          currentPage: 1,
          totalPages: 1,
          totalBooks: 0,
        });
      } finally {
        this.loading = false;
      }
    },
    showMoreBooks() {
      if (!this.loading) {
        this.displayLimit += this.pagination.booksPerPage;
        this.paginationType = 'load-more';
        this.$store.commit('setCurrentPage', Math.ceil(this.displayLimit / this.pagination.booksPerPage));
      }
    },
    changePage(page) {
      if (page >= 1 && page <= this.totalPages && page !== this.currentPage) {
        this.paginationType = 'pages';
        this.$store.commit('setCurrentPage', page);
        // Прокручуємо сторінку вгору при зміні сторінки
        window.scrollTo({ top: 0, behavior: 'smooth' });
      }
    },
    updateSearchQuery(query) {
      this.searchQuery = query;
      this.displayLimit = this.pagination.booksPerPage;
      this.$store.commit('setCurrentPage', 1);
      this.paginationType = 'pages';
    },
  },
  watch: {
    selectedGenreId() {
      this.displayLimit = this.pagination.booksPerPage;
      this.$store.commit('setCurrentPage', 1);
      this.paginationType = 'pages';
      this.fetchBooks(); // Перезавантажуємо книги тільки при зміні жанру
    },
  },
  created() {
    this.fetchBooks();
  },
};
</script>

<style scoped>
.main-page {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.content-wrapper {
  max-width: 1440px;
  margin: 0 auto;
  padding: 0 20px;
  width: 100%;
  flex: 1;
}

.page-title {
  text-align: left;
  font-size: 2rem;
  margin: 5rem;
  margin-bottom: 5rem;
}

.book-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(156px, 1fr));
  gap: 2rem;
  padding: 0 2rem;
  margin-top: 2rem;
  justify-items: center;
}

.pagination-controls {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 3rem 0;
  gap: 2rem;
}

.show-more {
  margin-bottom: 1rem;
}

.load-more-text {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  color: #5884c9;
  font-size: 1rem;
  cursor: pointer;
  transition: all 0.3s ease;
  user-select: none;
  outline: none;
}

.loading-icon {
  width: 20px;
  height: 20px;
  animation: spin 1s linear infinite;
  opacity: 1;
  transition: opacity 0.3s ease;
}

.loading-icon.hidden {
  opacity: 0;
  width: 20px;
  margin-right: -20px;
}

.load-more-text:hover {
  color: #3F5C8A;
}

.load-more-text.disabled {
  color: #cccccc;
  cursor: not-allowed;
}

.load-more-text:focus-visible {
  box-shadow: 0 0 0 2px #5884c9;
  border-radius: 4px;
}

.page-navigation {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  background-color: #ffffff;
  padding: 0.5rem;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.page-nav-btn {
  padding: 0.5rem 0.75rem;
  background-color: #ffffff;
  color: #5884c9;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 0.9rem;
}

.page-nav-btn:hover:not(:disabled) {
  background-color: #f0f4f8;
  color: #3F5C8A;
}

.page-nav-btn:disabled {
  color: #cccccc;
  cursor: not-allowed;
  background-color: transparent;
}

.page-numbers {
  display: flex;
  align-items: center;
  gap: 0.25rem;
}

.page-number {
  min-width: 2rem;
  height: 2rem;
  padding: 0;
  background-color: #ffffff;
  color: #5884c9;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0.9rem;
}

.page-number:hover:not(.active) {
  background-color: #f0f4f8;
  color: #3F5C8A;
}

.page-number.active {
  background-color: #5884c9;
  color: #ffffff;
  font-weight: 500;
}

.ellipsis {
  color: #5884c9;
  padding: 0 0.25rem;
  user-select: none;
}

@keyframes spin {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

@media (min-width: 1440px) {
  .book-grid {
    grid-template-columns: repeat(6, 1fr);
  }
}

@media (max-width: 1200px) {
  .book-grid {
    grid-template-columns: repeat(auto-fill, minmax(156px, 1fr));
    gap: 1.5rem;
  }
}

@media (max-width: 768px) {
  .content-wrapper {
    padding: 0 10px;
  }

  .page-title {
    margin: 3rem 2rem;
    font-size: 1.5rem;
  }

  .book-grid {
    padding: 0 1rem;
    gap: 1rem;
  }

  .pagination-controls {
    margin: 2rem 0;
  }
}
</style>
