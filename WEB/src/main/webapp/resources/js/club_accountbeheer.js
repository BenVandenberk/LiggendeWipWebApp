$(function() {

    var hoogte = $("#frm_afmetingen\\:spin_hoogte_input").val();
    var breedte = $("#frm_afmetingen\\:spin_breedte_input").val();

    $("#frm_logo\\:img_logo").attr("height", hoogte);
    $("#frm_logo\\:img_logo").attr("width", breedte);

    $("#frm_afmetingen\\:spin_hoogte_input").change(function() {
        $("#frm_logo\\:img_logo").css({height: $(this).val()});
    });

    $("#frm_afmetingen\\:spin_breedte_input").change(function() {
        $("#frm_logo\\:img_logo").width($(this).val());
    });
});

function uploadComplete() {
    var hoogte = $("#frm_afmetingen\\:spin_hoogte_input").val();
    var breedte = $("#frm_afmetingen\\:spin_breedte_input").val();

    $("#frm_logo\\:img_logo").attr("height", hoogte);
    $("#frm_logo\\:img_logo").attr("width", breedte);

    $("#frm_afmetingen\\:spin_hoogte_input").change(function() {
        $("#frm_logo\\:img_logo").css({height: $(this).val()});
    });

    $("#frm_afmetingen\\:spin_breedte_input").change(function() {
        $("#frm_logo\\:img_logo").width($(this).val());
    });
}
