<template>

  <head>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet">
  </head>
  <Header />
  <div class="book-page">
    <div class="breadcrumbs-container">
      <div class="breadcrumbs">
        <span>/ {{ book.genre || 'Жанр не вказано' }} /</span>
        <span class="breadcrumbs-book-title">{{ book.title || 'Назва книги' }}</span>
      </div>
    </div>
    <div class="content-wrapper">
      <div class="book-content">
        <div class="left-section">
          <div v-if="book.coverImage" class="image-placeholder">
            <img :src="book.coverImage" alt="Обкладинка книги" />
          </div>
          <div v-else class="image-placeholder">Обкладинка відсутня</div>
          <RatingStars :rating="book.rating || 0" />
        </div>
        <div class="right-section">
          <h1 class="book-title">{{ book.title || 'Назва книги' }}</h1>
          <p class="author">Автор: {{ book.author || '*Автор невідомий*' }}</p>
          <div class="tags-container">
            <span v-for="tag in book.tags || []" :key="tag" class="tag">{{ tag }}</span>
          </div>
          <p class="pages">Сторінок: {{ book.pages || 'N/A' }}</p>
          <p class="price">{{ book.price ? `${book.price} грн` : 'Ціна не вказана' }}</p>
          <div class="actions">
            <button class="action-button" @click="toggleCart">
              <img :src="require('@/assets/cart-icon.png')" alt="Cart Icon" class="icon" />
              {{ isInCart ? 'Видалити з кошика' : 'Додати до кошика' }}
            </button>
            <button class="action-button" @click="toggleFavorite">
              <img :src="isFavorite ? require('@/assets/active_heart.svg') : require('@/assets/inactive_heart.svg')"
                alt="Favorite Icon" class="icon" />
              {{ isFavorite ? 'Видалити з обраного' : 'Додати в обране' }}
            </button>
            <button class="action-button read-button">
              <img :src="require('@/assets/read-icon.png')" alt="Read Icon" class="icon" />
              Читати
            </button>
          </div>
        </div>
      </div>
      <div class="annotation">
        <p class="annotation-title">
          <strong>Анотація до книги "{{ book.title || 'Назва книги' }}"</strong>
        </p>
        <div class="annotation-text-container">
          <p class="annotation-text">{{ book.description || 'Опис відсутній.' }}</p>
        </div>
      </div>
    </div>
  </div>
  <Footer />
</template>

<script>
import axios from 'axios';
import RatingStars from '../components/RatingStars.vue';
import Header from '../components/HeaderComponent.vue';
import Footer from '../components/FooterComponent.vue';

export default {
  name: 'BookPage',
  props: {
    id: {
      type: String,
      required: true,
    },
  },
  components: {
    Header,
    RatingStars,
    Footer,
  },
  data() {
    return {
      book: {},
      userId: 'https://libum.yooud.org/api/users?id={{userId}}', // Замініть на правильний ідентифікатор користувача
      isFavorite: false, // Стан кнопки "Додати/Видалити з обраного"
      isInCart: false, // Стан кнопки "Додати/Видалити з кошика"
    };
  },
  mounted() {
    this.getBook(this.id);
    this.checkCartStatus();
  },
  methods: {
    async getBook(id) {
      try {
        const res = await axios.get(`https://libum.yooud.org/api/books?id=${id}`);
        this.book = res.data;
        console.log(res.data);
      } catch (error) {
        console.error('Error fetching book data:', error);
      }
    },
    async checkCartStatus() {
      try {
        const res = await axios.get();
        this.isInCart = res.data.some((item) => item.book_id === this.id);
        console.log('Cart status checked:', this.isInCart);
      } catch (error) {
        console.error('Error checking cart status:', error);
      }
    },
    toggleCart() {
      if (this.isInCart) {
        // Видалити з кошика
        axios.delete()
          .then((response) => {
            console.log('Book removed from cart', response.data);
            this.isInCart = false;
          })
          .catch((error) => {
            console.error('Error removing book from cart', error);
          });
      } else {
        // Додати до кошика
        axios.post()
          .then((response) => {
            console.log('Book added to cart', response.data);
            this.isInCart = true;
          })
          .catch((error) => {
            console.error('Error adding book to cart', error);
          });
      }
    },
    toggleFavorite() {
      if (this.isFavorite) {
        axios.post()
          .then((response) => {
            console.log('Book removed from favorites', response.data);
            this.isFavorite = false; // Зміна статусу на не в обраному
          })
          .catch((error) => {
            console.error('Error removing book from favorites', error);
          });
      } else {
        axios.post()
          .then((response) => {
            console.log('Book added to favorites', response.data);
            this.isFavorite = true; // Зміна статусу на в обраному
          })
          .catch((error) => {
            console.error('Error adding book to favorites', error);
          });
      }
    },
  },
};
</script>

