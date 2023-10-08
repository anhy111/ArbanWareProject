$(function(){
    // alert("하이");

    let titleUpdate = $('#title').val();
    let contentUpdate = $('#content').val();

    let today = new Date();

    $('#title').val(title);
    $('#name').val(name);
    $('#content').val(content);
    $('#today').text(today.toLocaleDateString());

    $('#cancel').click(function (){
        $(location).attr('href', '/notice')
    });

    $('#update').click(function (){
        let data = {'title':titleUpdate, 'content':contentUpdate}

        Swal.fire({
            title: '수정 하시겠습니까?',
            // text: "한번 등록한 내용은 되돌릴 수 없습니다.",
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '수정',
            cancelButtonText: '취소'
        }).then(function(result) {
            console.log(">>>>>>>>>>>result", result);
            if(result.value){
                $.ajax({
                    type: 'post',
                    url: '/notice/'+id+'/edit',
                    contentType:"application/json;charset=utf-8",
                    data:JSON.stringify(data),
                    // beforeSend:function(xhr){
                    //     xhr.setRequestHeader(header, token);
                    // },
                    success :function(data){
                        console.log("delete성공이라해주라 ", data);

                        Swal.fire(
                            '수정 완료',
                            '정상적으로 등록 되었습니다.',
                            'success'
                        ).then(function(){
                            location.replace("/notice");
                        });

                    },
                    error:function(request, status, error){
                        console.log("code : "+request.status+"\n"+"message : "+request.responseText+"\n"+"error : "+error);
                        Swal.fire(
                            "수정 실패",
                            "에러 났어요!", // had a missing comma
                            "error"
                        )
                    }

                });
            }
        });
    });
})