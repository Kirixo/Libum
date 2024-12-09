<template>
  <Header />
  <div class="book-page">
    <div class="breadcrumbs-container">
      <div class="breadcrumbs">
        <span>/ Сучасна проза /</span>
        <span class="breadcrumbs-book-title">{{ bookTitle }}</span>
      </div>
    </div>
    <div class="content-wrapper">
      <div class="book-content">
        <div class="left-section">
          <div class="image-placeholder"></div>
          <RatingStars :rating="bookRating" />
        </div>
        <div class="right-section">
          <h1 class="book-title">{{ bookTitle }}</h1>
          <p class="author">{{ author }}</p>
          <div class="tags-container">
            <span v-for="(tag, index) in tags" :key="index" class="tag">{{ tag }}</span>
          </div>
          <p class="pages">Сторінок: {{ pages }}</p>
          <p class="price">{{ price }} грн</p>
          <div class="actions">
            <button class="action-button" @click="addToCart">
              <img :src="require('@/assets/cart-icon.png')" alt="Cart Icon" class="icon" />
              Додати до кошику
            </button>
            <!-- Кнопка "Додати до обраного" або "Видалити з обраного" -->
            <button v-if="!isFavorite" class="action-button" @click="toggleFavorite">
              <img :src="require('@/assets/favorite-icon.png')" alt="Favorite Icon" class="icon" />
              Додати в обране
            </button>
            <button v-else class="action-button" @click="toggleFavorite">
              <img :src="require('@/assets/favorite-icon.png')" alt="Favorite Icon" class="icon" />
              Видалити з обраного
            </button>
            <button class="action-button">
              <img :src="require('@/assets/read-icon.png')" alt="Read Icon" class="icon" />
              Читати
            </button>
          </div>
        </div>
      </div>
      <div class="annotation">
        <p class="annotation-title"><strong>Анотація до книги "{{ bookTitle }}"</strong></p>
        <div class="annotation-text-container">
          <p class="annotation-text">{{ annotation }}</p>
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
      default: '',
    },
  },
  components: {
    Header,
    RatingStars,
    Footer,
  },
  data() {
    return {
      bookTitle: 'Назва книги',
      author: 'Автор',
      tags: ['Тег 1', 'Тег 2', 'Тег 3'],
      pages: 201,
      price: 300,
      isFavorite: false,
      bookRating: 4.5,
      annotation: 'Тут буде анотація до книги...',
    };
  },
  mounted() {
    if (this.id) {
      this.getBook(this.id);
    }
  },
  methods: {
    getBook(id) {
      axios
        .get('https://literate-vastly-pony.ngrok-free.app/api/books', {
          params: { id },
        })
        .then((res) => {
          const bookData = res.data;
          this.bookTitle = bookData.title || this.bookTitle;
          this.author = bookData.author || this.author;
          this.tags = bookData.tags || this.tags;
          this.pages = bookData.pages || this.pages;
          this.price = bookData.price || this.price;
          this.bookRating = bookData.rating || this.bookRating;
          this.annotation = bookData.annotation || this.annotation;
          this.isFavorite = bookData.isFavorite || this.isFavorite;
        })
        .catch((error) => {
          console.error('Error fetching book data:', error);
        });
    },
    addToCart() {
      // Додаємо книгу до кошику
      console.log('Додано до кошику');
    },
    toggleFavorite() {
      // Заглушка для запиту на сервер
      if (this.isFavorite) {
        this.removeFromFavorites();
      } else {
        this.addToFavorites();
      }
    },
    addToFavorites() {
      // Замість цього виконати реальний запит для додавання в обране
      axios.post('https://your-api-endpoint.com/favorites', { bookId: this.id })
        .then(() => {
          this.isFavorite = true;
          console.log('Книга додана до обраного');
        })
        .catch((error) => {
          console.error('Помилка додавання до обраного:', error);
        });
    },
    removeFromFavorites() {
      // Замість цього виконати реальний запит для видалення з обраного
      axios.delete('https://your-api-endpoint.com/favorites', { data: { bookId: this.id } })
        .then(() => {
          this.isFavorite = false;
          console.log('Книга видалена з обраного');
        })
        .catch((error) => {
          console.error('Помилка видалення з обраного:', error);
        });
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
  /* Відступаємо від верхнього краю контейнера */
  right: 20px;
  /* Відступ від правого краю */
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
  /* Дозволяє елементам переноситися на новий рядок */
  width: 100%;
  justify-content: space-between;
  /* Вирівнює елементи по ширині */
}

.left-section {
  flex: 0 1 290px;
  /* Фіксована ширина для лівої секції */
  max-width: 290px;
  /* Максимальна ширина */
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 25px;
  /* Збільшуємо відступ між зображенням і зірочками */
}

.image-placeholder {
  width: 100%;
  padding-top: 130%;
  /* Щоб зробити обкладинку книги пропорційною */
  background-color: #d9d9d9;
  border-radius: 20px;
}

.right-section {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 10px;
  max-width: 100%;
  /* Забезпечує, що контент не виходить за межі */
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
  /* Дозволяє кнопкам переноситися */
  gap: 20px;
  /* Відстань між кнопками */
  justify-content: flex-start;
}

.action-button {
  width: 200px;
  /* Фіксована ширина кнопки */
  height: 40px;
  /* Фіксована висота кнопки */
  font-size: 14px;
  font-weight: 700;
  text-align: center;
  /* Текст вирівнюється по центру кнопки */
  background: #6e85b7;
  color: white;
  border: 1px solid #ccc;
  border-radius: 12px;
  cursor: pointer;
  display: flex;
  align-items: center;
  /* Вертикальне центрування іконки і тексту */
  justify-content: center;
  gap: 10px;
  /* Відстань між іконкою і текстом */
  position: relative;
}

.icon {
  width: 18px;
  height: 18px;
  position: absolute;
  /* Фіксоване позиціонування іконки */
  left: 12px;
  /* Відступ від лівого краю кнопки */
}

.action-button span {
  flex-grow: 1;
  text-align: center;
}

.action-button:hover {
  background: #6e85b7;
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
