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
    '@angular/cli',
    '@angular/common',
    '@angular/compiler',
    '@angular/compiler-cli',
    '@angular/core',
    '@angular/forms',
    '@angular/http',
    '@angular/platform-browser',
    '@angular/router',
    '@angular/tsc-wrapped',
    '@angular/service-worker',
    'angular-ssr'
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
        "environments/environment.ts": "environments/environment.prod.ts"
      },
    })
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
