<template>
  <div class="cart-page">
    <div class="user-info">
      <div class="profile-picture">
        <img :src="user.avatar" alt="User Avatar" />
      </div>
      <div class="user-details">
        <h3>{{ user.name }}</h3>
        <p>Мій кошик</p>
      </div>
    </div>

    <div class="cart-content">
      <!-- Первый столбец (список товаров) -->
      <div v-if="isCartEmpty" class="empty-cart-message">
        Ваш кошик порожній. Будь ласка, додайте книжки, аби оформити замовлення!
      </div>
      <div v-else>
        <div class="cart-items">
          <CartItem v-for="book in books" :key="book.id" :book="book" @remove="removeCurrentBook(book.id)" />
        </div>
      </div>

      <!-- Второй столбец (итоги, кнопки, подписка) -->
      <div class="cart-summary-and-subscription">
        <div class="cart-summary">
          <div class="total">
            <p>Загальна вартість</p>
            <p>{{ totalPrice }} грн</p>
          </div>

          <div class="actions">
            <button @click="checkout" class="checkout-button">Перейти до оплати</button>
          </div>
        </div>
        <div class="actions">
          <button @click="continueShopping" class="continue-button">Продовжити покупки</button>
        </div>

        <div class="subscription">
          <p>Читайте без обмежень з підпискою</p>
          <p class="subscription-price">80 грн за місяць</p>
          <button @click="subscribe" class="subscribe-button">Підписатися</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex';
import bookImage from '@/assets/testBook.jpg';
import CartItem from '../components/CartComponent.vue';

export default {
  name: 'CartPage',
  components: {
    CartItem,
  },
  data() {
    return {
      user: {
        name: 'Ім’я користувача',
        avatar: bookImage,
      },
      // books: [
      //   {
      //     id: 1, title: 'Знаюча', author: 'Наталі Папелл', price: 7, image: bookImage,
      //   },
      //   {
      //     id: 2, title: 'Назва', author: 'Автор', price: 70, image: bookImage,
      //   },
      //   {
      //     id: 3, title: 'Назва2', author: 'Автор2', price: 170, image: bookImage,
      //   },
      //   {
      //     id: 4, title: 'Назва3', author: 'Автор3', price: 1170, image: bookImage,
      //   },
      // ],
    };
  },
  computed: {
    ...mapState({
      books: (state) => state.cartBooks || [], // Получаем книги из Vuex
    }),
    // Проверяем, пуста ли корзина
    isCartEmpty() {
      return this.$store.state.cartBooks.length === 0;
    },
    totalPrice() {
      return this.books.length ? this.books.reduce((total, book) => total + book.price, 0).toFixed(2) : 0;
    },
  },
  methods: {
    ...mapActions(['fetchCartBooks', 'removeBook']),
    async removeCurrentBook(bookId) {
      await this.removeBook(bookId); // Удаление книги через Vuex
      this.fetchCartBooks();
    },
    // removeBook(bookId) {
    //   this.books = this.books.filter((book) => book.id !== bookId);
    // },
    checkout() {
      alert('Переходимо до оплати...');
    },
    continueShopping() {
      this.$router.push({ name: 'MainPage' });
    },
    subscribe() {
      alert('Переходимо до оформленя...');
    },
  },
  created() {
    this.fetchCartBooks(); // Загружаем книги корзины при инициализации
  },
};
</script>

<style scoped>
.cart-page {
  padding: 100px 200px;
}

.user-info {
  display: flex;
  align-items: center;
  background-color: #DDE4F0;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 30px;
}

.profile-picture img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 15px;
}

.user-details h3 {
  margin: 0;
  font-size: 22px;
}

.user-details p {
  margin: 5px 0;
  color: #555;
}

.cart-content {
  display: flex;
  justify-content: flex-start;
  justify-content: center;
  gap: 30px;
}

.cart-items {
  width: 70%;
}

.cart-summary-and-subscription {
  width: 30%;
  display: flex;
  flex-direction: column;
  min-width: 300px;
}

.cart-summary {
  background-color: #DDE4F0;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 30px;
}

.cart-summary .total p {
  font-size: 20px;
  font-weight: bold;
}

.actions button {
  padding: 10px 20px;
  margin-left: 10px;
  font-size: 16px;
  border-radius: 20px;
  cursor: pointer;
}

.checkout-button {
  background-color: #ffffff;
  color: rgb(0, 0, 0);
}

.continue-button {
  margin-bottom: 20px;
  background-color: #6E85B7;
  color: #ffffff;
  border: 1px solid #6E85B7;
}

.actions button:hover {
  opacity: 0.8;
}

.subscription {
  background-color: #f3f6fa;
  padding: 20px;
  border-radius: 8px;
}

.subscription-price {
  font-size: 18px;
  font-weight: bold;
}

.subscribe-button {
  background-color: #6c5ce7;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
}

.subscribe-button:hover {
  background-color: #5e47d3;
}

/* Медиазапросы для адаптивного дизайна */
@media (max-width: 1250px) {
  .cart-content {
    flex-direction: column;
    gap: 20px;
  }

  .cart-items {
    width: 100%;
  }

  .cart-summary-and-subscription {
    width: 100%;
    min-width: 540px;
  }
}

.empty-cart-message {
  color: #6E85B7;
  font-size: 24px;
  margin: 20px;
}
</style>
