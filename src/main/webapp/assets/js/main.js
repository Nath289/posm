jQuery(document).ready(function ($) {

    function loadHandler(event) {
        console.log("load:", $(this).attr("href"));
        event.preventDefault();
        $("#content").load($(this).attr("href"), function() {
            console.log('callback');
            $("a.dynamic", "#content").click(loadHandler);
        });
    }

    $("a.dynamic").click(loadHandler);

});