import divide from 'lodash/divide';
import floor from 'lodash/floor';
import api from '../utils/api';

const DATE_MS = 1000 * 60 * 60 * 24;
const HOUR_MS = 1000 * 60 * 60;
const MINUTE_MS = 1000 * 60;
const SECOND_MS = 1000;

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
                const time = (data.impeachment_datetime * SECOND_MS) + (HOUR_MS * 9);

                this.date = new Date(time);

                this.initiailized = true;
            });
    }

    getDiff() {
        const today = new Date();

        return this.date.getTime() - today.getTime();
    }

    getDay() {
        if (!this.initiailized) return '';

        const diff = this.getDiff();

        if (diff < 0) {
            return '탄핵이 완료되었습니다.'
        } else if (diff === 0) {
            return 'D-Day';
        }

        const diffDate = floor(divide(diff, DATE_MS));

        return `D-${diffDate}`;
    }

    getFormatted() {
        if (!this.initiailized) return '';

        const diff = this.getDiff();

        if (diff < 0) {
            return '';
        }

        let str = '';

        const date = floor(divide(diff, DATE_MS));
        const dateTime = date * DATE_MS;

        if (date > 0) {
            str += `${date}일 `;
        }

        const hour = floor(divide(diff - dateTime, HOUR_MS));
        const hourTime = hour * HOUR_MS;

        if (hour > 0) {
            str += `${hour}시간 `;
        }

        const minute = floor(divide(diff - dateTime - hourTime, MINUTE_MS));
        const minuteTime = minute * MINUTE_MS;

        if (minute > 0) {
            str += `${minute}분 `;
        }

        const second = floor(divide(diff - dateTime - hourTime - minuteTime, SECOND_MS));
        str += `${second}초`;

        return str;
    }
}

DDay.$inject = ['$http'];

export default DDay;
