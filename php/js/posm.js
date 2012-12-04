$(document).ready(function(){
    $(".role-section").not(".role-1").hide();
    $("#roleMenu").change(function(){
        var role = $(this).val();
        $(".role-section").not("."+role).hide();
        $("."+role).show();
    })
})