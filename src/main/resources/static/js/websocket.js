$(function() {
	var url = "ws://" + location.host + "/echo";
    var ws = new WebSocket(url);
    ws.onopen = function(){
    };
    ws.onclose = function(){
    };
    ws.onmessage = function(json){
    	var data = $.parseJSON(json.data);
		var dateTime = unixTimeToString(data.dateTime);
        noticeBalloon(data);
        $("#result").prepend(makeComment(dateTime, data.message));
    };
	function noticeBalloon(data) {
	    $.amaran({
	        'theme'     :'kindle no',
	        'content'   :{
	        	asin: data.asin,
	            img: data.imgUrl,
	            title: data.title,
	            message: data.message
	        },
	        'position'  :'bottom right'
	    });
	}
	function makeComment(dateTime, message) {
		var comment = '';
		comment += '<div class="detailComment">';
		comment += '<div>';
		comment += dateTime;
		comment += '</div>';
		comment += '<div>';
		comment += message;
		comment += '</div>';
		comment += '</div>';
		return comment;
	}
	function unixTimeToString(dateTime){
		var format = 'YYYY/MM/DD HH:mm';
		var string = moment().format(format, new Date(dateTime));
		return string;
	}
    ws.onerror = function(event){
        alert("接続に失敗しました。");
    };
	$(document).on("submit", "#form", function(){
		var message = $("#message").val();
		var imgUrl = $("img").attr("src");
		var title = $("h1").text();
		var data = JSON.stringify({
			asin: $("#asin").attr("value"),
			message: message
		});
        ws.send(data);
        $("#message").val("");
        return false;
    });
	$("#form").validationEngine('attach', {
	    promptPosition:"bottomLeft"
	});
//	$(document).on("click", ".amaran", function(){
//		alert("aaaa");
//		alert($(this));
//	});
});