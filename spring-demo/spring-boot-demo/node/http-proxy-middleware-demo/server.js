// include dependencies
const express = require('express');
const { createProxyMiddleware } = require('http-proxy-middleware');

// proxy middleware options
const options = {
  target: 'http://localhost:8080', // target host
  changeOrigin: true, // needed for virtual hosted sites
  ws: true, // proxy websockets
  pathRewrite: {
    // '^/api/test1': '/api/test', // rewrite path
    // '^/api/remove/path': '/test', // remove base path
    '^/': '/', // remove base path
  },
  // router: {
  //   // when request.headers.host == 'dev.localhost:3000',
  //   // override target 'http://www.example.org' to 'http://localhost:8000'
  //   'dev.localhost:3000': 'http://localhost:8000',
  // },
};

// create the proxy (without context)
const exampleProxy = createProxyMiddleware(options);

// mount `exampleProxy` in web server
const app = express();
app.use('/proxy', exampleProxy);
app.listen(3000);