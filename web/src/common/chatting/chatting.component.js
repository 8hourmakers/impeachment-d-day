import template from './chatting.html';
import controller from './chatting.controller';

const ChattingComponent = {
    template,
    controller,
    bindings: {
        initMemberCount: '<',
        initVisitCount: '<'
    }
};

export default ChattingComponent;
