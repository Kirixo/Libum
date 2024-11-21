<template>
    <div class="star-rating">
        <img v-for="(star, index) in stars" :key="index" :src="getStarImage(star)" alt="Star" class="star" />
    </div>
</template>

<script>
import fullStar from '../assets/full-star.svg';
import halfStar from '../assets/half-star.svg';
import emptyStar from '../assets/empty-star.svg';

export default {
    name: 'StarRating',
    props: {
        rating: {
            type: Number,
            required: true,
        },
    },
    computed: {
        stars() {
            const clampedRating = Math.min(Math.max(this.rating, 0), 5); // Ограничение
            const fullStars = Math.floor(clampedRating);
            const halfStars = clampedRating % 1 >= 0.5 ? 1 : 0;
            const emptyStars = 5 - fullStars - halfStars;
            return [
                ...Array(fullStars).fill('full'),
                ...Array(halfStars).fill('half'),
                ...Array(emptyStars).fill('empty'),
            ];
        },
    },

    methods: {
        getStarImage(star) {
            if (star === 'full') {
                return fullStar;
            }
            if (star === 'half') {
                return halfStar;
            }
            return emptyStar;
        },
    },
};
</script>

<style scoped>
.star-rating {
    display: flex;
    gap: 8px;
    /* Збільшення відступу між зірочками */
    margin-top: 10px;
    /* Додаємо відступ між зображенням книги та зірочками */
}

.star {
    width: 30px;
    /* Збільшення розміру зірочки */
    height: 30px;
    /* Збільшення висоти зірочки */
}
</style>
