class SliderCtrl {
    constructor($interval) {
        this.$interval = $interval;
    }

    $onInit() {
        this.currentIndex = 0;
        this.currentImg = this.images[this.currentIndex];

        this.slider = this.$interval(() => {
            if (this.currentIndex === this.images.length - 1) {
                this.currentIndex = 0;
            } else {
                this.currentIndex += 1;
            }

            this.currentImg = this.images[this.currentIndex];
        }, 1000 * 10);
    }

    $onDestroy() {
        if (this.slider) {
            this.$interval.cancel(this.slider);
        }
    }
}

SliderCtrl.$inject = ['$interval'];

export default SliderCtrl;