<style scoped>
.book-page {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  padding: 0 50px;
  font-family: 'Roboto', sans-serif;
}

.breadcrumbs-container {
  min-width: 90%;
  display: flex;
  justify-content: flex-end;
  margin: 70px auto 30px;
}

.breadcrumbs {
  top: -50px;
  right: 20px;
  width: auto;
  height: 45px;
  opacity: 1;
  font-size: 32px;
  font-weight: 300;
  line-height: 44.8px;
  text-align: left;
  text-underline-position: from-font;
  text-decoration-skip-ink: none;
  margin-right: 135px;
}

.content-wrapper {
  position: relative;
  /* Контейнер для позиціонування внутрішніх елементів */
  min-width: 90%;
  border-radius: 20px;
  opacity: 1;
  background-color: #dde4f0;
  padding: 20px 24px;
  display: flex;
  flex-direction: column;
  gap: 20px;
  flex-grow: 1;
  margin: 0 auto;
  box-sizing: border-box;
}

/* Книга, обкладинка і права інформація */
.book-content {
  display: flex;
  gap: 20px;
  flex-wrap: wrap;
  width: 100%;
  justify-content: space-between;
}

.left-section {
  flex: 0 1 290px;
  max-width: 290px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 25px;
}

.image-placeholder {
  width: 100%;
  padding-top: 130%;
  background-color: #d9d9d9;
  border-radius: 20px;
}

.right-section {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 10px;
  max-width: 100%;
}

.icon {
  width: 20px;
  height: 20px;
}

.book-title {
  color: black;
  font-size: 48px;
  font-weight: 700;
  line-height: 56.25px;
  text-align: left;
  margin-bottom: 5px;
}

.author {
  font-size: 24px;
  font-weight: 500;
  line-height: 28.13px;
  text-align: left;
  color: #777;
}

.tags-container {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.tag {
  padding: 8px 16px;
  background: #3f5c8a;
  color: white;
  border-radius: 20px;
  font-size: 14px;
  text-align: center;
  white-space: nowrap;
  cursor: pointer;
}

.tag:hover {
  background-color: #36558f;
}

.pages,
.price {
  font-size: 24px;
  color: black;
  font-weight: 500;
  line-height: 28.13px;
  text-align: left;
}

.actions {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: flex-start;
}

.action-button {
  width: 200px;
  height: 40px;
  font-family: 'Montserrat', sans-serif;
  font-size: 14px;
  font-weight: 600;
  text-align: center;
  color: #3F5C8A;
  border: 1px solid #3F5C8A;
  border-radius: 12px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  position: relative;
  transition: background 0.3s ease, color 0.3s ease;
}

.action-button.active {
  background: #3F5C8A;
  color: white;
}

.action-button span {
  flex-grow: 1;
  text-align: center;
}

.action-button:hover {
  background: #3F5C8A;
  /* Змінюємо фон при наведенні на кнопки */
  color: white;
  /* Текст білим при наведенні */
}

/* Якщо кнопка в кошику */
.action-button.in-cart {
  font-family: 'Montserrat', sans-serif;
  background: #3F5C8A;
  border-color: #3F5C8A;
}

.action-button.in-favorite {
  background: #3F5C8A;
  color: white;
  border-color: #3F5C8A;
}

/* Кнопка у стандартному (не обраному) стані */
.action-buttonю.in-favorite:not(.in-favorite) {
  background: transparent;
  color: #3F5C8A;
  border-color: #3F5C8A;
}

/* Зміна кольору при наведені на кнопку, якщо вона в обраному стані */
.action-button.in-favorite:hover {
  background: #36558f;

  color: white;
}

.read-button {
  font-family: 'Montserrat', sans-serif;
  background-color: #3F5C8A;
  /* Синя заливка */
  color: white;
  /* Білий текст */
  border: 1px solid #3F5C8A;
  /* Синя межа */
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  width: 200px;
  height: 40px;
  font-size: 14px;
  font-weight: 400;
  border-radius: 12px;
}

.annotation {
  margin-top: 20px;
  padding: 20px 0;
  border-radius: 8px;
}

.annotation-title {
  font-family: Inter;
  color: black;
  font-size: 40px;
  font-weight: 700;
  line-height: 48.41px;
  text-align: left;
  text-indent: 56px;
}

.annotation-text-container {
  gap: 20px;
}

.annotation-text {
  font-family: Inter;
  font-size: 32px;
  font-weight: 400;
  line-height: 38.73px;
  text-align: left;
  text-indent: 56px;
}
</style>
