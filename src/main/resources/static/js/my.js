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
	$(document).on("click", ".item a", function(){
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
		if(kindledata.largeImage == ""){
			this.imageUrl = "img/noimage.png";
		}else{
			this.imageUrl = kindledata.largeImage;
		}
		var params = clone(params);

		this.getItemDom = function(){
			var classString = 'item ' + params.bootColClass;
			var attrList = [getAttrString("class", classString)];
			var text = this.getItemDomContent();
			return getHtmlDom("div", attrList, text);
		}

		this.getItemDomContent = function(){
			var classString = params.tileClassName + ' content';
			var attrList = [getAttrString("class", classString)];
			var text = this.getItemDomThumbnail() + this.getItemDomTile() + this.getItemDomReleaseDate();
			return getHtmlDom("div", attrList, text);
		}

		this.getItemDomThumbnail = function(){
			var valueString = "/items/" + this.asin;
			var classString = "thumbnail " + params.thumbnailClassName;
			var attrList = [getAttrString("value", valueString), getAttrString("class", classString)];
			var text = this.getItemDomImg();
			return getHtmlDom("a", attrList, text);
		}

		this.getItemDomImg = function(){
			var attrList = [getAttrString("class", params.imgClassName), getAttrString("src", this.imageUrl)];
			return getHtmlDom("img", attrList);
		}

		this.getItemDomTile = function(){
			var attrList = [getAttrString("class", params.titleClassName)];
			var text = '『' + this.title + '』';
			return getHtmlDom("p", attrList, text);
		}

		this.getItemDomReleaseDate = function(){
			return getHtmlDom("p", null, this.releaseDate);
		}
	}
	var PageDom = function(kindlesdata, params){
		this.bootColClass = "col-md-" + (12 / colnum);
		this.tileClassName = "tile" + position;
		this.thumbnailClassName = "thumbnail" + position;
		this.imgClassName = "img" + position;
		this.titleClassName = "title" + position;

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
			var dom = "";
			dom += '<div id="' + this.pageId + '" />';
			dom += '<div class="row">';
			dom += this.pageDom.getPageDom();
			dom += '</div>';
			dom += '<div class="row"><p　class="text-center">' + "page " + position + '</p></div>';
			
			return dom;
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
	function pageLoad(obj) { 
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
			
			obj.data('loading', false);
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
					history.pushState([null, $("#pageState").html()], "", "?page="
							+ viewPosition);
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
			pageLoad(obj);
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
});