$(function () {
    $(".gegevensHeader").click(function () {
        $(".gegevensContent").slideToggle(500);
        $(".upExpandGegevens").toggle();
        $(".downExpandGegevens").toggle();
    });

    $(".cateringHeader").click(function () {
        $(".cateringContent").slideToggle(500);
        $(".upExpandCatering").toggle();
        $(".downExpandCatering").toggle();
    });

    $(".inschrijvingenHeader").click(function () {
        $(".inschrijvingenContent").slideToggle(500);
        $(".upExpandInschrijvingen").toggle();
        $(".downExpandInschrijvingen").toggle();
    });
});
