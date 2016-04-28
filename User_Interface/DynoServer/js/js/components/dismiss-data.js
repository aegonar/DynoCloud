$('[data-dismiss=modal]').on('click', function (e) {
var $t = $(this),
    target = $t[0].href || $t.data("target") || $t.parents('.modal') || [];

$(target)
.find("input,textarea,select")
   .val('')
   .end()
.find("input[type=checkbox], input[type=radio]")
   .prop("checked", "")
   .end()
.find("span")
	.text('')
	.hide()
	.empty();
})