import reverse from 'lodash/reverse';
import api from '../../utils/api';

class ChatRoom {
    constructor() {
        this.$http = ChatRoom.injector.$http;

        this.chats = [];
    }

    init() {
        return this.$http
            .get(api.comment)
            .then((res) => {
                const chats = reverse(res.data.results);
                chats.forEach(chat => this.chats.push(chat));
            });
    }

    unshiftChats(chats) {
        const lastChatId = this.chats[0].id;

        return this.$http
            .get(api.comment, { params: { start: lastChatId } })
            .then((res) => {
                const chats = reverse(res.data.results);
                chats.forEach(chat => this.chats.unshift(chat));
            });
    }

    addChat(chat) {
        this.chats.push(chat);
    }
}

export default ['$http', ($http) => {
    ChatRoom.injector = { $http };
    return ChatRoom;
}];
