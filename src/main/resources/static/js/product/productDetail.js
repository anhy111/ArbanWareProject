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
});
