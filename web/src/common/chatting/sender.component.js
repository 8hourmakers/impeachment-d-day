import template from './sender.html';
import controller from './sender.controller';

const SenderComponent = {
    template,
    controller,
    bindings: {
        memberName: '<',
        onSendChat: '&'
    }
};

export default SenderComponent;
