import angular from 'angular';
import ChatComponent from './chat.component';
import MemberNameComponent from './memberName.component';
import Sender from './sender.component';
import ChattingComponent from './chatting.component';
import './chatting.less';
import './chat.less';

const chatting = angular
    .module('common.chatting', [])
    .component('chat', ChatComponent)
    .component('memberName', MemberNameComponent)
    .component('sender', Sender)
    .component('chatting', ChattingComponent)
    .name;

export default chatting;
