import template from './chat.html';

const ChatComponent = {
    template,
    bindings: {
        chatMemberName: '<',
        chatMessage: '<'
    }
};

export default ChatComponent;
