$(document).ready(function () {

    if ($("#reviewTabs").val() == 'true') {
        $("#reviewTab").trigger("click");
    }

    let $amount = $('#amount');
    let $amountPrint = $('#amountPrint');

    ClassicEditor
        .create( document.querySelector( '#content' ) )
        .then( editor => {
            window.editor = editor;
            editor.setData(content);
            const toolbarElement = editor.ui.view.toolbar.element;
            editor.on( 'change:isReadOnly', ( evt, propertyName, isReadOnly ) => {
                if ( isReadOnly ) {
                    toolbarElement.style.display = 'none';
                } else {
                    toolbarElement.style.display = 'flex';
                }
            } );
            editor.enableReadOnlyMode('#content');
        } );

    $(".owl-carousel").owlCarousel({
        items : 5,
        margin: 5,
        loop : true,
        dots : false,
    });

    let $collapseOne = $("#collapseOne");
    let $collapseOne_down = $("#collapseOne-down");
    let $collapseOne_up = $("#collapseOne-up");
    $("#collapse1").on('click', function () {
        if ($collapseOne.css('display') === 'block') {
            $collapseOne_down.css('display', 'block');
            $collapseOne_up.css('display', 'none');
        } else {
            $collapseOne_down.css('display', 'none');
            $collapseOne_up.css('display', 'block');
        }

    });

    let $collapseTwo = $("#collapseTwo");
    let $collapseTwo_down = $("#collapseTwo-down");
    let $collapseTwo_up = $("#collapseTwo-up");
    $("#collapse2").on('click', function () {
        if ($collapseTwo.css('display') === 'block') {
            $collapseTwo_down.css('display', 'block');
            $collapseTwo_up.css('display', 'none');
        } else {
            $collapseTwo_down.css('display', 'none');
            $collapseTwo_up.css('display', 'block');
        }

    });

    let $collapseThree = $("#collapseThree");
    let $collapseThree_down = $("#collapseThree-down");
    let $collapseThree_up = $("#collapseThree-up");
    $("#collapse3").on('click', function () {
        if ($collapseThree.css('display') === 'block') {
            $collapseThree_down.css('display', 'block');
            $collapseThree_up.css('display', 'none');
        } else {
            $collapseThree_down.css('display', 'none');
            $collapseThree_up.css('display', 'block');
        }

    });

    $("#subAmount").on('click', function () {
        let val = $amount.val();

        if (!regNumberAndSwal(val)) {
            return;
        }

        if (val <= 1) {
            return;
        }

        $amount.val($amount.val() - 1);
        $amountPrint.text($amount.val());
    });

    $("#addAmount").on('click', function () {
        let val = $amount.val();
        if (!regNumberAndSwal(val)) {
            return;
        }

        $amount.val(val - 0 + 1);
        $amountPrint.text($amount.val());
    });

    $("#cartBtn").on('click', function () {
        if (cartAndOrderNew() == false) {
            return;
        }

        $('#newForm').attr('action', '/cart/new');
        $('#newForm').submit();

    });

    $("#orderBtn").on('click', function () {
        if (cartAndOrderNew() == false) {
            return;
        }

        $('#newForm').attr('action', '/order/new');
        $('#newForm').submit();

    });

    if ($("#reviewImgs img").length == 0) {
        $("#reviewImgs").css('display', 'none');
        $("#none_photo").css('display', 'block');
    }

    $("#review_form_btn").on('click', function () {
        let data = {
            productId : $("#reviewForm #productId").val()
        };

        console.log(data);
        $.ajax({
            type: 'get',
            url: '/review/orderCheck',
            data: data,
            contentType:"application/x-www-form-urlencoded;charset=UTF-8",
            success :function(data){
                console.log(data)

                if (data.statusCode == 401) {
                    location.href = '/login';
                } else if (data.statusCode == 204) {
                    Swal.fire({
                        html: '<b>' + data.responseMessage + '</b>',
                        icon: 'error'
                    });
                } else {
                    $('#reviewModal').modal("show");
                    $("#reviewForm #orderProductId").val(data.data);
                }
            },
            error:function(request,status,error){
                console.log("code" + request.status +"message" + request.responseText + " error " + error);
            }
        });
    });

    let $rating = $("#rating");
    let $ratingSpan = $(".rating span");
    $ratingSpan.hover(function () { // 마우스 들어올 때
        let rate = $(this).data('rate')
        $ratingSpan.html(function (inx, val) {
            if (inx + 1 <= rate) {
                return "★";
            } else {
                return "☆";
            }
        });
    }, function () {                // 마우스 벗어날떄
        $ratingSpan.html('☆');
    }).on('click', function () {
        $ratingSpan.unbind('mouseenter mouseleave');
        let rate = $(this).data('rate')
        $ratingSpan.html(function (inx, val) {
            if (inx + 1 <= rate) {
                return "★";
            } else {
                return "☆";
            }
        });
        $rating.val($(this).data("rate"));
    });

    $('#images').on('change', function() {
        $(".image").remove();
        if (this.files && this.files[0]) {
            for(let file of this.files) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    $('#beforePreview').before('<div class="col-3 px-1 mb-3 image">' +
                        '<img src="' + e.target.result + '"style="width: 100%; height: 100px; border: 1px solid #e6e6e6 !important;">' +
                        '</div>');
                };
                reader.readAsDataURL(file);
            }
        }
    });

    $("#createReview").on('click', function () {
        $("#reviewForm").submit();
    });

    $(".review-edit").on('click', function () {
        let reviewId = $(this).data('edit');
        let findReview = reviews.find(review => {
            return review.reviewId == reviewId;
        });

        if (findReview == null) {
            return;
        }

        $("#reviewForm #reviewId").val(reviewId);

        $ratingSpan.unbind('mouseenter mouseleave');

        $("#reviewModal").modal('show');
        $("#reviewForm").attr('action', "/review/edit");

        $("#reviewForm #rating").val(findReview.rating)
        $(".rating span").html(function (inx, html) {
            if (inx + 1 <= findReview.rating) {
                return '★';
            }
            return '☆';
        });

        $(".image").remove();

        $("#reviewForm #content").text(findReview.content);
        if (findReview.images == null){
           return;
        }

        findReview.images.forEach(function (image, inx) {
            $('#beforePreview').before('<div class="col-3 px-1 mb-3 image">' +
                '<img src="' + '/upload/' + image.storedPath + image.storedFileName + '"style="width: 100%; height: 100px; border: 1px solid #e6e6e6 !important;">' +
                '</div>');
        });
    });

    $(".review-delete").on('click', function () {
        let data = {
            reviewId : $(this).data('delete')
        };

        Swal.fire({
            html: '<b>리뷰를 삭제하시겠습니까?</b>',
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor : "#d33",
            confirmButtonText: "삭제",
            cancelButtonText: "취소",
            reverseButtons: true
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    type: 'post',
                    url: '/review/delete',
                    data: data,
                    contentType:"application/x-www-form-urlencoded;charset=UTF-8",
                    success :function(data){
                        console.log(data);
                        if (data.statusCode != 200) {
                            Swal.fire({
                                html:'<b>' + data.responseMessage + '</b>',
                                icon : 'error'
                            })
                            return;
                        }
                        Swal.fire({
                            html: '<b>' + data.responseMessage + '</b>',
                            icon: 'success'
                        }).then(function () {
                            window.location = window.location.pathname + '?reviewTabs=true';
                        });
                    }
                });
            }
        });
    });

    $(".page-link").on('click', function () {
        $("#page").val($(this).val());
        if ($("#reviewTab").hasClass("active")) {
            $("#reviewTabs").val(true);
        }
        $("#reviewSearchForm").submit();
    });

    $(".sort-btn").on('click', function () {
        $("#sort").val($(this).val());
        if ($("#reviewTab").hasClass("active")) {
            $("#reviewTabs").val(true);
        }
        $("#reviewSearchForm").submit();
    });

    $("#contentTab").on('click', function () {
        $("#reviewTabs").val(false);
    });

    $("#infoTab").on('click', function () {
        $("#reviewTabs").val(false);
    });

    $(".img-modal").on('click', function () {
        if ($(this).hasClass("image-box")) {
            $(this).removeClass("image-box")
                .children("img").css("object-fit", "contain")
        } else {
            $(this).addClass("image-box")
                .children("img").css("object-fit", "");
        }
    });

    function regNumberAndSwal(val) {
        let regExp = /^[0-9]*$/g;
        if (regExp.test(val)) {
            return true;
        }
        Swal.fire({
            html:'<b>숫자만 입력해주세요.</b>',
            icon : 'error'
        })
        return false;
    }

    function cartAndOrderNew(){
        let checkSize = $('input[name=size]:checked').val();
        let checkColor = $('input[name=color]:checked').val();
        let amount = $amount.val();

        if(checkColor == null){
            Swal.fire({
                html:'<b>색상을 선택해 주세요.</b>',
                icon : 'warning'
            })
            return false;
        }else if(checkSize == null){
            Swal.fire({
                html:'<b>사이즈를 선택해 주세요.</b>',
                icon : 'warning'
            })
            return false;
        }else if(amount <= 0){
            Swal.fire({
                html:'<b>수량을 1개 이상 선택해주세요.</b>',
                icon : 'warning'
            })
            return false;
        }

        return true;
    }
});
