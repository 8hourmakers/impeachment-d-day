class ChattingCtrl {
    constructor($scope, ChatRoom, Socket) {
        this.$scope = $scope;
        this.ChatRoom = ChatRoom;
        this.Socket = Socket;
    }

    $onInit() {
        this.socket = new this.Socket();
        this.chatRoom = new this.ChatRoom();

        this.state = 'BeforeEntry';
        this.memberName = null;

        this.chatRoom.init().then(() => {
            this.$scope.$broadcast('SCROLL');
        });

        this.socket
            .connect()
            .listen('listen/new_comment', ({ data }) => {
                this.chatRoom.addChat(data);
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
    }
}

ChattingCtrl.$inject = ['$scope', 'ChatRoom', 'Socket'];

export default ChattingCtrl;
