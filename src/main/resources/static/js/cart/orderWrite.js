$(function(){
    // alert("하이");

    let price;
    let priceAll = 0;

    $("#searchAddress").on('click',searchAddressFunc);

    $(".price").text(function(index, element ) {

        price = Number(element);
        priceAll += price;

        $('#priceAll').text(priceAll.toLocaleString('ko-KR') + '원');
        $('#orderBtn').text(priceAll.toLocaleString('ko-KR') + '원 주문하기');
    });

    $('#orderBtn').click(function (){
        $(location).attr('href', '/cart/orderWrite');
    });

})

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