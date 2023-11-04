let $searchForm;
$(document).ready(function () {

    $searchForm = $("#searchForm");
    let $minPrice = $("#minPrice");
    let $maxPrice = $("#maxPrice");

    $("input[name=colors]").on('change', function () {
        searchSubmit()
    });


    $(".conditionPrice").on('click', function () {
        $minPrice.val($(this).data("min-price"));
        $maxPrice.val($(this).data("max-price"));
        searchSubmit()
    });

    $("#priceSearch").on("click", function () {
        searchSubmit()
    });

    $("input[name=sizes]").on("change", function () {
        searchSubmit()
    });

    $("#sortProperty").on('change', function () {
        searchSubmit()
    });

    $(".page-link").on('click', function () {
        $("#page").val($(this).val());
        searchSubmit()
    });

    $(".color").css('backgroundColor', function () {
        let color = $(this).data('color');
        if (color == 'WHITE') {
            return '#feffef'
        }
        return color;
    });

});

function searchSubmit() {
    let keyword = $("#searchProductInput").val();
    $("#name").val(keyword);
    $searchForm.submit();
}
