$(document).ready(function () {


    $(".color").css('backgroundColor', function () {
        let color = $(this).data('color');
        if (color == 'WHITE') {
            return '#feffef'
        }
        return color;
    });
});