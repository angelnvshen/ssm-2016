$(function(){
    $("#submit").click(function(){
        $.ajax({
            url: ctx + "WX/pay",
            data:{
                body:"a pen",
                total_fee : 1,
                Out_trade_no : "113384"
            },
            success: function(data){
            console.log(data);
            var str = "<span><img src=\"data:image/gif;base64," + data.message + "\"/></span><br />" ;
            //$("#insert").html(str);
            $("#view").html(str);
        }});
    });
});