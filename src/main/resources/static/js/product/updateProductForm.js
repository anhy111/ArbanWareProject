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
            const imageUploadEditing = editor.plugins.get( 'ImageUploadEditing' );

            imageUploadEditing.on( 'uploadComplete', ( evt, { data, imageElement } ) => {
                editor.model.change( writer => {
                    writer.setAttribute( 'alt', data.imageId, imageElement );
                } );
            } );
        } );

    $(".color").css('backgroundColor', function () {
        let color = $(this).data('color');
        if (color == 'WHITE') {
            $(this).css('border', '1px solid black');
        }
        return color;
    });

    $('#thumbnail').on('change', function() {
        let imagePreview = $('#image-preview');
        if (this.files && this.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                imagePreview.attr('src', e.target.result);
            };
            reader.readAsDataURL(this.files[0]);
            $('#thumbnail_label').text(this.files[0].name);
            $("#image-preview_div").css('display', 'block');
        }
    });

    $("#update").on('click', function () {
        let str  = '';
        $('figure img').attr('alt', function (inx, val) {
            str += val + ',';
            return val;
        });

        $('#productImages').val(str);
        $("#updateForm").submit();
    });
});

function MyCustomUploadAdapterPlugin( editor ) {
    editor.plugins.get( 'FileRepository' ).createUploadAdapter = ( loader ) => {
        // Configure the URL to the upload script in your back-end here!
        return new UploadAdapter( loader );
    };
}