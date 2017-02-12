const focusManagerDirective = ($timeout) => {
    const config = {
        restrict: 'A',
        replace: false
    };

    config.link = function (scope, elem) {
        const $elem = elem[0];

        scope.$on('FOCUS', () => {
            $timeout(() => {
                $elem.focus();
            }, 0);
        });
    };

    return config;
};

focusManagerDirective.$inject = ['$timeout'];

export default focusManagerDirective;
