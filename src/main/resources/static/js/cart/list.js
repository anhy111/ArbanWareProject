$(function(){
    // alert("하이");

})
function quantityUpdate(id, quantity, plusMinus){

    let data;

    if(plusMinus == 'minus'){
        let quan = quantity-1;
        if(quan <= 0){
            Swal.fire({
                title:'1개 이상 선택해주세요',         // Alert 제목
                icon:'warning',                         // Alert 타입
            });
            return
        }
        data = {"productInfoId":id, "quantity":quan}
    }else if (plusMinus == 'plus'){
        let quan = quantity+1;
        data = {"productInfoId":id, "quantity":quantity+1}
    }

    $.ajax({
        type: 'post',
        url: '/cart/'+id+'/edit/quantity',
        data:JSON.stringify(data),
        contentType:"application/json;charset=utf-8",
        success :function(data){
            window.location.reload(true);

        }
    });
}
