import angular from 'angular';
import SliderComponent from './slider.component';
import './slider.less';

const slider = angular
    .module('common.slider', [])
    .component('slider', SliderComponent)
    .name;

export default slider;
