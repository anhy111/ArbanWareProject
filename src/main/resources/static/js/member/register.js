window.onload = function (){

    const $phone_disp = $("#phone_disp");
    const $email_disp = $("#email_disp");
    const $emailAuthSuccess = $("#emailAuthSuccess");
    const $emailErrorDisp = $("#emailErrorDisp");
    const $emailAuthErrorDisp = $("#emailAuthErrorDisp");
    const $loginIdErrorDisp = $("#loginIdErrorDisp");
    const $loginIdSuccessDisp = $("#loginIdSuccessDisp");
    const $loginPwErrorDisp = $("#loginPwErrorDisp");
    const $loginPwCheckErrorDisp = $("#loginPwCheckErrorDisp");
    const $phoneNumberErrorDisp = $("#phoneNumberErrorDisp");
    const $smsAuthErrorDisp = $("#smsAuthErrorDisp");
    const $email = $("#email");
    const formReg = {
        regLoginId : { status : false, msg: "" },
        regLoginPw : { status : false, msg: '비밀번호는 영문과 숫자를 조합하고 8자~16자 사이로 입력해주세요.' },
        regLoginPwCheck : { status : false, msg: "비밀번호가 일치하지 않습니다." },
        regAddress : { status : false, msg: "주소를 입력해주세요." },
        regPhoneNumber : { status : false, msg: "" },
        regEmail : { status : false, msg: "" },
        regName : {status : false, msg : "이름을 입력해주세요"}
    }
    let emailRegCheck = false;


    $("#loginId").on('change', function () {
        let val = $(this).val();
        let regExp1 = /^[a-zA-Z0-9]*$/g;
        let regExp2 = /^[0-9]*$/g;
        let regExp3 = /^[0-9]$/g;

        $loginIdSuccessDisp.css('display', 'none');
        formReg.regLoginId.status = false;

        if (val.length < 4 || 16 < val.length) {
            $loginIdErrorDisp.css('display', 'block');
            formReg.regLoginId.msg = '아이디는 영문 소문자 또는 숫자 4~16자로 입력해 주세요.';
            $("#loginIdErrorMsg").text(formReg.regLoginId.msg);
            return;
        } else if (!regExp1.test(val) || regExp2.test(val) || regExp3.test(val)) {
            $loginIdErrorDisp.css('display', 'block');
            formReg.regLoginId.msg = '공백/특수 문자가 포함되거나, 숫자로 시작 또는 숫자로만 이루어진 아이디는 사용할 수 없습니다.';
            $("#loginIdErrorMsg").text(formReg.regLoginId.msg);
            return;
        }

        $.ajax({
            type : 'post',
            url : '/members/duplicateIdCheck',
            data : {
                loginId : $(this).val()
            },
            success : function(result) { // 결과 성공 콜백함수
                if (result.statusCode != 200) {
                    $loginIdErrorDisp.css('display', 'block');
                    formReg.regLoginId.msg = '이미 사용중인 아이디입니다.'
                    $("#loginIdErrorMsg").text(formReg.regLoginId.msg);
                    return;
                }
                $loginIdErrorDisp.css('display', 'none');
                $loginIdSuccessDisp.css('display', 'block');
                formReg.regLoginId.status = true;
            },
        })

    });

    $("#loginPassword").on('input', function() {
        let regExp = /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,25}$/g;
        formReg.regLoginPw.status = false;

        if ($(this).val() < 8 || 16 < $(this).val()
            || !regExp.test($(this).val())) {
            $loginPwErrorDisp.css('display', 'block');
            return;
        }
        $loginPwErrorDisp.css('display', 'none');
        formReg.regLoginPw.status = true;
    });

    $("#loginPasswordCheck").on('input', function() {
        formReg.regLoginPwCheck.status = false;
        if ($(this).val() !== $("#loginPassword").val()) {
            $loginPwCheckErrorDisp.css('display', 'block');
            return;
        }
        $loginPwCheckErrorDisp.css('display', 'none');
        formReg.regLoginPwCheck.status = true;
    });

    $("#name").on('change', function () {
        if ($(this).val().length < 2) {
            formReg.regName.status = false;
        }
        formReg.regName.status = true;
    });

    $("#searchAddress").on('click',function () {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분
                document.getElementById('zipcode').value = data.zonecode;
                document.getElementById("city").value = data.roadAddress;
                document.getElementById("street").focus();
                formReg.regAddress.status = true;
            }
        }).open();
    })

    $("#telephonedms3").on('change', function () {
        let telephonedms = $("#telephonedms1").val() + $("#telephonedms2").val() + $(this).val();
        let regExp = /^[0-9]*$/g;

        if (telephonedms.length < 9 || 10 < telephonedms.length
            || !regExp.test(telephonedms)) {
            return;
        }
        $("#telephonedms").val(telephonedms);
    });

    $("#sendSMS").on('click', function (){
        let phoneNumber = $("#phoneNumber1").val() + $("#phoneNumber2").val() + $("#phoneNumber3").val();
        let regExp = /^[0-9]*$/g;

        if (phoneNumber.length < 10 || 11 < phoneNumber.length
            || !regExp.test(phoneNumber)) {
            $phoneNumberErrorDisp.css('display', 'block');
            formReg.regPhoneNumber.status = false;
            formReg.regPhoneNumber.msg = '올바른 핸드폰 번호를 입력해주세요.';
            return;
        }
        $("#phoneNumber").val(phoneNumber);
        $phoneNumberErrorDisp.css('display', 'none');

        // $.ajax({
        //     type : 'post',
        //     url : '/members/sendSms',
        //     data : {
        //         phoneNumber: phoneNumber
        //     },
        //     success : function(result) { // 결과 성공 콜백함수
                $("#sendSMS").html('재전송');
                Swal.fire({
                    html: '<b>인증번호가 전송되었습니다.</b>',
                    icon: 'success'
                });

                $phone_disp.css("display", "block");
                $("#phoneCheck").css("display", "block");
                $("#smsAuthSuccess").css("display", 'none');
        //     },
        // });
    });

    $("#phoneCheck").on('click', function () {
        $.ajax({
            type : 'post',
            url : '/members/phoneCheck',
            data : {
                smsAuthInput: $("#smsAuthInput").val()
            },
            success : function(result) { // 결과 성공 콜백함수
                if (result.statusCode != 200) {
                    $smsAuthErrorDisp.css("display", "block");
                    $("#smsAuthErrorMsg").text(result.responseMessage);
                    formReg.regPhoneNumber.msg = result.responseMessage;
                    formReg.regPhoneNumber.status = false;
                    return;
                }
                $("#phoneCheck").css("display", "none");
                $smsAuthErrorDisp.css("display", "none");
                $("#smsAuthSuccess").css("display", "block");
                formReg.regPhoneNumber.status = true;
            },
        });
    });

    $email.on('input', function() {
        let regExp = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

        emailRegCheck = false;
        if (!regExp.test($email.val())) {
            $emailErrorDisp.css('display', 'block');
            formReg.regEmail.msg = '올바른 이메일 형식이 아닙니다.';
            return;
        }
        $emailErrorDisp.css('display', 'none');
        emailRegCheck = true;
    })

    $("#sendEmail").on('click', function (){
        if (!emailRegCheck) {
            Swal.fire({
                html: '<b>올바른 이메일 형식이 아닙니다.</b>',
                icon: 'error'
            });
            formReg.regEmail.msg = "올바른 이메일 형식이 아닙니다.";
            return;
        }

        $(this).html('재전송')
        $email_disp.css("display", "block");
        let recipient = {
            "address":$("#email").val(),
            "name":$("#name").val(),
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
        //                 icon: 'success'
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
    });


    $("#emailCheck").on('click', function (){
        $emailAuthErrorDisp.css("display", "none");
        $.ajax({
            type : 'post',
            url : '/members/emailCheck',
            data : {
                emailAuthInput: $("#emailAuthInput").val()
            },
            success : function(result) { // 결과 성공 콜백함수
                if (result.statusCode != 200) {
                    $emailAuthErrorDisp.css("display", "block");
                    $("#emailAuthErrorMsg").text(result.responseMessage);
                    formReg.regEmail.msg = result.responseMessage;
                    return;
                }
                $("#emailCheck").css("display", "none");
                $emailAuthSuccess.css("display", "block");
                formReg.regEmail.status = true;
            },
        });
    });

    $("#birth3").on('change', function (){
        let birth = $("#birth1").val() + $("#birth2").val() + $(this).val();
        let regExp = /^[0-9]*$/g;

        if (birth.length < 4 || 8 < birth.length
            || !regExp.test(birth)) {
            return;
        }
       $("#birth").val(birth);
    });

    $("#newForm").submit(function (event){
        for (let key in formReg) {
            let obj = formReg[key];
            if (!obj.status) {
                Swal.fire({
                    html: '<b>' + obj.msg + '</b>',
                    icon: 'error'
                });
                event.preventDefault();
                return;
            }
        }
        $("#zipcode").attr('disabled', false);
        $("#city").attr('disabled', false);
    });


}

