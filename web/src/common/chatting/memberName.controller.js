class MemberNameCtrl {
    $onInit() {
        this.memberName = '';
    }

    submitMemberName() {
        this.onSubmit({
            memberName: this.memberName
        });
    }
}

export default MemberNameCtrl;
