$(function() {
    var ws = new WebSocket("ws://localhost/echo");
    ws.onopen = function(){
    };
    ws.onclose = function(){
    };
    ws.onmessage = function(message){
        $("#result").append(message.data).append("<br>");
    };
    ws.onerror = function(event){
        alert("接続に失敗しました。");
    };
	$(document).on("submit", "#form", function(){
        ws.send($("#message").val());
        $("#message").val("")
        return false;
    });
});