$(function(){
    // alert("하이");

    let price;
    let priceAll = 0;
    let requirements;

    let clientKey = 'test_ck_DLJOpm5QrlxzAkdbmPdrPNdxbWnY' // 테스트용 클라이언트 키
    let tossPayments = TossPayments(clientKey)

    $("#searchAddress").on('click',searchAddressFunc); //우편번호 불러오기
    // $('#orderBtn').on('click', paymentStart); //주문 및 결제 시작

    $(".price").text(function(index, element) {

        console.log("index : ", index, " element : ", element)

        price = Number(element);
        priceAll += price;

        $('#priceAll').text(priceAll.toLocaleString('ko-KR') + '원');
        $('#orderBtn').text(priceAll.toLocaleString('ko-KR') + '원 주문하기');
    });

    $("select[name=deliveryRequest]").change(function(){
        requirements = $("select[name=deliveryRequest] option:selected").text();

        console.log(requirements); //text값 가져오기
    });

    $('#orderBtn').click(function (){

        let orderer = $('#inputOrderer').val(); //주문자
        let email = $('#inputEmail').val();
        let ordererPhone = $('#inputPhone').val();

        let recipient = $('#inputRecipient').val(); //받는 사람
        let recipientPhone = $('#recipientPhone').val();
        let zipcode = $('#zipcodeView').val(); //우편 번호
        let city = $('#cityView').val(); //기본 주소
        let street = $('#street').val(); //나머지 주소

        let deliverySave = $('#deliverySave').is(':checked');



        console.log("email : ", email, " name : ", orderer, " productName : ", productName, " cartLenth : ", cartLenth, " deliverySave : ", deliverySave,
            " recipient : ", recipient, " zipcode : ", zipcode, " city : ", city, " street : ", street);

        // ------ 결제창 띄우기 ------
        tossPayments.requestPayment({
            amount: priceAll,
            orderId: '165165165151kkhhiu', //6자 이상
            orderName: productName + ' 외 ' + (cartLenth-1),
            customerName: orderer,
            customerEmail: email,
            successUrl: 'http://localhost:8088/order/success',
            failUrl: 'http://localhost:8088/order/fail'
            // successUrl: 'http://localhost:8081/api/v1/payments/toss/success',
            // failUrl: 'http://localhost:8081/api/v1/payments/toss/fail'
        });

    });

});

function searchAddressFunc() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분
            $("#zipcode").val(data.zonecode);
            $("#zipcodeView").val(data.zonecode);
            $("#city").val(data.roadAddress);
            $("#cityView").val(data.roadAddress);
            $("#street").focus();
            formReg.regAddress.status = true;
        }
    }).open();
}

function paymentStart() {

    let email = $('#inputEmail').val();
    let orderer = $('#inputOrderer').val();
    let deliverySave = $('#deliverySave').is(':checked');

    console.log("email : ", email, " name : ", orderer, " productName : ", productName, " cartLenth : ", cartLenth, " deliverySave : ", deliverySave);

    // ------ 결제창 띄우기 ------
    tossPayments.requestPayment('CARD', {
        amount: priceAll,
        orderId: '100000', //6자 이상
        orderName: productName + ' 외 ' + (cartLenth-1),
        customerName: orderer,
        customerEmail: email,
        successUrl: 'http://localhost:8088/order/success',
        failUrl: 'http://localhost:8088/order/fail'
        // successUrl: 'http://localhost:8081/api/v1/payments/toss/success',
        // failUrl: 'http://localhost:8081/api/v1/payments/toss/fail'
    });

}