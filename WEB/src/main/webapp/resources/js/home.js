$(function () {
    $(".post").click(function () {
        $(".postForm").slideToggle(500);
        $(".upExpand").toggle(0);
        $(".downExpand").toggle(0);
    });
});
