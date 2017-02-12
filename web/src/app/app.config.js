const httpConfig = ($httpProvider) => {
    $httpProvider.defaults.headers.common.Accept = 'application/json';

};

export default {
    httpConfig
};
