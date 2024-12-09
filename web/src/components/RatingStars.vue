<template>
    <div class="rating-stars">
        <span v-for="star in 5" :key="star" class="star" :class="{ filled: star <= displayedRating }" role="button"
            tabindex="0" :aria-label="`Rate ${star} star${star > 1 ? 's' : ''}`" @click="setRating(star)"
            @keydown="event => handleKeydown(event, star)" @mouseover="() => setHoverRating(star)"
            @mouseleave="resetHover" @focus="() => hoverRating(star)" @blur="resetHover">
            ★
        </span>
    </div>
</template>

<script>
export default {
    name: 'RatingStars',
    props: {
        modelValue: {
            type: Number,
            default: 0,
        },
    },
    data() {
        return {
            currentRating: this.modelValue,
            hoverRating: 0,
        };
    },
    methods: {
        setRating(rating) {
            this.currentRating = rating;
            this.$emit('update:modelValue', rating);
        },
        setHoverRating(rating) {
            this.hoverRating = rating;
        },
        resetHover() {
            this.hoverRating = 0;
        },
        handleKeydown(event, rating) {
            if (event.key === 'Enter' || event.key === ' ') {
                this.setRating(rating);
                event.preventDefault();
            }
        },
    },
    computed: {
        displayedRating() {
            return this.hoverRating || this.currentRating;
        },
    },
};
</script>

<style scoped>
.rating-stars {
    display: flex;
    gap: 5px;
    cursor: pointer;
    margin-top: -20px;
    /* Поднимаем блок выше */
}

.star {
    font-size: 3.5rem;
    color: #819CC7;
    transition: color 0.2s;
}

.star.filled {
    color: #3F5C8A;
}

.star:focus {
    outline: 2px solid blue;
}
</style>
