$(document).ready(function(){
    const $emailDisp = $("#emailDisp");
    const $phoneDisp = $("#phoneDisp");
    const $searchPasswordType = $("#searchPasswordType");

    changeDisp($("input[name='selectRadio']:checked").val());

    $("input[name='selectRadio']").on('change', function () {
        changeDisp($(this).val());
    });

    function changeDisp(value) {
        console.log("value = ", value);
        if (value == 'email') {
            $phoneDisp.css('display', 'none');
            $emailDisp.css('display', 'block');
            $searchPasswordType.val('email');
        } else if (value == 'phone') {
            $emailDisp.css('display', 'none');
            $phoneDisp.css('display', 'block');
            $searchPasswordType.val('phone');
        }
    }

    function emailReg() {
        let regExp = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

        if (!regExp.test($("#email").val())) {
            Swal.fire({
                html: '<b>올바른 이메일 형식이 아닙니다.</b>',
                icon: 'error'
            });
            return false;
        }
        return true;
    }

    function phoneReg() {
        let phoneNumber = $("#phoneNumber1").val() + $("#phoneNumber2").val() + $("#phoneNumber3").val();
        let regExp = /^[0-9]*$/g;

        if (phoneNumber.length < 10 || 11 < phoneNumber.length
            || !regExp.test(phoneNumber)) {
            Swal.fire({
                html: '<b>올바른 핸드폰 번호를 입력해주세요.</b>',
                icon: 'error'
            });
            return false;
        }
        $("#phoneNumber").val(phoneNumber);
        return true;
    }

    $("#submitBtn").on('click', function (){
        let result = false;
        switch ($searchPasswordType.val()) {
            case 'EMAIL':
                result = emailReg();
                break;
            case 'PHONE':
                result = phoneReg();
                break;
        }
        console.log(result);
        if (result) {
            $("#searchForm").submit();
        }
    });
});