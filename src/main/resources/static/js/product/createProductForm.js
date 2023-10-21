$(document).ready(function (){
    ClassicEditor
        .create( document.querySelector( '#editor' ) , {
            extraPlugins: [MyCustomUploadAdapterPlugin],
            removePlugins: ['ImageInsert', 'MediaEmbed'],
            image: {
                resizeOptions: [
                    {
                        name: 'resizeImage:25',
                        value: '25',
                        icon: 'small'
                    },
                    {
                        name: 'resizeImage:50',
                        value: '50',
                        icon: 'medium'
                    },
                    {
                        name: 'resizeImage:original',
                        value: null,
                        icon: 'original'
                    }
                ],
                toolbar: [
                    'toggleImageCaption', 'imageTextAlternative', '|',
                    'imageStyle:inline',
                    'imageStyle:block',
                    'imageStyle:side', '|',
                    'resizeImage:25', 'resizeImage:50', 'resizeImage:original',
                ]
            }
        })
        .then( editor => {
            window.editor = editor
        } );

    $("#price").on('click', function () {
        const editorData = editor.getData();
        console.log(editorData);
    });

    $(".color").css('backgroundColor', function () {
        let color = $(this).data('color');
        if (color == 'WHITE') {
            $(this).css('border', '1px solid black');
        }
        return color;
    });
})

function MyCustomUploadAdapterPlugin( editor ) {
    editor.plugins.get( 'FileRepository' ).createUploadAdapter = ( loader ) => {
        // Configure the URL to the upload script in your back-end here!
        return new UploadAdapter( loader );
    };
}