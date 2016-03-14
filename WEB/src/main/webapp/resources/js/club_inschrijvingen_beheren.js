$(function () {
    bindExpandHandlers();
    restoreState();
});

function bindExpandHandlers() {
    $(document).on("click", ".ploegenHeader", function () {
        $(".ploegenContent").slideToggle(500);
        $(".upExpandPloegen").toggle();
        $(".downExpandPloegen").toggle();

        var ploegenZichtbaar = toBoolean($("#frm\\:ploegenZichtbaar").val());
        $("#frm\\:ploegenZichtbaar").val(!ploegenZichtbaar);
    });

    $(document).on("click", ".maaltijdenHeader", function () {
        $(".maaltijdenContent").slideToggle(500);
        $(".upExpandMaaltijden").toggle();
        $(".downExpandMaaltijden").toggle();

        var maaltijdenZichtbaar = toBoolean($("#frm\\:maaltijdenZichtbaar").val());
        $("#frm\\:maaltijdenZichtbaar").val(!maaltijdenZichtbaar);
    });

    $(document).on("click", ".afrekeningHeader", function () {
        $(".afrekeningContent").slideToggle(500);
        $(".upExpandAfrekening").toggle();
        $(".downExpandAfrekening").toggle();

        var afrekeningZichtbaar = toBoolean($("#frm\\:afrekeningZichtbaar").val());
        $("#frm\\:afrekeningZichtbaar").val(!afrekeningZichtbaar);
    });
}

function restoreState() {
    ploegenState();
    maaltijdenState();
    afrekeningState();
}

function ploegenState() {
    var ploegenZichtbaar = toBoolean($("#frm\\:ploegenZichtbaar").val());
    if (ploegenZichtbaar) {
        $(".ploegenContent").toggle();
        $(".upExpandPloegen").toggle();
        $(".downExpandPloegen").toggle();
    }
}

function maaltijdenState() {
    var maaltijdenZichtbaar = toBoolean($("#frm\\:maaltijdenZichtbaar").val());
    if (maaltijdenZichtbaar) {
        $(".maaltijdenContent").toggle(0);
        $(".upExpandMaaltijden").toggle();
        $(".downExpandMaaltijden").toggle();
    }
}

function afrekeningState() {
    var afrekeningZichtbaar = toBoolean($("#frm\\:afrekeningZichtbaar").val());
    if (afrekeningZichtbaar) {
        $(".afrekeningContent").toggle(0);
        $(".upExpandAfrekening").toggle();
        $(".downExpandAfrekening").toggle();
    }
}

function toBoolean(booleanString) {
    if (booleanString === "true") {
        return Boolean(1);
    } else {
        return Boolean(0);
    }
}
