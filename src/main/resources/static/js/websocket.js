$(function() {
	url = "ws://" + location.host + "/echo";
    ws = new WebSocket(url);
    ws.onopen = function(){
    };
    ws.onclose = function(){
    };
    ws.onmessage = function(json){
    	var data = $.parseJSON(json.data);
		var dateTime = unixTimeToString(data.dateTime);
        noticeBalloon(data);
        $("#result").prepend(makeComment(data.id, dateTime, data.message));
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
	function makeComment(id, dateTime, message) {
		var comment = '';
		comment += '<div class="detailComment">';
		comment += '<div>';
		comment += dateTime;
		comment += '</div>';
//		comment += '<div>';
//		comment += id;
//		comment += '</div>';
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
		var request = $.ajax({
		    headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
			type : "POST",
			url : "/api/comment/register",
			datatype : "json",
			data : JSON.stringify({
				"asin" : $("#asin").attr("value"),
				"content" : message
			}),
			timeout : 3000
		});
		request.done(function(data) {
			// alert("通信成功");
			console.log(data);
			var wsData = JSON.stringify({
				asin: $("#asin").attr("value"),
				id: data
			});
	        ws.send(wsData);
	        $("#message").val("");
		});
		request.fail(function() {
			// alert("通信エラー");
		});
		request.always(function() {
			// alert("通信完了");
		});
		
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