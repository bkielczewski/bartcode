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
  externals: [],
  node: {
    __dirname: true
  },
  output: {
    path: path.join(__dirname, 'dist'),
    filename: '[name].js'
  },
  plugins: [
    new ngToolsWebpack.AotPlugin({
      tsConfigPath: './tsconfig-server.json'
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
