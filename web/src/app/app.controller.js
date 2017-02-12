import api from '../utils/api';

class AppCtrl {
    constructor(dDay) {
        this.dDay = dDay;
    }

    $onInit() {
        this.dDay.initialize();
    }
}

AppCtrl.$inject = ['dDay'];

export default AppCtrl;
