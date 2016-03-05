$(function () {
    $(".kampioenschap").click(function () {
        var id = $(this).data("id");
        var teToggelenId = "#toer-" + id;
        $(teToggelenId).toggle(500);

        // Expand arrows
        var downId = "#down-" + id;
        var upId = "#up-" + id;

        $(downId).toggle(0);
        $(upId).toggle(0);
    });
});
