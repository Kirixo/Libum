// const { defineConfig } = require('@vue/cli-service');

// module.exports = defineConfig({
//   transpileDependencies: true,
// });

module.exports = {
  devServer: {
    proxy: {
      '/path': {
        target: 'https://literate-vastly-pony.ngrok-free.app/api',
        changeOrigin: true,
        pathRewrite: { '^/path': '' }, // Убирает префикс /path из пути запроса
        logLevel: 'debug', // Добавьте логирование
      },
    },
  },
};
