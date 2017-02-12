const webpack = require('webpack');
const autoprefixer = require('autoprefixer');
const CleanWebpackPlugin = require('clean-webpack-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const paths = require('./paths');

const extractFrameworkCss = new ExtractTextPlugin('framework.css');
const extractStylesheet = new ExtractTextPlugin('[name].css');

module.exports = {
    devtool: 'source-map',
    entry: {
        framework: ['angular'],
        app: paths.entry.app
    },
    output: {
        path: paths.dist,
        filename: '[name].js'
    },
    module: {
        loaders: [
            { test: /\.js$/, loader: 'babel', include: paths.src, query: { cacheDirectory: true } },
            { test: /\.less$/, loader: extractStylesheet.extract('style', 'css!postcss!less'), include: paths.src },
            { test: /\.css$/, loader: extractFrameworkCss.extract('style', 'css!postcss') },
            { test: /\.(png|jpg|jpeg|gif|svg|woff|woff2|ttf|eot)$/, loader: 'file' },
            { test: /\.html$/, loader: 'raw' }
        ]
    },
    postcss: () => autoprefixer({ browsers: ['> 1%', 'last 3 versions', 'ie 10-11'] }),
    plugins: [
        new CleanWebpackPlugin([paths.dist]),
        new webpack.optimize.CommonsChunkPlugin({
            name: ['framework'],
            filename: '[name].bundle.js',
            minChunks: Infinity,
            chunks: ['app']
        }),
        new HtmlWebpackPlugin({
            filename: 'index.html',
            template: paths.views.index,
            inject: 'body',
            chunks: ['framework', 'app']
        }),
        extractFrameworkCss,
        extractStylesheet,
        new CopyWebpackPlugin([{
            from: paths.assets,
            to: './assets'
        }])
    ]
};
