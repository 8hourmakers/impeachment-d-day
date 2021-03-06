import angular from 'angular';
import Socket from '../socket';
import FocusManager from '../focus-manager';
import ScrollManager from '../scroll-manager';
import ChatComponent from './chat.component';
import MemberNameComponent from './memberName.component';
import Sender from './sender.component';
import ChatRoomFactory from './chatRoom.factory';
import ChattingComponent from './chatting.component';
import './chatting.less';
import './chat.less';
import './memberName.less';
import './sender.less';

const chatting = angular
    .module('common.chatting', [
        Socket,
        FocusManager,
        ScrollManager
    ])
    .factory('ChatRoom', ChatRoomFactory)
    .component('chat', ChatComponent)
    .component('memberName', MemberNameComponent)
    .component('sender', Sender)
    .component('chatting', ChattingComponent)
    .name;

export default chatting;
