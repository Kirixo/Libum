// const { defineConfig } = require('@vue/cli-service');

// module.exports = defineConfig({
//   transpileDependencies: true,
// });

module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'https://libum.yooud.org/',
        changeOrigin: true,
        secure: false, // Accept invalid certificates
        pathRewrite: { '^/api': '/api' },
        logLevel: 'debug',
        // headers: {
        //   'ngrok-skip-browser-warning': 'ERR_NGROK_6024',
        // },
      },
    },
  },
};
