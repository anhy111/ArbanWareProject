$(function(){
    // alert("하이");



    $('#cancel').click(function (){
        $(location).attr('href', '/notice')
    });

    $('#register').click(function (){
        Swal.fire({
            title: '등록 하시겠습니까?',
            // text: "한번 등록한 내용은 되돌릴 수 없습니다.",
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '등록',
            cancelButtonText: '취소'
        }).then(function(result) {
            console.log(">>>>>>>>>>>result", result);
            if(result.value){
                $.ajax({
                    type: 'post',
                    url: '/notice/new',
                    contentType:"application/json;charset=utf-8",
                    beforeSend:function(xhr){
                        xhr.setRequestHeader(header, token);
                    },
                    success :function(data){
                        console.log("delete성공이라해주라 ", data);

                        Swal.fire(
                            '등록 완료',
                            '정상적으로 등록 되었습니다.',
                            'success'
                        ).then(function(){
                            window.location.reload(true);
                        });

                    },
                    error:function(request, status, error){
                        console.log("code : "+request.status+"\n"+"message : "+request.responseText+"\n"+"error : "+error);
                        Swal.fire(
                            "등록 실패",
                            "에러 났어요!", // had a missing comma
                            "error"
                        )
                    }

                });
            }
        });
    });
})