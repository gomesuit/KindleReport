$(function() {
	function addTagIdList(tagid){
		tagIdList[tagIdList.length] = tagid;
	}
	function deleteTagIdList(tagid){
		var newTagIdList;
		for(i = 0; i < tagIdList.length; i++){	
			if(tagIdList[i] != tagid){
				newTagIdList[newTagIdList.length] = tagIdList[i];
			}
		}
		tagIdList = newTagIdList;
	}
	$(document).on("click", ".tile a", function(){
		var url = $(this).attr("value");
		history.replaceState([$(window).scrollTop(), $("#pageState").html()], null, null);
		itemPageLoad(url);
	});
	function itemPageLoad(url) {
		var request = $.ajax({
			type : "GET",
			url : url,
			cache : false,
			datatype : "html",
			data : {
				"ajaxflg" : 1
			},
			timeout : 3000
		});
		request.done(function(data) {
			//alert("通信成功");
			//console.log(data);
			$("#pageState").empty();
			$("#pageState").html(data);
			commentHeightAdjust();
			history.pushState([null, $("#pageState").html()], "", url);
			$(window).scrollTop(0);
			$("#form").validationEngine('attach', {
			    promptPosition:"bottomLeft"
			});
			$("#tagform").validationEngine('attach', {
			    promptPosition:"bottomLeft"
			});
		});
		request.fail(function() {
			// alert("通信エラー");
		});
		request.always(function() {
			// alert("通信完了");
		});
		
	}
	// $(window).on('load resize', function(){
	// $('.item').tile(colnum);
	// });
//	function pageLoad2() {
//		// var url = "/json" + location.search;
//		var request = $.ajax({
//			type : "GET",
//			url : "/json",
//			cache : false,
//			datatype : "json",
//			data : {
//				"order" : order,
//				"page" : position
//			},
//			timeout : 3000
//		});
//		request.done(function(data) {
//			// alert("通信成功");
//			var imgClassName = "img" + position;
//			var tileClassName = "tile" + position;
//			var bootColClass = "col-md-" + (12 / colnum);
//			var mainrow = $("#mainrow");
//			var pageId = "page" + position;
//			mainrow.prepend('<div id="' + pageId + '" />');
//			var pagerow = $("#" + pageId);
//			var str = "";
//			for ( var i in data) {
//				if (data[i].largeImage == "") {
//					data[i].largeImage = "img/noimage.png"
//				}
//				str += '<div class="' + bootColClass
//						+ ' col-full-height"><div class="' + tileClassName
//						+ ' item"><div class="content">' + '<p>'
//						+ data[i].title + '</p>' + '<a href="/items/'
//						+ data[i].asin + '" class="thumbnail"><img class="'
//						+ imgClassName + '" src="' + data[i].largeImage
//						+ '" /></a>' + '<p>' + data[i].releaseDate + '</p>'
//						// + '<p><a class="btn btn-default" href="#"
//						// role="button">View details »</a></p>'
//						+ '</div></div></div>';
//			}
//			str += '<div class="row"><h1>' + "page " + position + '</h1></div>'
//			pagerow.append(str);
//			var allImage = $("." + imgClassName);
//			var allImageCount = allImage.length;
//			var completeImageCount = 0;
//			for (var i = 0; i < allImageCount; i++) {
//				$(allImage[i]).bind("load", function() {
//					completeImageCount++;
//					if (allImageCount == completeImageCount) {
//						$('.' + tileClassName).tile(colnum);
//					}
//				});
//			}
//		});
//		request.fail(function() {
//			// alert("通信エラー");
//		});
//		request.always(function() {
//			// alert("通信完了");
//		});
//	}
	// $(window).scroll(function(ev) {
	// var $window = $(ev.currentTarget),
	// height = $window.height(),
	// scrollTop = $window.scrollTop(),
	// documentHeight = $(document).height();
	// if (documentHeight === height + scrollTop) {
	// //alert('一番下だよ');
	// pageLoad();
	// }
	// });
	function getPageTop(page) {
		var PageId = "page" + (page);
		var Pagerow = $("#" + PageId);
		if (Pagerow.get(0) == null) {
			return null;
		}
		return Pagerow.position().top;
	}
	function getPageBottom(page) {
		var PageId = "page" + (page);
		var Pagerow = $("#" + PageId);
		if (Pagerow.get(0) == null) {
			return null;
		}
		return Pagerow.position().top + Pagerow.height();
	}
	// 次のページを読み込む
	$(window).bottom({
		proximity : 0
	});
	// //前のページを読み込む
	// var mousewheelevent = 'onwheel' in document ? 'wheel' : 'onmousewheel' in
	// document ? 'mousewheel' : 'DOMMouseScroll';
	// $(document).on(mousewheelevent,function(e){
	// console.log("position: " + position);
	// console.log("viewPosition: " + viewPosition);
	// if(viewPosition <= 1){
	// return null;
	// }
	// if(pageExistsCheck(viewPosition - 1)){
	// return null;
	// }
	// if($(window).scrollTop() == 0 && position > 1){
	// var delta = e.originalEvent.deltaY ? -(e.originalEvent.deltaY) :
	// e.originalEvent.wheelDelta ? e.originalEvent.wheelDelta :
	// -(e.originalEvent.detail);
	// if (delta > 0){
	// //alert('一番上で上にスクロールされたよ');
	// viewPosition--;
	// position = viewPosition;
	// pageLoad2();
	// //$(window).scrollTop(getPageTop(position + 2 ));
	// $(window).scrollTop(3000);
	// console.log(getPageTop(position + 2));
	// console.log(getPageTop(position + 2));
	// console.log(getPageTop(position + 2));
	// console.log(getPageTop(position + 2));
	// console.log(getPageTop(position + 2));
	// console.log(getPageTop(position + 2));
	// //$(window).scrollTop(1000);
	// pageDelete(position + 2);
	// history.pushState("", "", "?page=" + viewPosition);
	// }
	// }
	// });
	// $(window).on('popstate', function(jqevent) {
	// var state = jqevent.originalEvent.state;
	// console.log(state);
	// $("#mainrow").empty();
	// //$("#mainrow").append(state);
	// });
//	 $.pjax({
//	 area : '#pjaxContent',
//	 link : 'a:not([target])'
//	 });
	function pageDelete(page) {
		var pageId = "page" + page;
		$("#" + pageId).remove();
	}
	$(window).on("popstate", function(_event) {
		if (!_event.originalEvent.state) return;
		var state = _event.originalEvent.state;
		//console.log("_event", _event);
		//console.log("state", state);
		//console.log($(window).scrollTop());
		$("#pageState").html(state[1]);
		if(state[0] != null){
			$(window).scrollTop(state[0]);
			setTimeout(function(){
				$(window).scrollTop(state[0]);
			}, 1);
		}
		//console.log($(window).scrollTop());
		
		init();
	});
	//==================================websocket==================================
	
	var url = "ws://" + location.host + "/echo";
	commentHeightAdjust();
	function commentHeightAdjust(){
		$("#detailImg").on("load", function(){
			var height = $("#detail-img-grid").height() - ($("#detail-p-grid").outerHeight(true) + $("#detail-input-grid").outerHeight(true));
			$('#detail-scroll-grid').css("height", height);
		});
	}
    ws = new WebSocket(url);
    ws.onopen = function(){
    };
    ws.onclose = function(){
    };
    ws.onmessage = function(json){
    	var data = $.parseJSON(json.data);
    	if(data.sw == 1){
    		var dateTime = unixTimeToString(data.dateTime);
            noticeBalloon(data);
            if(data.asin == $("#asin").attr("value")){
                $("#detail-scroll-grid").prepend(makeComment(data.id, dateTime, data.message));
            }
            $("#detail-scroll-grid").scrollTop(0);
    	}else if(data.sw == 2){
            if(data.asin == $("#asin").attr("value")){
        		$("#tagList").append(makeTag(data.message, data.id, isOpenTagForm()));
            }
    	}else if(data.sw == 3){
    		if(data.asin == $("#asin").attr("value")){
    			$("#detailTag" + data.id).remove();
    		}
    	}
    };
	function noticeBalloon(data) {
	    $.amaran({
	        'theme'     :'kindle no',
	        'content'   :{
	        	asin: data.asin,
	            img: data.imgUrl,
	            title: escapeHtml(data.title),
	            message: escapeHtml(data.message)
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
		comment += escapeHtml(message);
		comment += '</div>';
		comment += '</div>';
		return comment;
	}
	function makeTag(tagName, id, isOpen) {
		if(isOpen){
			display = "inline";
		}else{
			display = "none";
		}
		var tag = '';
		tag += '<a href="';
		tag += '/list?tagId=' + id;
		tag += '" class="detailTag" id="';
		tag += "detailTag" + id;
		tag += '">';
		tag += '<span class="detailLabel label label-warning glyphicon glyphicon-tag">';
		tag += escapeHtml(tagName);
		tag += '</span>';
		tag += '<button class="tagDelete btn btn-default btn-xs" type="button" th:value="';
		tag += id;
		tag += '" style="display: ';
		tag += display;
		tag += ';">';
		tag += '<span class="glyphicon glyphicon-remove" aria-hidden="false" />';
		tag += '</button>';
		tag += '</a>';
		return tag;
	}
	var escapeHtml = (function (String) {
	  var escapeMap = {
	    '&': '&amp;',
	    "'": '&#x27;',
	    '`': '&#x60;',
	    '"': '&quot;',
	    '<': '&lt;',
	    '>': '&gt;'
	  };
	  var escapeReg = '[';
	  var reg;
	  for (var p in escapeMap) {
	    if (escapeMap.hasOwnProperty(p)) {
	      escapeReg += p;
	    }
	  }
	  escapeReg += ']';
	  reg = new RegExp(escapeReg, 'g');
	  return function escapeHtml (str) {
	    str = (str === null || str === undefined) ? '' : '' + str;
	    return str.replace(reg, function (match) {
	      return escapeMap[match];
	    });
	  };
	}(String));
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
			//console.log(data);
			var wsData = JSON.stringify({
				sw : 1,
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
	$(document).on("submit", "#tagform", function(){
		var request = $.ajax({
		    headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
			type : "POST",
			url : "/api/tag/register",
			datatype : "json",
			data : JSON.stringify({
				asin : $("#asin").attr("value"),
				name : $("#tagInput").val()
			}),
			timeout : 3000
		});
		request.done(function(data) {
			// alert("通信成功");
			//console.log(data);
			var wsData = JSON.stringify({
				sw : 2,
				asin: $("#asin").attr("value"),
				id: data
			});
	        ws.send(wsData);
	        $("#tagInput").val("");
		});
		request.fail(function() {
			// alert("通信エラー");
		});
		request.always(function() {
			// alert("通信完了");
		});
		
        return false;
    });
	$("#tagform").validationEngine('attach', {
	    promptPosition:"bottomLeft"
	});

	$(document).on("click", ".tagDelete", function(){
		tagId = $(this).attr("value");
		$(this).parent().remove();
		var request = $.ajax({
		    headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
			type : "POST",
			url : "/api/tag/delete",
			datatype : "json",
			data : JSON.stringify({
				asin : $("#asin").attr("value"),
				tagId : tagId
			}),
			timeout : 3000
		});
		request.done(function(data) {
			// alert("通信成功");
			//console.log(data);
			var wsData = JSON.stringify({
				sw : 3,
				asin: $("#asin").attr("value"),
				id: data
			});
	        ws.send(wsData);
	        $("#tagInput").val("");
		});
		request.fail(function() {
			// alert("通信エラー");
		});
		request.always(function() {
			// alert("通信完了");
		});
		
        return false;
	});
	$(document).on("click", "#tagRegist", function(){
		targetInput = $("#tag-input-group");
		targetButton = $(".tagDelete");
		if(!isOpenTagForm()){
			targetInput.css("display", "block");
			targetButton.css("display", "inline");
		}else{
			targetInput.css("display", "none");
			targetButton.css("display", "none");
		}
	});
	function isOpenTagForm() {
		targetInput = $("#tag-input-group");
		if(targetInput.css("display") == "none"){
			return false;
		}else{
			return true;
		}
	}
//	$(document).on("click", ".amaran", function(){
//		alert("aaaa");
//		alert($(this));
//	});
	//==================================datelist==================================
	//$('.sidebar').containedStickyScroll();
});