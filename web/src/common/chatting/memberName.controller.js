const regExp = /\S{2,15}/;

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
        if (!regExp.test(this.memberName)) {
            alert('유효하지 않은 닉네임입니다! 2~15글자');
            return;
        }

        this.onSubmit({
            memberName: this.memberName
        });
    }
}

export default MemberNameCtrl;
