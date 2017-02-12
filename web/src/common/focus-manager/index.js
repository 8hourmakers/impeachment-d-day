import angular from 'angular';
import FocusManagerDirective from './focusManager.directive';

const scrollManager = angular
    .module('common.focusManager', [])
    .directive('focusManager', FocusManagerDirective)
    .name;

export default scrollManager;
