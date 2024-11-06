<template>
  <div>
    <Header />
    <h1>(☞ﾟヮﾟ)☞ 📚Это КОНКРЕТНАЯ КНИГА 📚 ☜(ﾟヮﾟ☜)</h1>
    <p> и вот что мы о ней знаем ✍ </p>
    <Footer />
  </div>
</template>

<script>
import Header from '../components/HeaderComponent.vue';
import Footer from '../components/FooterComponent.vue';

export default {
  name: 'BookPage',
  components: {
    Header,
    Footer,
  },
};
</script>
