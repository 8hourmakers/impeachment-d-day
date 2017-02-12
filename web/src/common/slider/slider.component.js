import template from './slider.html';
import controller from './slider.controller';

const SliderComponent = {
    template,
    controller,
    bindings: {
        images: '<'
    }
};

export default SliderComponent;
