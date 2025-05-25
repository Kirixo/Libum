<template>
  <div class="cart-item">
    <img :src="book.cover || ''" :alt="book.title" class="cart-item-image" />
    <div class="cart-item-details">
      <h3 class="cart-item-title">{{ book.title }}</h3>
      <p class="cart-item-author">{{ book.author }}</p>
      <p class="cart-item-price">{{ book.price || 0 }} грн</p>
      <button @click="removeFromCart" class="remove-button">
        Видалити
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CartItem',
  props: {
    book: {
      type: Object,
      required: true,
    },
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
  display: flex;
  padding: 20px;
  background-color: #DDE4F0;
  border-radius: 10px;
  margin-bottom: 20px;
  gap: 20px;
}

.cart-item-image {
  width: 120px;
  height: 180px;
  object-fit: cover;
  border-radius: 8px;
  background-color: #fff;
}

.cart-item-details {
  flex: 1;
  display: flex;
  flex-direction: column;
  position: relative;
  min-width: 0;
}

.cart-item-title {
  font-size: 20px;
  font-weight: 600;
  margin: 0 0 8px;
  color: #000;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.cart-item-author {
  font-size: 16px;
  color: #666;
  margin: 0 0 16px;
}

.cart-item-price {
  font-size: 18px;
  font-weight: 600;
  color: #3F5C8A;
  margin: 0;
  position: absolute;
  bottom: 50px;
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

@media (max-width: 600px) {
  .cart-item {
    flex-direction: column;
  }

  .cart-item-image {
    width: 100%;
    height: 200px;
  }

  .cart-item-price {
    position: static;
    margin-bottom: 16px;
  }

  .remove-button {
    position: static;
  }
}
</style>
