class AppCtrl {
    constructor($interval, dDay) {
        this.$interval = $interval;

        this.counterInterval = null;
        this.dDay = dDay;
        this.counter = '계산 중 ...';
    }

    $onInit() {
        this.images = [
            '/assets/1.jpeg',
            '/assets/2.jpeg',
            '/assets/3.jpeg'
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
