import angular from 'angular';
import Socket from './socket.factory';

const socket = angular
    .module('common.socket', [])
    .factory('Socket', Socket)
    .name;

export default socket;
