$(function(){
    // alert("하이");

    $('#listBtn').click(function (){
        $(location).attr('href', '/notice')
    });

    $('#delete').click(function (){
        Swal.fire({
            title: '정말로 삭제 하시겠습니까?',
            text: "한번 삭제한 내용은 되돌릴 수 없습니다.",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '삭제',
            cancelButtonText: '취소'
        }).then(function(result) {
            console.log(">>>>>>>>>>>result", result);
            if(result.value){
                $.ajax({
                    type: 'post',
                    url: '/notice/'+id+'/delete',
                    contentType:"application/json;charset=utf-8",
                    // beforeSend:function(xhr){
                    //     xhr.setRequestHeader(header, token);
                    // },
                    success :function(data){
                        console.log("delete성공이라해주라 ", data);

                        Swal.fire(
                            '삭제 완료',
                            '정상적으로 삭제 되었습니다.',
                            'success'
                        ).then(function(){
                            window.location.reload(true);
                        });

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
    });


})