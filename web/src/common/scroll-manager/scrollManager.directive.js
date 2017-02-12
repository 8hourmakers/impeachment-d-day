const scrollManagerDirective = ($timeout) => {
    const config = {
        restrict: 'A',
        replace: false
    };

    config.scope = {
        onInfiniteScroll: '&',
        threshold: '@'
    };

    config.link = function (scope, elem) {
        const $elem = elem[0];

        scope.$on('SCROLL', () => {
            $timeout(() => {
                $elem.scrollTop = $elem.scrollHeight;
            }, 0);
        });
    };

    return config;
};

scrollManagerDirective.$inject = ['$timeout'];

export default scrollManagerDirective;
