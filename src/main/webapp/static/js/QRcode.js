$(function(){
    $("#submit").click(function(){
        $.ajax({ url: ctx + "WX/pay",success: function(data){
            console.log(data);
            var str = "<span><img src=\"data:image/gif;base64," + data + "\"/></span><br />" ;
            //$("#insert").html(str);
            $("#view").html(str);
        }});
    });
});