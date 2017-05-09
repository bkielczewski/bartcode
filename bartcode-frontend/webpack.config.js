const webpack = require('webpack');
const ngToolsWebpack = require('@ngtools/webpack');
const path = require('path');

module.exports = {
  entry: {
    server: './src/server.ts'
  },
  resolve: {
    extensions: ['.ts', '.js']
  },
  target: 'node',
  externals: [
    '@angular/animations',
    '@angular/cli',
    '@angular/common',
    '@angular/compiler',
    '@angular/compiler-cli',
    '@angular/core',
    '@angular/flex-layout',
    '@angular/forms',
    '@angular/http',
    '@angular/material',
    '@angular/platform-browser',
    '@angular/router',
    '@angular/tsc-wrapped',
    '@angular/service-worker',
    'angular-ssr',
    'apicache',
    'compression',
    'express',
    function (context, request, callback) {
      const exclusions = [/\@ngrx/, /rxjs/, /zone\.js/, /reflect-metadata/];

      if (exclusions.some(expr => expr.test(request))) {
        callback(null, `commonjs ${request.replace(/^.*?(\\|\/)node_modules(\\|\/)/, String())}`);
      }
      else {
        callback();
      }
    },
  ],
  node: {
    __dirname: true
  },
  output: {
    path: path.join(__dirname, 'dist'),
    filename: '[name].js',
    libraryTarget: 'commonjs2'
  },
  plugins: [
    new ngToolsWebpack.AotPlugin({
      tsConfigPath: './tsconfig-server.json',
      hostReplacementPaths: {
        "src/environments/environment.ts": "src/environments/environment.prod.ts"
      }
    }),
    new webpack.ProgressPlugin(),
  ],
  module: {
    rules: [
      {test: /\.scss$/, loaders: ['raw-loader', 'sass-loader']},
      {test: /\.css$/, loader: 'raw-loader'},
      {test: /\.html$/, loader: 'raw-loader'},
      {test: /\.ts$/, loader: '@ngtools/webpack'}
    ]
  }
};
