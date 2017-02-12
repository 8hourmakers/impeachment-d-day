import api from '../../utils/api';

class ChattingCtrl {
    constructor($scope, $http, $timeout, ChatRoom, Socket) {
        this.$scope = $scope;
        this.$http = $http;
        this.$timeout = $timeout;
        this.ChatRoom = ChatRoom;
        this.Socket = Socket;
    }

    $onInit() {
        this.socket = new this.Socket();
        this.chatRoom = new this.ChatRoom();

        this.state = 'BeforeEntry';
        this.isSpam = false;
        this.memberMaxCount = '-';
        this.visitCount = '-';
        this.memberName = '';

        this.$http
            .get(api.impeachment)
            .then((res) => {
                const data = res.data.results;

                this.memberMaxCount = data.member_num;
                this.visitCount = data.visit_cnt;
            });

        this.chatRoom.init().then((memberNumber, visitCount) => {
            this.memberMaxCount = memberNumber;
            this.visitCount = visitCount;

            this.$scope.$broadcast('SCROLL');
            this.$scope.$broadcast('FOCUS');
        });

        this.socket
            .connect()
            .listen('listen/update_member_num', (message) => {
                const memberNumber = message.results.member_num;
                const visitCount = message.results.visit_cnt;

                this.memberMaxCount = memberNumber;
                this.visitCount = visitCount;

                this.$scope.$apply();
            })
            .listen('listen/new_comment', (message) => {
                this.chatRoom.addChat(message.results);
                this.$scope.$broadcast('SCROLL');
                this.$scope.$apply();
            });
    }

    $onDestroy() {
        if (this.socket) {
            this.socket.disconnect();
        }
    }

    enterChatRoom(memberName) {
        this.state = 'Entrance';
        this.memberName = memberName;

        this.$scope.$broadcast('FOCUS');
    }

    showSpam() {
        this.isSpam = true;

        this.$timeout(() => {
            this.isSpam = false;
            this.$scope.$broadcast('FOCUS');
        }, 1500);
    }
}

ChattingCtrl.$inject = ['$scope', '$http', '$timeout', 'ChatRoom', 'Socket'];

export default ChattingCtrl;
