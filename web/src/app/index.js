import angular from 'angular';
import Chatting from '../common/chatting';
import AppComponent from './app.component';
import '../styles/general.less';
import './app.less';

const app = angular
    .module('app', [
        Chatting
    ])
    .component('app', AppComponent)
    .name;

export default app;
