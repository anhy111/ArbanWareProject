$(document).ready(function () {
    $("#searchProductInput").keyup(function () {
        if (window.event.keyCode == 13) {
            location.href = $("#searchProductBtn").prop('href');
        }
    });
});

function searchProductKeyword() {
    location.href = '/products?name=' + $("#searchProductInput").val();
}