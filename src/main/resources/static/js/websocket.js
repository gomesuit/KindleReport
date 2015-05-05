$(function() {
	var url = "ws://" + location.host + "/echo";
    var ws = new WebSocket(url);
    ws.onopen = function(){
    };
    ws.onclose = function(){
    };
    ws.onmessage = function(json){
    	var data = $.parseJSON(json.data);
        noticeBalloon(data);
        $("#result").append(makeComment(data.message));
    };
	function noticeBalloon(data) {
	    $.amaran({
	        'theme'     :'user blue',
	        'content'   :{
	            img:data.imgUrl,
	            user:data.title,
	            message:data.message
	        },
	        'position'  :'bottom right'
	    });
	}
	function makeComment(str) {
		return '<p class="detailComment">' + str + '</p>'
	}
    ws.onerror = function(event){
        alert("接続に失敗しました。");
    };
	$(document).on("submit", "#form", function(){
		var message = $("#message").val();
		var imgUrl = $("img").attr("src");
		var title = $("h1").text();
		var data = JSON.stringify({
			message: message,
			imgUrl: imgUrl,
			title: title,
		});
        ws.send(data);
        $("#message").val("");
        return false;
    });
});