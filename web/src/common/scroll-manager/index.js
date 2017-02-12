import angular from 'angular';
import ScrollManagerDirective from './scrollManager.directive';

const scrollManager = angular
    .module('common.scrollManager', [])
    .directive('scrollManager', ScrollManagerDirective)
    .name;

export default scrollManager;
