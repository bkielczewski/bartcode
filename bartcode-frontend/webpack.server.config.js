const path = require('path');
const webpack = require('webpack');

module.exports = {
  entry: {server: './src/server.ts'},
  resolve: {extensions: ['.ts', '.js']},
  target: 'node',
  externals: [/(node_modules|main\..*\.js)/],
  output: {
    path: path.join(__dirname, 'dist'),
    filename: '[name].js'
  },
  module: {
    rules: [
      {test: /\.ts$/, loader: 'ts-loader'}
    ]
  },
  plugins: [
    new webpack.DefinePlugin({
      window: undefined,
      document: undefined,
      location: JSON.stringify({
        protocol: 'https',
        host: `localhost`,
      })
    }),
    // Temporary Fix for issue: https://github.com/angular/angular/issues/11580
    // for "WARNING Critical dependency: the request of a dependency is an expression"
    new webpack.ContextReplacementPlugin(
      /(.+)?angular(\\|\/)core(.+)?/,
      path.join(__dirname, 'src'),
      {}
    ),
    new webpack.ContextReplacementPlugin(
      /(.+)?express(\\|\/)(.+)?/,
      path.join(__dirname, 'src'),
    )
  ]
};
