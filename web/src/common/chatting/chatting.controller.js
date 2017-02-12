class ChattingCtrl {
    constructor(ChatRoom, Socket) {
        this.ChatRoom = ChatRoom;
        this.Socket = Socket;
    }

    $onInit() {
        this.socket = new this.Socket();
        this.chatRoom = new this.ChatRoom();

        this.state = 'BeforeEntry';
        this.memberName = null;

        this.chatRoom.init();

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

ChattingCtrl.$inject = ['ChatRoom', 'Socket'];

export default ChattingCtrl;
