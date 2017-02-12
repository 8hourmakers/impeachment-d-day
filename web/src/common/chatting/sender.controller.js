import api from '../../utils/api';

class SenderCtrl {
    constructor($http) {
        this.$http = $http;
    }

    $onInit() {
        this.message = '';
    }

    press(event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            this.sendMessage();
        }
    }

    sendMessage() {
        this.$http.post(api.comment, {
            sender_name: this.memberName,
            content: this.message
        }).then(() => {
            this.message = '';
        });
    }
}

SenderCtrl.$inject = ['$http'];

export default SenderCtrl;
