$(document).ready(function (){
    ClassicEditor
        .create( document.querySelector( '.editor' ), {
            // Editor configuration.
        } )
        .then( editor => {
            window.editor = editor;
        } )
        .catch( handleSampleError );

    function handleSampleError( error ) {
        const issueUrl = 'https://github.com/ckeditor/ckeditor5/issues';

        const message = [
            'Oops, something went wrong!',
            `Please, report the following error on ${ issueUrl } with the build id "pl7dgf100hue-ww6gt4b4sc9c" and the error stack trace:`
        ].join( '\n' );

        console.error( message );
        console.error( error );
    }
})