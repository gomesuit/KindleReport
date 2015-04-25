$(function(){
	itemCount = 0;
	pageLoad();
	$(".btn").click(function(){
		//$(".item").attr("class", "item col-md-3");
	});
	function pageLoad(){
		var request = $.ajax({
			type: "GET",
			url: '/json',
			cache: false,
			datatype: "json",
			data:{
				"itemCount" : itemCount
			},
			timeout: 3000
		});
		request.done(function(data){
			//alert("通信成功");
			var row = $(".row");
			var str = "";
			for(var i in data){
				str += '<div class="item col-md-4">'
					+ '<h2>' + data[i].title + '</h2>'
					+ '<img src="' + data[i].largeImage + '" />'
					+ '<p>' + data[i].releaseDate + '</p>'
					+ '<p><a class="btn btn-default" href="#" role="button">View details »</a></p>'
					+ '</div>';
			}
			row.append(str);
			itemCount = itemCount + data.length;
		});
		request.fail(function(){
			//alert("通信エラー");
		});
		request.always(function(){
			//alert("通信完了");
		});
	}
    $(window).scroll(function(ev) {
        var $window = $(ev.currentTarget),
            height = $window.height(),
            scrollTop = $window.scrollTop(),
            documentHeight = $(document).height();
        if (documentHeight === height + scrollTop) {
            // 一番下だよ
            //alert('一番下だよ');
            pageLoad();
        }
    });
});