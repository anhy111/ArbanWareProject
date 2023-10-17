$(document).ready(function (){
    ClassicEditor
        .create( document.querySelector( '#editor' ) ,{
            extraPlugins: [ MyCustomUploadAdapterPlugin ],
            removePlugins: [ 'ImageInsert', 'MediaEmbed' ]
        })
        .then( editor => {
            window.editor = editor
        } );

    $("#price").on('click', function () {
        const editorData = editor.getData();
        console.log(editorData);
    });
})

function MyCustomUploadAdapterPlugin( editor ) {
    editor.plugins.get( 'FileRepository' ).createUploadAdapter = ( loader ) => {
        // Configure the URL to the upload script in your back-end here!
        return new UploadAdapter( loader );
    };
}