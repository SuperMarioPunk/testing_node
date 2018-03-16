var path = require('path');
const slsw = require('serverless-webpack');
const UglifyJsPlugin = require('uglifyjs-webpack-plugin')
const nodeExternals = require("webpack-node-externals");
module.exports = {
    entry: slsw.lib.entries,
    output: {
        path: path.join(__dirname,'dist'),
        libraryTarget: 'commonjs2',
    },
    externals: [nodeExternals()],
    target: "node",
    module: {
        rules: [
            {
                test: /\.json$/,
                loaders: ['json-loader']
            },
            {
                test: /\.js$/,
                loader: "babel-loader",
                include: __dirname,
                exclude: ['/node_modules/','**/*.test.js']
            }
        ]
    },
    plugins: [
        new UglifyJsPlugin(),
    ]
}