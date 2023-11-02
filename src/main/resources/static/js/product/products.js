$(document).ready(function () {

    let $searchForm = $("#searchForm");
    let $minPrice = $("#minPrice");
    let $maxPrice = $("#maxPrice");

    $("input[name=colors]").on('change', function () {
        $searchForm.submit();
    });


    $(".conditionPrice").on('click', function () {
        $minPrice.val($(this).data("min-price"));
        $maxPrice.val($(this).data("max-price"));
        $searchForm.submit();
    });

    $("#priceSearch").on("click", function () {
        $searchForm.submit();
    });

    $("input[name=sizes]").on("change", function () {
        $searchForm.submit();
    });

    $("#sort").on('change', function () {
        $searchForm.submit();
    });

    $(".color").css('backgroundColor', function () {
        let color = $(this).data('color');
        if (color == 'WHITE') {
            return '#feffef'
        }
        return color;
    });
});