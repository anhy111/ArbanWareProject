window.onload = function (){

    const $phone_disp = $("#phone_disp").css("display", "none");
    const $email_disp = $("#email_disp").css("display", "none");
    const $emailAuthSuccess = $("#emailAuthSuccess").css("display", "none");
    const $emailErrorDisp = $("#emailErrorDisp").css('display', 'none');
    const $emailAuthErrorDisp = $("#emailAuthErrorDisp").css('display', 'none');
    const $email = $("#email");
    let regEmail = false;

    $("#searchAddress").on('click',function () {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분
                document.getElementById('address.zipcode').value = data.zonecode;
                document.getElementById("address.city").value = data.roadAddress;
                document.getElementById("address.street").focus();
            }
        }).open();
    })

    $("#sendSMS").on('click', function (){
        $(this).html('재전송');
        Swal.fire({
            html: '<b>인증번호가 전송되었습니다.</b>',
            icon: 'success'
        });
        $phone_disp.css("display", "block");
    })

    $("#sendEmail").on('click', function (){
        if (!regEmail) {
            Swal.fire({
                html: '<b>올바른 이메일 형식이 아닙니다.</b>',
                icon: 'error'
            });
            return;
        }

        $(this).html('재전송')
        $email_disp.css("display", "block");
        let recipient = {
            "address":$("#email").val(),
            "name":$("#name").val(),
            "type":"R"
        };

        $.ajax({
            type : 'post',
            url : '/members/email',
            headers : {              // Http header
                "Content-Type": "application/json"
            },
            data : JSON.stringify(recipient),
            success : function(result) { // 결과 성공 콜백함수
                if (result == "fail") {
                    Swal.fire('잠시 후 다시 시도해주세요.');
                    return;
                }
                Swal.fire('인증번호가 전송되었습니다.');
            },
        })
    });

    $email.on('input', function() {
        let regExp = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
        if (regExp.test($email.val()) == false) {
            $emailErrorDisp.css('display', 'block');
            regEmail = false;
            return;
        }
        $emailErrorDisp.css('display', 'none');
        regEmail = true;
    })

    $("#emailCheck").on('click', function (){
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
                    return;
                }
                $("#emailCheck").css("display", "none");
                $emailAuthErrorDisp.css("display", "none");
                $emailAuthSuccess.css("display", "block");
            },
        });
    });


}

