var debug = process.env.NODE_ENV !== "production";
var webpack = require('webpack');


module.exports = {
  context: __dirname,
  devtool: debug ? "inline-sourcemap" : null,
  entry: "./js/metrics/metrics_scripts.js",
  output: {
    path: __dirname + "/js",
    filename: "metrics_scripts.min.js"
  },
  module: {
    loaders: [
      {
        test: /\.jsx?$/,
        exclude: /(node_modules|bower_components)/,
        loader: 'babel', 
        query: {
          presets: ['react', 'es2015']
        }
      }
    ]
  },

  node: {
    fs: "empty"
  },

  plugins: debug ? [] : [
    new webpack.optimize.DedupePlugin(),
    new webpack.optimize.OccurenceOrderPlugin(),
    new webpack.optimize.UglifyJsPlugin({ mangle: false, sourcemap: false }),
  ],
};