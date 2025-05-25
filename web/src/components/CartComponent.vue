<template>
  <div class="cart-item">
    <img :src="book.cover" alt="Book image" class="cart-item-image" />
    <div class="cart-item-details">
      <h3 class="cart-item-title">{{ book.title }}</h3>
      <p class="cart-item-author">{{ book.author }}</p>
      <p class="cart-item-price">{{ book.price }} грн</p>
      <button @click="removeFromCart" class="remove-button">Видалити</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CartItem',
  props: {
    book: Object,
  },
  methods: {
    removeFromCart() {
      this.$emit('remove', this.book.id);
    },
  },
};
</script>

<style scoped>
.cart-item {
  min-width: 500px;
  display: flex;
  padding: 20px;
  background-color: #DDE4F0;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
  position: relative;
}

.cart-item-image {
  width: 175px;
  height: 250px;
  object-fit: cover;
  margin-right: 20px;
  margin-top: 10px;
  margin-bottom: 10px;
  border-radius: 10px;
}

.cart-item-details {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  flex-grow: 1;
  height: 100%;
}

.cart-item-title {
  font-size: 24px;
  font-weight: bold;
  word-wrap: break-word;
  overflow-wrap: break-word;
  word-break: break-word;
  margin-bottom: 5px;
}

.cart-item-author {
  margin-top: 5px;
  font-size: 16px;
  color: #000000;
  margin-bottom: 15px;
}

.cart-item-price {
  font-size: 24px;
  font-weight: bold;
  position: absolute;
  bottom: 20px;
  right: 20px;
  margin-bottom: 45px;
}

.remove-button {
  position: absolute;
  bottom: 20px;
  right: 20px;
  padding: 8px 15px;
  background-color: #ffffff;
  border-color: #3F5C8A;
  color: rgb(0, 0, 0);
  border-radius: 15px;
  cursor: pointer;
  font-size: 16px;
}

.remove-button:hover {
  background-color: #9ebbf1;
}
</style>
