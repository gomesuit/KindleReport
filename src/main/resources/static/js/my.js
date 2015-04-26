$(function(){
	var itemCount = 0;
	var colnum = 4;
	var order = 1;
	pageLoad();
	$(".mybtn").click(function(){
		colnum = $(this).attr("value");
		$("#mainrow").empty();
		itemCount = 0;
		pageLoad();
	});
	$(".myorder").click(function(){
		order = $(this).attr("value");
		$("#mainrow").empty();
		itemCount = 0;
		pageLoad();
	});
	$(window).on('load resize', function(){
		$('.item').tile(colnum);
	});
	function pageLoad(){
		var request = $.ajax({
			type: "GET",
			url: '/json',
			cache: false,
			datatype: "json",
			data:{
				"itemCount" : itemCount,
				"order" : order
			},
			timeout: 3000
		});
		request.done(function(data){
			//alert("通信成功");
			var imgClassName = "img" + itemCount;
			var tileClassName = "tile" + itemCount;
			var bootColClass = "col-md-" + (12 / colnum);
			var row = $("#mainrow");
			var str = "";
			for(var i in data){
				if(data[i].largeImage == ""){
					data[i].largeImage = "img/noimage.png"
				}
				str += '<div class="' + bootColClass + ' col-full-height"><div class="' + tileClassName + ' item"><div class="content">'
					+ '<p>' + data[i].title + '</p>'
					+ '<a href="' + data[i].detailPageURL + '" class="thumbnail"><img class="' + imgClassName + '" src="' + data[i].largeImage + '" /></a>'
					+ '<p>' + data[i].releaseDate + '</p>'
					//+ '<p><a class="btn btn-default" href="#" role="button">View details »</a></p>'
					+ '</div></div></div>';
			}
			row.append(str);
			itemCount = itemCount + data.length;
		    var allImage = $("." + imgClassName);
		    var allImageCount = allImage.length;
		    var completeImageCount = 0;
		    for(var i = 0; i < allImageCount; i++){
		      $(allImage[i]).bind("load", function(){
		        completeImageCount ++;
		        if (allImageCount == completeImageCount){
				  $('.' + tileClassName).tile(colnum);
		        }
		      });
		    }
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
            //alert('一番下だよ');
            pageLoad();
        }
    });
});