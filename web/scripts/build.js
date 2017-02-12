const webpack = require('webpack');
const env = require('../configs/env');
const webpackConfig = env.isProduction() ? require('../configs/webpack.prod') : require('../configs/webpack.dev');

const compiler = webpack(webpackConfig);

compiler.apply(new webpack.ProgressPlugin(function (percentage, msg) {
    process.stdout.clearLine();
    process.stdout.cursorTo(0);
    process.stdout.write(`${(percentage * 100).toFixed(2)}% ${msg}`);
}));

compiler.run(function (err, stats) {
    if (err) throw new Error(err);

    console.log(stats.toString({
        colors: true,
        children: false,
        assets: false,
        chunks: ['framework', 'app']
    }));
});
