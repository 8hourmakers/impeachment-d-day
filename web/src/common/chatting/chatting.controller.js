class ChattingCtrl {
    constructor(Socket) {
        this.Socket = Socket;
    }

    $onInit() {
        this.socket = new this.Socket();
        this.state = 'BeforeEntry';

        this.socket.connect();
    }

    $onDestroy() {
        if (this.socket) {
            this.socket.disconnect();
        }
    }

    enterChatRoom(memberName) {
        this.state = 'Entrance';

        console.log(memberName);
    }
}

ChattingCtrl.$inject = ['Socket'];

export default ChattingCtrl;
