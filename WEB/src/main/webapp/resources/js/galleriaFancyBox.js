var pic_real_width, pic_real_height;

$(function() {
   fancyUp();
});

function fancyUp() {
    $(".fancybox").css('display', 'inline')
        .css('width', 600).css('height', 400);

    $(".fancybox").fancybox({
        afterClose : function() {
            $(".fancybox").css('display', 'inline')
                .css('width', 600).css('height', 400);
        },
        fitToView : true,
        autoSize : true,
        aspectRatio : true,
        beforeShow : function() {
            var alt = this.element.attr('alt');

            this.inner.find('img').attr('alt', alt);

            this.title = alt;
        }
    });

    $('.fotoLink').click(function() {
        var img = $(this).children().first();
        $("<img/>")
            .attr("src", $(img).attr("src"))
            .load(function() {
                pic_real_width = this.width;
                pic_real_height = this.height;
                $(img).css('width', pic_real_width);
                $(img).css('height', pic_real_height);
            });
    });
}

function setId(button) {
    var labelId = $(button).siblings("label").first();
    var id = $(labelId).html();
    $("#frm\\:hdn_geselecteerdeFotoId").val(id);
}

