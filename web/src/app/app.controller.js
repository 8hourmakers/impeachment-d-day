const api = require('../utils/api');

class AppCtrl {
    constructor($http) {
        this.$http = $http;
    }

    $onInit() {
        this.dDay = null;
        this.initiailized = false;
    }
}

AppCtrl.$inject = ['$http'];

export default AppCtrl;
