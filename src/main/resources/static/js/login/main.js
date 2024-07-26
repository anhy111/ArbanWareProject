(function($) {

	$("#member-login").on("click", function () {
		$("input[name=loginId]").val("member");
		$("input[name=loginPassword]").val("1234");

		$("#login-form").submit();
	});

	$("#admin-login").on("click", function () {
		$("input[name=loginId]").val("admin");
		$("input[name=loginPassword]").val("1234");

		$("#login-form").submit();
	});


})(jQuery);
