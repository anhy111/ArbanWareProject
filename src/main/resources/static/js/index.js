$(document).ready(function () {

    $(".owl-carousel").owlCarousel({
        items : 4,
        margin: 20,
        loop : true,
        dots : false,
    });

    $(".product-color").css('backgroundColor', function () {
        let color = $(this).data('product-color');
        if (color == 'WHITE') {
            return '#feffef'
        }
        return color;
    });
});