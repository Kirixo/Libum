<template>
  <!-- <link rel="stylesheet" href="../style.css"> -->

  <div>
    <Header />
    <h1>Main Page или же каталог в нашем случае</h1>

    <Book />
    <Book />
    <Book />

    <Footer />
  </div>
</template>

<script>
import Header from '../components/HeaderComponent.vue';
import Footer from '../components/FooterComponent.vue';
import Book from '../components/BookComponent.vue';

export default {
  name: 'MainPage',
  components: {
    Header,
    Footer,
    Book,
  },
  // computed: {
  //   userInfo() {
  //     return this.$store.state.userInfo;
  //   },
  // },
};
</script>

<style scoped></style>
