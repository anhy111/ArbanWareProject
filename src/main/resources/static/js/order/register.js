$(function(){
    // alert("하이");

    let price;
    let productInfoId;
    let amount;
    let priceAll = 0;
    let requirements;

    let clientKey = 'test_ck_DLJOpm5QrlxzAkdbmPdrPNdxbWnY' // 테스트용 클라이언트 키
    let tossPayments = TossPayments(clientKey);

    let orderId = 0;
    let paymentId = 0;

    let orderProductArr = new Array();
    let orderProductData;

    $("#searchAddress").on('click',searchAddressFunc); //우편번호 불러오기
    // $('#orderBtn').on('click', paymentStart); //주문 및 결제 시작

    $(".price").text(function(index, element) {

        console.log("index : ", index, " element : ", element)

        orderProductData = new Object();
        price = Number(element);
        orderProductData.price = price;
        orderProductArr.push(orderProductData);

        priceAll += price;

        $('#priceAll').text(priceAll.toLocaleString('ko-KR') + '원');
        $('#orderBtn').text(priceAll.toLocaleString('ko-KR') + '원 주문하기');
    });

    $(".prodInfoId").text(function(index, element) {
        productInfoId = Number(element);
        orderProductArr[index].productInfo = {'id' : productInfoId};
    });

    $(".amount").text(function(index, element) {
        amount = Number(element);
        orderProductArr[index].amount = amount;
    });

    console.log(" orderProductArr : ", orderProductArr);

    $('#orderBtn').click(function (){

        let orderer = $('#inputOrderer').val(); //주문자
        let ordererPhone = $('#inputPhone').val();
        let email = $('#inputEmail').val();

        let recipient = $('#inputRecipient').val(); //받는 사람
        let recipientPhone = $('#recipientPhone').val(); //받는 사람 번호
        let zipcode = $('#zipcodeView').val(); //우편 번호
        let city = $('#cityView').val(); //기본 주소
        let street = $('#street').val(); //나머지 주소

        requirements = $("select[name=deliveryRequest] option:selected").text(); //요청사항

        // let deliverySave = $('#deliverySave').is(':checked');

        console.log("email : ", email, " name : ", orderer, " ordererPhone : ", ordererPhone, " productName : ", productName, " cartLenth : ", cartLenth,
            " recipient : ", recipient, " recipientPhone : ", recipientPhone, " zipcode : ", zipcode, " city : ", city, " street : ", street
            , " requirements : ", requirements);

        let orderData = {
            'member': {
                'id':customerKey
            },
            'status' : 'AWAITING_PAYMENT',
            'totalPrice' : priceAll,
            'totalPaymentPrice' : priceAll,
            'orderer' : orderer,
            'ordererPhoneNumber' : ordererPhone,
            'email' : email,
            'recipient':recipient,
            'recipientPhoneNumber':recipientPhone,
            'requirements':requirements,
            "address": {
                'zipcode':zipcode,
                'city':city,
                'street':street
            }
        };

        let paymentData = {
            'status' : 'AWAITING_PAYMENT',
            'totalAmount' : priceAll
        }

        if (orderId == 0) {
            findOrderSeq(orderData, paymentData);
        }
        console.log("orderId :  ", orderId);

        // ------ 결제창 띄우기 ------
        tossPayments.requestPayment('CARD',{
            amount: priceAll,
            orderId: orderId, //6자 이상
            orderName: productName + ' 외 ' + (cartLenth-1),
            customerName: orderer,
            customerEmail: email,
            // successUrl: 'http://localhost:8088/payment/success',
            // failUrl: 'http://localhost:8088/payment/fail'
        }).then(function (data) {
            // 성공 처리: 결제 승인 API를 호출하세요
            console.log("성공 data=", data);

            $.each(orderProductArr, function (idx, item){
                orderProductArr[idx].order = {'id' : orderId}
            })

            $.ajax({
                async:false,
                type: 'post',
                url: '/orderProduct/new',
                contentType:"application/json;charset=utf-8",
                data:JSON.stringify(orderProductArr),
                success :function(orderProductData){
                    console.log("orderProductData : ", orderProductData);

                },
                error:function(request, status, error){
                    console.log("code : "+request.status+"\n"+"message : "+request.responseText+"\n"+"error : "+error);
                }
            });

           location.replace( location.origin + '/payment/success?paymentKey=' + data.paymentKey
                                + '&orderId=' + data.orderId
                                + '&amount=' + data.amount
                                + '&paymentId=' + paymentId);


        }).catch(function (error) {
            console.log(error.code);
            if (error.code === 'PAY_PROCESS_CANCELED') {
                // 사용자의 결제취소
                Swal.fire({
                    html: '<b>결제가 취소되었습니다.</b>',
                    icon: 'info'
                });

                $.ajax({
                    async:false,
                    type: 'post',
                    url: '/payment/cancel/'+paymentId + '/' + orderId,
                    contentType:"application/json;charset=utf-8",
                    success :function(paymentCancelData){
                        console.log("paymentCancelData : ", paymentCancelData);

                    },
                    error:function(request, status, error){
                        console.log("code : "+request.status+"\n"+"message : "+request.responseText+"\n"+"error : "+error);
                    }
                });


            }
        });

        // window.location.reload(true);


        // if(deliverySave){ 배송지 저장
        //     if(deliveryYN ==null){
        //
        //     } else {
        //         $.ajax({
        //             type: 'post',
        //             url: '/order/'+customerKey+'/deliveryUpdate',
        //             contentType:"application/json;charset=utf-8",
        //             data:JSON.stringify(data),
        //             success :function(data){
        //                 console.log("deliverySave성공이라해주라 ", data);
        //                 // window.location.reload(true);
        //             },
        //             error:function(request, status, error){
        //                 console.log("code : "+request.status+"\n"+"message : "+request.responseText+"\n"+"error : "+error);
        //             }
        //         });
        //     }
        // }

    });


    function findOrderSeq(orderData, paymentData) {
        $.ajax({
            async:false,
            type: 'post',
            url: '/order/'+customerKey+'/new',
            contentType:"application/json;charset=utf-8",
            data:JSON.stringify(orderData),
            success :function(data){
                orderId = data.id;

                paymentData.order = {'id' : orderId};

                $.ajax({
                    async:false,
                    type: 'post',
                    url: '/payment/new',
                    contentType:"application/json;charset=utf-8",
                    data:JSON.stringify(paymentData),
                    success :function(returnPayment){
                        console.log("paymentData : ", returnPayment);
                        paymentId = returnPayment.id;

                    },
                    error:function(request, status, error){
                        console.log("code : "+request.status+"\n"+"message : "+request.responseText+"\n"+"error : "+error);
                        // orderId = 0;
                    }
                });
            },
            error:function(request, status, error){
                console.log("code : "+request.status+"\n"+"message : "+request.responseText+"\n"+"error : "+error);
                orderId = 0;
            }
        });
    }
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
            // formReg.regAddress.status = true;
        }
    }).open();
}

