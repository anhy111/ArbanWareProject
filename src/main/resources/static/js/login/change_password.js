$(document).ready(function(){
    const $emailDisp = $("#email_disp");
    const $sendEmail = $("#sendEmail");
    const $emailAuthInput = $("#emailAuthInput");
    const $emailAuthErrorDisp = $("#emailAuthErrorDisp");
    const $emailCheck = $("#emailCheck");
    const $emailAuthSuccess = $("#emailAuthSuccess");

    const $phoneDisp = $("#phone_disp");
    const $sendSms = $("#sendSms");
    const $phoneAuthInput = $("#phoneAuthInput");
    const $phoneAuthErrorDisp = $("#phoneAuthErrorDisp");
    const $phoneCheck = $("#phoneCheck");
    const $phoneAuthSuccess = $("#phoneAuthSuccess");

    const $loginPwErrorDisp = $("#loginPasswordErrorDisp");
    const $loginPwCheckErrorDisp = $("#loginPasswordCheckErrorDisp");
    const $loginPassword = $("#loginPassword");
    const $loginPasswordCheck = $("#loginPasswordCheck");

    const formReg = {
        regAuth : {status: false, msg : '인증이 완료되지 않았습니다.', reg:authRegCheck},
        regLoginPw : { status : false, msg: '비밀번호는 영문과 숫자를 조합하고 8자~16자 사이로 입력해주세요.', reg: loginPasswordReg  },
        regLoginPwCheck : { status : false, msg: "비밀번호가 일치하지 않습니다.", reg: loginPasswordCheckReg }
    };

    $sendEmail.on('click', sendMail);
    $loginPassword.on('input', loginPasswordReg);
    $loginPasswordCheck.on('input', loginPasswordCheckReg);
    $emailCheck.on('click', emailAuthCheck);
    $sendSms.on('click', sendSms);
    $phoneCheck.on('click', phoneAuthCheck)

    if (type === 'EMAIL') {
        $emailDisp.css('display', 'block');
        $phoneDisp.css('display', 'none');
    } else {
        $phoneDisp.css('display', 'block');
        $emailDisp.css('display', 'none');
    }

    $("#cancel_btn").on('click', function () {
        history.go(-1);
    });

    $("#submitBtn").on('click', function () {
        for (let key in formReg) {
            let obj = formReg[key];
            obj.reg();
            if (!obj.status) {
                Swal.fire({
                    html: '<b>' + obj.msg + '</b>',
                    icon: 'error'
                });
                return;
            }
        }

        Swal.fire({
            html: '<b>비밀번호를 변경하시겠습니까?</b>',
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: '확인',
            cancelButtonText : '취소'
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire({
                    html: '<b>변경되었습니다.</b>',
                    icon: 'success'
                }).then((result) => {
                    $("#changePasswordForm").submit();
                });
            }
        });
    });

    function sendMail() {
        $sendEmail.html('재전송');
        $emailAuthInput.attr('disabled', false);
        $emailCheck.css('display', 'block');
        let recipient = {
            "address":$("#email").val(),
            "name": ' ',
            "type":"R"
        };

        // $.ajax({
        //     type : 'post',
        //     url : '/members/email',
        //     headers : {              // Http header
        //         "Content-Type": "application/json"
        //     },
        //     data : JSON.stringify(recipient),
        //     success : function(result) { // 결과 성공 콜백함수
        //         if (result.statusCode != 200) {
        //             Swal.fire({
        //                 html: '<b>잠시 후 다시 시도해주세요.</b>',
        //                 icon: 'error'
        //             });
        //             formReg.regEmail.msg = '이메일 인증이 완료되지 않았습니다.';
        //             return;
        //         }
        Swal.fire({
            html: '<b>인증번호가 전송되었습니다.</b>',
            icon: 'success'
        });
        //     },
        // })
    }

    function authRegCheck() {
        return formReg.regAuth.status;
    }

    function emailAuthCheck(){
        $emailAuthErrorDisp.css("display", "none");
        $.ajax({
            type : 'post',
            url : '/members/emailCheck',
            data : {
                emailAuthInput: $emailAuthInput.val()
            },
            success : function(result) { // 결과 성공 콜백함수
                if (result.statusCode != 200) {
                    $emailAuthErrorDisp.css("display", "block");
                    $("#emailAuthErrorMsg").text(result.responseMessage);
                    formReg.regAuth.msg = result.responseMessage;
                    return;
                }
                $emailCheck.css("display", "none");
                $emailAuthSuccess.css("display", "block");
                formReg.regAuth.status = true;
            },
        });
    }

    function sendSms() {
        $sendSms.html('재전송');
        $phoneAuthInput.attr('disabled', false);
        $phoneCheck.css('display', 'block');
        // $.ajax({
        //     type : 'post',
        //     url : '/members/sendSms',
        //     data : {
        //         phoneNumber: phoneNumber
        //     },
        //     success : function(result) { // 결과 성공 콜백함수
        Swal.fire({
            html: '<b>인증번호가 전송되었습니다.</b>',
            icon: 'success'
        });
        //     },
        // });
    }

    function phoneAuthCheck() {
        $.ajax({
            type : 'post',
            url : '/members/phoneCheck',
            data : {
                smsAuthInput: $phoneAuthInput.val()
            },
            success : function(result) { // 결과 성공 콜백함수
                if (result.statusCode != 200) {
                    $phoneAuthErrorDisp.css("display", "block");
                    formReg.regAuth.msg = result.responseMessage;
                    formReg.regAuth.status = false;
                    return;
                }
                $("#phoneCheck").css("display", "none");
                $phoneAuthErrorDisp.css("display", "none");
                $phoneAuthSuccess.css("display", "block");
                formReg.regAuth.status = true;
            },
        });
    }

    function loginPasswordReg() {
        let regExp = /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,25}$/g;
        formReg.regLoginPw.status = false;

        if ($loginPassword.val() < 8 || 16 < $loginPassword.val()
            || !regExp.test($loginPassword.val())) {
            $loginPwErrorDisp.css('display', 'block');
            return;
        }
        $loginPwErrorDisp.css('display', 'none');
        formReg.regLoginPw.status = true;
    }

    function loginPasswordCheckReg() {
        formReg.regLoginPwCheck.status = false;
        if ( $loginPasswordCheck.val() !== $loginPassword.val()) {
            $loginPwCheckErrorDisp.css('display', 'block');
            return;
        }
        $loginPwCheckErrorDisp.css('display', 'none');
        formReg.regLoginPwCheck.status = true;
    }
});