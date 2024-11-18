<template>
  <div
    class="book"
    @click="goToBookPage"
    @keydown.enter="goToBookPage"
    @keydown.space.prevent="goToBookPage"
    tabindex="0"
    role="button"
  >
    <div class="book-inner">
      <img :src="book.cover" :alt="book.title" class="book-cover" />
    </div>
    <h3 class="book-title">{{ book.title }}</h3>
    <p class="book-price">{{ book.price }}</p>
  </div>
</template>

<script>
export default {
  name: 'BookComponent',
  props: {
    book: {
      type: Object,
      required: true,
    },
  },
  methods: {
    goToBookPage() {
      this.$router.push({ name: 'BookPage', params: { id: this.book.id } });
    },
  },
};
</script>

<style scoped>
.book {
  text-align: center;
  border: 1px solid #ddd;
  border-radius: 20px;
  padding: 1rem;
  background-color: #dde4f0;
  cursor: pointer;
  transition: transform 0.2s, background-color 0.3s;
  width: 156px;
  height: 243px;
}

.book:hover {
  transform: scale(1.05);
}

.book:focus {
  outline: 2px solid #000000;
}

.book-inner {
  width: 100%;
  height: 72%;
  overflow: hidden;
  margin-bottom: 1rem;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 5px;
}

.book-cover {
  width: 196px;
  height: 256px;
  object-fit: cover;
}

.book-title {
  font-size: 1.2rem;
  font-weight: bold;
  margin-bottom: 0.5rem;
}

.book-price {
  color: #000000;
  font-weight: 550;
  margin-top: 0.3rem;
}

</style>
