import angular from 'angular';
import Slider from '../common/slider';
import Chatting from '../common/chatting';
import DDayService from './dDay.service';
import AppComponent from './app.component';
import '../styles/general.less';
import './app.less';

const app = angular
    .module('app', [
        Slider,
        Chatting
    ])
    .service('dDay', DDayService)
    .component('app', AppComponent)
    .name;

export default app;
