class ChattingCtrl {
    $onInit() {
        this.state = 'BeforeEntry';
    }

    enterChatRoom(memberName) {
        this.state = 'Entrance';

        console.log(memberName);
    }
}

export default ChattingCtrl;
