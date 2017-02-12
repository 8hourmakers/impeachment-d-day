class MemberNameCtrl {
    $onInit() {
        this.memberName = '';
    }

    press(event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            this.submitMemberName();
        }
    }

    submitMemberName() {
        this.onSubmit({
            memberName: this.memberName
        });
    }
}

export default MemberNameCtrl;
