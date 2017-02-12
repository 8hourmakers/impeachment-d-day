const NODE_ENV = process.env.NODE_ENV || 'development';

module.exports = {
    isProduction() {
        return NODE_ENV === 'production';
    },

    isDevelopment() {
        return NODE_ENV === 'development';
    }
};
