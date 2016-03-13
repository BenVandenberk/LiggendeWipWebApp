$(function () {
    $(".ploegenHeader").click(function () {
        $(".ploegenContent").slideToggle(500);
        $(".upExpandPloegen").toggle();
        $(".downExpandPloegen").toggle();
    });

    $(".maaltijdenHeader").click(function () {
        $(".maaltijdenContent").slideToggle(500);
        $(".upExpandMaaltijden").toggle();
        $(".downExpandMaaltijden").toggle();
    });

    $(".afrekeningHeader").click(function () {
        $(".afrekeningContent").slideToggle(500);
        $(".upExpandAfrekening").toggle();
        $(".downExpandAfrekening").toggle();
    });
});
