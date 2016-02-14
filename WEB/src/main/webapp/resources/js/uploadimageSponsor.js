$(function() {

    var hoogte = $("#frm\\:spin_hoogte_input").val();
    var breedte = $("#frm\\:spin_breedte_input").val();

    $("#frm\\:img_logo").attr("height", hoogte);
    $("#frm\\:img_logo").attr("width", breedte);

    $("#frm\\:spin_hoogte_input").change(function() {
        $("#frm\\:img_logo").css({height: $(this).val()});
    });

    $("#frm\\:spin_breedte_input").change(function() {
        $("#frm\\:img_logo").width($(this).val());
    });
});

function uploadComplete() {
    var hoogte = $("#frm\\:spin_hoogte_input").val();
    var breedte = $("#frm\\:spin_breedte_input").val();

    $("#frm\\:img_logo").attr("height", hoogte);
    $("#frm\\:img_logo").attr("width", breedte);

    $("#frm\\:spin_hoogte_input").change(function() {
        $("#frm\\:img_logo").css({height: $(this).val()});
    });

    $("#frm\\:spin_breedte_input").change(function() {
        $("#frm\\:img_logo").width($(this).val());
    });
}
