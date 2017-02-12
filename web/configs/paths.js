const path = require('path');

const resolvePath = path.resolve.bind(path, __dirname, '../');

module.exports = {
    entry: {
        app: resolvePath('src/app/index.js')
    },
    views: {
        index: resolvePath('src/views/index.html')
    },
    src: resolvePath('src'),
    dist: resolvePath('build')
};
