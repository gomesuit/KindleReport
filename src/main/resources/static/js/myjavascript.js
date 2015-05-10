$(function() {
	var itemCount;
	var colnum;
	var order;
	var position;
	var viewPosition;
	init();
	pageLoad();
	function getParam(key) {
		if (location.search == "") {
			return null;
		}
		var url = location.href;
		parameters = url.split("?");
		params = parameters[1].split("&");
		var paramsArray = [];
		for (i = 0; i < params.length; i++) {
			neet = params[i].split("=");
			paramsArray.push(neet[0]);
			paramsArray[neet[0]] = neet[1];
		}
		var value = paramsArray[key];
		return value;
	}
	function init() {
		colnum = $.cookie("colnum");
		if (colnum == null) {
			colnum = 4;
			$.cookie("colnum", colnum);
		}
		selectColRadio(colnum);
		
		order = $.cookie("order");
		if (order == null) {
			order = 1;
			$.cookie("order", order);
		}
		
		position = getParam("page");
		if (position == null) {
			position = 1;
		}
		viewPosition = position;
	}
	function selectColRadio(num) {
		$(".active").removeClass("active");
		var radioName = "#colrdo" + num;
		$(radioName).addClass("active");
	}
	$(document).on("click", ".mybtn", function(){
		colnum = $(this).attr("value");
		$.cookie("colnum", colnum);
		$("#mainrow").empty();
		init()
		pageLoad();
	});
	$(document).on("click", ".myorder", function(){
		order = $(this).attr("value");
		$.cookie("order", order);
		$("#mainrow").empty();
		init()
		pageLoad();
	});
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
		});
		request.fail(function() {
			// alert("通信エラー");
		});
		request.always(function() {
			// alert("通信完了");
		});
		
	}
	function clone(src){
	    var dst = {}
	    for(var k in src){
	        dst[k] = src[k];
	    }
	    return dst;
	}
	// $(window).on('load resize', function(){
	// $('.item').tile(colnum);
	// });
	var ItemDom = function(kindledata, params){
		this.asin = kindledata.asin;
		this.title = kindledata.title;
		this.releaseDate = kindledata.releaseDate;
		this.imageUrl = kindledata.largeImage;
		if(this.imageUrl == ""){
			this.imageUrl = "/img/noimage.png";
		}
		var params = clone(params);

		this.getItemDom = function(){
			var classString = params.bootColClass;
			var attrList = [getAttrString("class", classString)];
			var text = this.getItemDomContent();
			return getHtmlDom("div", attrList, text);
		}

		this.getItemDomContent = function(){
			var classString = "tile " + params.tileClassName;
			var attrList = [getAttrString("class", classString)];
			var text = this.getItemDomThumbnail() + this.getItemDomTile() + this.getItemDomReleaseDate();
			return getHtmlDom("div", attrList, text);
		}

		this.getItemDomThumbnail = function(){
			var valueString = "/items/" + this.asin;
			var classString = "mythumbnail " + params.thumbnailClassName;
			var attrList = [getAttrString("value", valueString), getAttrString("class", classString)];
			var text = this.getItemDomImg();
			return getHtmlDom("a", attrList, text);
		}

		this.getItemDomImg = function(){
			var attrList = [getAttrString("class", params.imgClassName), getAttrString("src", this.imageUrl)];
			return getHtmlDom("img", attrList);
		}

		this.getItemDomTile = function(){
			var classString = "title " + params.titleClassName;
			var attrList = [getAttrString("class", classString)];
			var text = '『' + this.title + '』';
			return getHtmlDom("p", attrList, text);
		}

		this.getItemDomReleaseDate = function(){
			var classString = "releaseDate";
			var attrList = [getAttrString("class", classString)];
			return getHtmlDom("p", attrList, this.releaseDate);
		}
	}
	var PageDom = function(kindlesdata, params){
		this.getPageDom = function(){
			var classString = "row";
			var attrList = [getAttrString("class", classString)];
			var text = "";
			for (var i in kindlesdata) {
				var itemDom = new ItemDom(kindlesdata[i], params);
				text += itemDom.getItemDom();
			}
			return getHtmlDom("div", attrList, text);
		}
	}
	var PageRow = function(position, colnum, kindlesdata){
		this.params = {
			bootColClass:"col-md-" + (12 / colnum),
			tileClassName:"tile" + position,
			thumbnailClassName:"thumbnail" + position,
			imgClassName:"img" + position,
			titleClassName:"title" + position
		};
		this.pageId = "page" + position;
		this.pageDom = new PageDom(kindlesdata, this.params);
		
		this.getPageRow = function(){
			var attrList = [getAttrString("id", this.pageId)];
			var text = getHtmlDom("div", [getAttrString("class", "row")], this.pageDom.getPageDom()) + this.getPageNumber();
			return getHtmlDom("div", attrList, text);
		}

		this.getPageNumber = function(){
			var classString = "row";
			var attrList = [getAttrString("class", classString)];
			var text = getHtmlDom("p", [getAttrString("class", "pagenumber text-center")], "-page" + position + "-");
			return getHtmlDom("div", attrList, text);
		}
		
		this.tileAdjust = function(){
			var allImage = $("." + this.params.imgClassName);
			var allImageCount = allImage.length;
			var completeImageCount = 0;
			for (var i = 0; i < allImageCount; i++) {
				var that = this;
				$(allImage[i]).on("load", function(){
					completeImageCount++;
					if (allImageCount == completeImageCount) {
						$('.' + that.params.thumbnailClassName).tile(colnum);
						$('.' + that.params.titleClassName).tile(colnum);
						$('.' + that.params.tileClassName).tile(colnum);
					}
				});
			}
		}
	}
	function getAttrString(attrName, className){
		return attrName + '="' + className + '"';
	}
	function getHtmlDom(htmlName, attrList, text){
		var dom = '';
		dom += '<';
		dom += htmlName;
		if(attrList != null){
		    for (var i = 0; i < attrList.length; i++) {
				dom += ' ';
				dom += attrList[i];
		    }
		}
	    if(text != null){
			dom += ' >';
			dom += text;
			dom += '</';
			dom += htmlName;
			dom += '>';
	    }else{
			dom += ' />';
	    }
		return dom;
	}
	function pageLoad() { 
		var request = $.ajax({
			type : "GET",
			url : "/api/tile",
			cache : false,
			datatype : "json",
			data : {
				"order" : order,
				"page" : position
			},
			timeout : 3000
		});
		request.done(function(data) {
			var pageRow = new PageRow(position, colnum, data);
			var mainrow = $("#mainrow");
			mainrow.append(pageRow.getPageRow());
			pageRow.tileAdjust();
			
			$(window).data('loading', false);
		});
		request.fail(function() {
			// alert("通信エラー");
		});
		request.always(function() {
			// alert("通信完了");
		});
	}
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

	//ページ送り（下スクロール）
	$(window).scroll(
			function(ev) {
				var $window = $(ev.currentTarget);
				var prevPageId = "page" + (viewPosition - 1);
				var prevPagerow = $("#" + prevPageId);
				if (prevPagerow.get(0) == null) {
					return null;
				}
				var prevPageBottom = prevPagerow.position().top
						+ prevPagerow.height();
				var scrollTop = $window.scrollTop();
				var scrollBottom = scrollTop + $window.height();

				if (scrollBottom < prevPageBottom) {
					viewPosition--;
					history.pushState(null, "", "?page=" + viewPosition);
				}
			});
	//ページ戻り（上スクロール）
	$(window).scroll(
			function(ev) {
				var $window = $(ev.currentTarget);
				var nextPageId = "page" + (viewPosition);
				var nextPagerow = $("#" + nextPageId);
				if (nextPagerow.get(0) == null) {
					return null;
				}
				var nextPageBottom = nextPagerow.position().top
						+ nextPagerow.height();
				var scrollTop = $window.scrollTop();
				var scrollBottom = scrollTop + $window.height();

				if (scrollTop > nextPageBottom) {
					viewPosition++;
					if (position < viewPosition) {
						position = viewPosition;
					}
					//console.log($("#pageState").html());
					history.pushState([null, $("#pageState").html()], "", "?page=" + viewPosition);
				}
			});
	function pageExistsCheck(page) {
		var PageId = "page" + (page);
		var Pagerow = $("#" + PageId);
		if (Pagerow.get(0) == null) {
			return false;
		}
		return true;
	}
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
	$(window).on('bottom', function() {
		if (pageExistsCheck(position + 1)) {
			position++;
			return null;
		}
		var obj = $(this);
		if (!obj.data('loading')) {
			obj.data('loading', true);
			position++;
			pageLoad();
			//pageDelete(position - 2)
			// history.pushState("", "", "?page=" + position);
		}
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
		}
		//console.log($(window).scrollTop());
		init();
	});
	
	//==================================websocket==================================
	
	var url = "ws://" + location.host + "/echo";
	commentHeightAdjust();
	function commentHeightAdjust(){
		var height = $("#detail-img-grid").height() - ($("#detail-p-grid").outerHeight(true) + $("#detail-input-grid").outerHeight(true));
		$('#detail-scroll-grid').css("height", height);
	}
    ws = new WebSocket(url);
    ws.onopen = function(){
    };
    ws.onclose = function(){
    };
    ws.onmessage = function(json){
    	var data = $.parseJSON(json.data);
		var dateTime = unixTimeToString(data.dateTime);
        noticeBalloon(data);
        if(data.asin == $("#asin").attr("value")){
            $("#detail-scroll-grid").prepend(makeComment(data.id, dateTime, data.message));
        }
        $("#detail-scroll-grid").scrollTop(0);
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
			//console.log(data);
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