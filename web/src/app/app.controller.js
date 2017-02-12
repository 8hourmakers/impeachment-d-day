class AppCtrl {
    constructor($interval, dDay) {
        this.$interval = $interval;

        this.counterInterval = null;
        this.dDay = dDay;
        this.counter = '계산 중 ...';
    }

    $onInit() {
        this.images = [
            '/assets/bg1.jpeg',
            '/assets/bg2.jpeg',
            '/assets/bg3.jpeg',
            '/assets/bg4.jpeg',
            '/assets/bg5.jpeg',
            '/assets/bg6.jpeg',
            '/assets/bg7.jpeg'
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
