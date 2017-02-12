import reverse from 'lodash/reverse';
import remove from 'lodash/remove';
import api from '../../utils/api';

class ChatRoom {
    constructor() {
        this.$http = ChatRoom.injector.$http;

        this.chats = [];
        this.canLoadMore = true;
    }

    canLoad() {
        return this.canLoadMore;
    }

    init() {
        remove(this.chats, () => true);

        return this.$http
            .get(api.comment)
            .then((res) => {
                const chats = reverse(res.data.results);

                this.canLoadMore = this.chats.length === 20;

                chats.forEach(chat => this.chats.push(chat));
            });
    }

    unshiftChats(chats) {
        const lastChatId = this.chats[0].id;

        return this.$http
            .get(api.comment, { params: { start: lastChatId } })
            .then((res) => {
                const chats = reverse(res.data.results);

                this.canLoadMore = this.chats.length === 20;

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
