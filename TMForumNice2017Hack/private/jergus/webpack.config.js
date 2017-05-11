var path = require('path');
var webpack = require('webpack');
var config = require('./src/.env');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var ExtractTextPlugin = require('extract-text-webpack-plugin');

module.exports = {

    entry: './src/app.js',

    output: {
        path: path.join(__dirname, 'dist'),
        filename: 'bundle.js',
        publicPath: config.APP_URL
    },

    performance: {
        hints: false
    },

    devtool: 'source-map',

    devServer: {
        stats: 'errors-only',
    },

    module: {
        rules: [
            {
                test: /\.scss$/,
                use: ExtractTextPlugin.extract({
                    fallback: 'style-loader',
                    use: ['css-loader?sourceMap', 'sass-loader?sourceMap']
                })
            },
            {
                test: /\.(eot|ttf|svg|woff|woff2)(\?v=[0-9]\.[0-9]\.[0-9])?$/,
                use: 'file-loader'
            },
            {
                test: /\.html$/,
                use: ['ngtemplate-loader?module=app&relativeTo=src/&prefix=/', 'html-loader'],
                exclude: path.join(__dirname, 'src/index.html')
            }
        ]
    },

    plugins: [
        new ExtractTextPlugin('app.css'),

        new HtmlWebpackPlugin({
            template: 'src/index.html',
            hash: true
        }),
    ]

};
