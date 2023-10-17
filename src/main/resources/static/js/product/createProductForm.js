$(document).ready(function (){
    ClassicEditor
        .create( document.querySelector( '#editor' ) )
        .then( editor => {
            window.editor = editor
        } );

    $("#price").on('click', function () {
        const editorData = editor.getData();
        console.log(editorData);
    });
})