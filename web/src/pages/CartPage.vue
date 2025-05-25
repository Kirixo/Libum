<template>
  <div>
    <Header />
    <div class="cart-page">
      <div class="user-info">
        <div class="profile-picture">
          <img :src="userInfo?.avatar || ''" alt="User Avatar" />
        </div>
        <div class="user-details">
          <h3>{{ userInfo?.login || 'Користувач' }}</h3>
          <p>Мій кошик</p>
        </div>
      </div>

      <div class="cart-content">
        <div v-if="isCartEmpty" class="empty-cart-message">
          Ваш кошик порожній. Будь ласка, додайте книжки, аби оформити замовлення!
        </div>
        <div v-else class="cart-container">
          <div class="cart-items">
            <CartItem
              v-for="book in books"
              :key="book.id"
              :book="book"
              @remove="removeCurrentBook"
            />
          </div>

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
    </div>
    <Footer />
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex';
import CartItem from '../components/CartComponent.vue';
import Header from '../components/HeaderComponent.vue';
import Footer from '../components/FooterComponent.vue';

export default {
  name: 'CartPage',
  components: {
    CartItem,
    Header,
    Footer,
  },
  computed: {
    ...mapState(['cartBooks', 'userInfo']),
    books() {
      return this.cartBooks || [];
    },
    isCartEmpty() {
      return !this.books.length;
    },
    totalPrice() {
      return this.books.length ? this.books.reduce((total, book) => total + (book.price || 0), 0).toFixed(2) : '0.00';
    },
  },
  methods: {
    ...mapActions(['fetchCartBooks', 'removeBook']),
    async removeCurrentBook(bookId) {
      if (!this.userInfo?.id) {
        console.error('Користувач не авторизований');
        return;
      }
      try {
        await this.removeBook(bookId);
        await this.fetchCartBooks();
      } catch (error) {
        console.error('Error removing book:', error);
      }
    },
    checkout() {
      // TODO: Implement checkout logic
      alert('Переходимо до оплати...');
    },
    continueShopping() {
      this.$router.push({ name: 'MainPage' });
    },
    subscribe() {
      // TODO: Implement subscription logic
      alert('Переходимо до оформлення підписки...');
    },
  },
  created() {
    if (this.userInfo?.id) {
      this.fetchCartBooks();
    }
  },
};
</script>

<style scoped>
.cart-page {
  padding: 20px 50px;
  max-width: 1200px;
  margin: 0 auto;
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
  object-fit: cover;
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
  flex-direction: column;
}

.cart-container {
  display: flex;
  gap: 30px;
}

.empty-cart-message {
  text-align: center;
  font-size: 18px;
  color: #666;
  padding: 40px;
  background: #DDE4F0;
  border-radius: 8px;
}

.cart-items {
  flex: 1;
  min-width: 0;
}

.cart-summary-and-subscription {
  width: 300px;
  flex-shrink: 0;
}

.cart-summary {
  background-color: #DDE4F0;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
}

.total {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.total p {
  font-size: 18px;
  font-weight: bold;
  margin: 0;
}

.actions {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.checkout-button,
.continue-button,
.subscribe-button {
  width: 100%;
  padding: 12px;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.checkout-button {
  background-color: #3F5C8A;
  color: white;
}

.continue-button {
  background-color: white;
  border: 1px solid #3F5C8A;
  color: #3F5C8A;
}

.subscription {
  background-color: #DDE4F0;
  padding: 20px;
  border-radius: 8px;
  text-align: center;
}

.subscription-price {
  font-size: 24px;
  font-weight: bold;
  margin: 10px 0;
}

.subscribe-button {
  background-color: #3F5C8A;
  color: white;
}

.checkout-button:hover,
.subscribe-button:hover {
  background-color: #2F4C7A;
}

.continue-button:hover {
  background-color: #F5F5F5;
}
</style>
