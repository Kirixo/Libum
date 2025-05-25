<template>
  <div
    class="book"
    @click="goToBookPage"
    @keydown.enter="goToBookPage"
    @keydown.space.prevent="goToBookPage"
    tabindex="0"
    role="button"
    @mouseenter="isHovered = true"
    @mouseleave="isHovered = false"
    @focusin="isHovered = true"
    @focusout="isHovered = false"
    :class="{ 'is-hovered': isHovered }"
  >
    <div class="book-inner">
      <img :src="book.cover" :alt="book.title" class="book-cover" />
    </div>
    <div class="book-content">
      <h3 class="book-title">{{ book.title }}</h3>
      <p class="book-price">{{ book.price }} грн</p>
    </div>
    <div class="book-details">
      <div class="rating">
        <span
          v-for="i in 5"
          :key="i"
          class="star"
          :class="{ 'filled': i <= Math.round(book.mean_score) }"
        >
          ★
        </span>
      </div>
      <p class="details-author">Автор: {{ book.author || "ім'я автора" }}</p>
      <p class="details-pages">Сторінок: {{ book.page_count || '0' }}</p>
      <p class="details-genres">Жанри: {{ formatGenres }}</p>
    </div>
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
  data() {
    return {
      isHovered: false,
    };
  },
  computed: {
    formatGenres() {
      if (!this.book.genres || !this.book.genres.length) return '*жанр*';
      return this.book.genres.map((genre) => genre.name || genre).join(', ');
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
  width: 170px;
  border: 1px solid #ddd;
  border-radius: 20px;
  padding: 1rem;
  background-color: #dde4f0;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  min-height: 340px;
  height: 340px;
  margin-bottom: 0px;
  padding-bottom: 0px;
}

.book.is-hovered {
  height: 400px;
  margin-bottom: 0px;
  padding-bottom: 0px;
}

.book-inner {
  width: 100%;
  height: 240px;
  min-height: 240px;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 12px;
  margin-bottom: 0.8rem;
}

.book-cover {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 10px;
}

.book-content {
  display: flex;
  flex-direction: column;
  width: 100%;
  min-height: 60px;
  margin: 0px;
  padding: 0px;
}

.book-title {
  font-size: 14px;
  font-weight: bold;
  margin: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  margin-bottom: 0px;
  padding-bottom: 0px;
}

.book-price {
  font-size: 14px;
  font-weight: 550;
  color: #000;
  margin: 0;
}

.book-details {
  width: 100%;
  margin-top: 2px;
  opacity: 0;
  height: 0;
  overflow: hidden;
  transition: all 0.3s ease;
}

.is-hovered .book-details {
  opacity: 1;
  height: 80px;
}

.rating {
  display: flex;
  justify-content: center;
  gap: 2px;
  margin-bottom: 4px;
}

.star {
  color: #ccc;
  font-size: 14px;
}

.star.filled {
  color: #3F5C8A;
}

.details-author,
.details-pages,
.details-genres {
  font-size: 11px;
  color: #333;
  margin: 2px 0;
  text-align: left;
  padding: 0 5px;
}
</style>
