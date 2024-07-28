$(function(){

    let price;
    let priceAll = 0;
    let orderCheck = 0;

    $(".price").text(function(index, element ) {

        price = Number(element);
        priceAll += price;

        $('#priceAll').text(priceAll.toLocaleString('ko-KR'));
//        $('.priceAll2').text(priceAll.toLocaleString('ko-KR'));
    });


    $('#orderAllBtn').click(function (){

        if (cartLenth <= 0){
            Swal.fire({
                html: '<b>장바구니에 담긴 상품이 없습니다.</b>',
                icon:'warning',                         // Alert 타입
            });
            return
        }

        $(location).attr('href', '/order/new');
    });

    $('#orderBtn').click(function (){

        $.each($('.cartCheck'), function (idx, item){
            if($(this).is(':checked')){
                orderCheck += 1;
            }
        });

        if(orderCheck <= 0){
            Swal.fire({
                html: '<b>상품을 선택해주세요.</b>',
                icon:'warning',                         // Alert 타입
            });
            return
        }else{
            $('#newFrom').submit();
        }

    });

    $('#cartList').click(function (){
        if($('#cartList').is(':checked')){
            $('.cartCheck').prop('checked',true);
        }else {
            $('.cartCheck').prop('checked',false);
        }

    });

    $('.cartCheck').click(function (){
        $.each($('.cartCheck'), function (idx, item){
           if(!$(this).is(':checked')){
               $('#cartList').prop('checked',false);
           }
        });

    });


})
function quantityUpdate(id, quantity, inventory, plusMinus){

    console.log("id : ", id, " quantity : ", quantity, " inventory : ", inventory, " plusMinus : ", plusMinus);

    let data;

    if(plusMinus == 'minus'){
        let quan = quantity-1;
        if(quan <= 0){
            Swal.fire({
                html: '<b>상품을 1개 이상 선택해주세요</b>',
                icon:'warning',                         // Alert 타입
            });
            return
        }
        data = {"productInfoId":id, "quantity":quan}
    }else if (plusMinus == 'plus'){
        let quan = quantity+1;
        if(quan > inventory){
            Swal.fire({
                title:'상품의 수량이 재고수량보다 많습니다.',         // Alert 제목
                icon:'warning',                         // Alert 타입
            });
            return
        }
        data = {"productInfoId":id, "quantity":quan}
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

function cartOneDelete(id) {

    Swal.fire({
        html:'<b>선택하신 상품을 삭제하시겠습니까?</b>',         // Alert 제목
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '삭제',
        cancelButtonText: '취소'
    }).then(function(result) {
        if(result.value){
            $.ajax({
                type: 'post',
                url: '/cart/'+id+'/delete',
                contentType:"application/json;charset=utf-8",
                success :function(data){
                    console.log("delete성공이라해주라 ", data);

                        window.location.reload(true);

                },
                error:function(request, status, error){
                    console.log("code : "+request.status+"\n"+"message : "+request.responseText+"\n"+"error : "+error);
                    Swal.fire(
                        "삭제 실패",
                        "에러 났어요!", // had a missing comma
                        "error"
                    )
                }
            });
        }
    });
}

function optionUpdate(productId) { //보류

    console.log("productId : ", productId);

    $.ajax({
        type: 'GET',
        url: '/cart/'+productId+'/edit/option',
        contentType:"application/json;charset=utf-8",
        // beforeSend:function(xhr){
        //     xhr.setRequestHeader(header, token);
        // },
        success :function(data){
            console.log("delete성공이라해주라 ", data);

            // window.location.reload(true);

        },
        error:function(request, status, error){
            console.log("code : "+request.status+"\n"+"message : "+request.responseText+"\n"+"error : "+error);
            Swal.fire({
                html: '<b>옵션변경 실패</b>',         // Alert 제목
                icon: "error"
            })
        }

    });

}
