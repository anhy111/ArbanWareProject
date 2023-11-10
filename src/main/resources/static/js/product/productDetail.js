$(document).ready(function () {

    let $amount = $('#amount');

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
    });

    $("#addAmount").on('click', function () {
        let val = $amount.val();
        if (!regNumberAndSwal(val)) {
            return;
        }

        $amount.val(val - 0 + 1);
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
