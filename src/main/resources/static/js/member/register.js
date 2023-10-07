window.onload = function (){
    document.getElementById("searchAddress").onclick = function() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분
                document.getElementById('address.zipcode').value = data.zonecode;
                document.getElementById("address.city").value = data.roadAddress;
                document.getElementById("address.street").focus();
            }
        }).open();
    }
}

