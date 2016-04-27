var debug = process.env.NODE_ENV !== "production";
var webpack = require('webpack');


module.exports = {
  context: __dirname,
  devtool: debug ? "inline-sourcemap" : null,
  entry: "./js/alerts_scripts.js",
  output: {
    path: __dirname + "/js",
    filename: "alerts_scripts.min.js"
  },
  module: {
    loaders: [
      {
        test: /\.jsx?$/,
        exclude: /(node_modules|bower_components)/,
        loader: 'babel', // 'babel-loader' is also a legal name to reference 
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