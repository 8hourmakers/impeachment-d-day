import template from './memberName.html';
import controller from './memberName.controller';

const MemberNameComponent = {
    template,
    controller,
    bindings: {
        onSubmit: '&'
    }
};

export default MemberNameComponent;
