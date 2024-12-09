// const { defineConfig } = require('@vue/cli-service');

// module.exports = defineConfig({
//   transpileDependencies: true,
// });

module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'https://literate-vastly-pony.ngrok-free.app',
        changeOrigin: true,
        pathRewrite: { '^/api': '/api' },
        logLevel: 'debug',
        headers: {
          'ngrok-skip-browser-warning': 'ERR_NGROK_6024',
        },
      },
    },
  },
};
