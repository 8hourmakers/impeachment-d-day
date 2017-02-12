class ChattingCtrl {
    constructor(Socket) {
        this.Socket = Socket;
    }

    $onInit() {
        this.socket = new this.Socket();
        this.state = 'BeforeEntry';
        this.memberName = null;

        this.socket
            .connect()
            .listen('listen/update_member_num', ({ data }) => {
                console.log(data);
            })
            .listen('listen/new_comment', ({ data }) => {
                console.log(data);
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

ChattingCtrl.$inject = ['Socket'];

export default ChattingCtrl;
