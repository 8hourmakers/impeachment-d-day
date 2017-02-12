class AppCtrl {
    constructor($interval, dDay) {
        this.$interval = $interval;

        this.counterInterval = null;
        this.dDay = dDay;
        this.counter = '계산 중 ...';
    }

    $onInit() {
        this.images = [
            '/impeachment_d_day/assets/1.jpeg',
            '/impeachment_d_day/assets/2.jpeg',
            '/impeachment_d_day/assets/3.jpeg'
        ];

        this.dDay.initialize().then(() => {
            this.counterInterval = this.$interval(() => {
                this.counter = this.dDay.getFormatted();
            }, 1000);
        });
    }

    $onDestroy() {
        if (this.counterInterval) {
            this.$interval.cancel(this.counterInterval);
        }
    }
}

AppCtrl.$inject = ['$interval', 'dDay'];

export default AppCtrl;
