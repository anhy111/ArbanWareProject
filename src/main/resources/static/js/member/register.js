window.onload = function (){

    const $phone_disp = $("#phone_disp").css("display", "none");
    const $email_disp = $("#email_disp").css("display", "none");

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
        Swal.fire('인증번호가 전송되었습니다.');
        $phone_disp.css("display", "block");
    })

    $("#sendEmail").on('click', function (){
        $(this).html('재전송')
        Swal.fire('인증번호가 전송되었습니다.');
        $email_disp.css("display", "block");
        let recipient = {
            "address":$("#email").val(),
            "name":$("#name").val(),
            "type":"R"
        };

        console.log(recipient);

        $.ajax({
            type : 'post',
            url : '/members/email',
            headers : {              // Http header
                "Content-Type": "application/json"
            },
            data : JSON.stringify(recipient),
            success : function(result) { // 결과 성공 콜백함수
                console.log(result);
            },
        })
    })

    $("#emailCheck").on('click', function (){

    });


}

