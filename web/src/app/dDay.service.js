import api from '../utils/api';

class DDay {
    constructor($http) {
        this.$http = $http;

        this.date = null;
        this.initializeed = false;
    }

    initialize() {
        return this.$http
            .get(api.impeachment)
            .then((res) => {
                const data = res.data.results;
                const time = (data.impeachment_datetime * 1000) + (1000 * 60 * 60 * 9);

                this.date = new Date(time);
                console.log(this.date.getMonth(), this.date.getDate());
                this.initiailized = true;
            });
    }

    getDay() {
        if (!this.initiailized) return '';

        const today = new Date();
        const diff = this.date.getTime() - today.getTime();

        console.log(diff);

        return Math.floor(diff / (1000 * 60 * 60 * 24));
    }
}

DDay.$inject = ['$http'];

export default DDay;
