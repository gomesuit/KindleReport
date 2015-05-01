/**文字カットプラグイン
 *option
    -cutHei カットする高さ
    -afterText 末尾に追加する文字
    -blank リンクのtarget属性
    -デフォルトは高さ60、末尾に追加する文字「...」
    -data-textCut-link　リンクをつける

 *実行例
    $(".text").textCut({
        cutHei : 60,
    });

    <p class="text">
      i miyakawa with Nigel Olsson
      i miyakawa with Nigel Olsson
    </p>
*/
$.fn.textCut = function(op){
    op = $.extend({
        cutHei : 60,
        afterText : "...",
        blank : false
    },op);

    return this.each(function(){
        var $this = $(this);

        var link = $this.attr("data-textCut-link"), //リンク先を取得
            blank,
            clone = $this.clone(); //クローンの作成

        blank = op.blank == true ? "target='_blank'" : "";

        //指定された高さよりも高かった場合
        if(clone.context.clientHeight > op.cutHei){
            var textLen = clone.context.innerText.length,
                textLen2,
                i = 0;

            //指定された高さより低くなるまで文字をカットする
            for(i = 0;i < textLen;i++){
                textLen2 = clone.context.innerText.length;
                clone.context.innerText = clone.context.innerText.substr(0,textLen2-1);

                if(clone.context.clientHeight < op.cutHei){
                    //カットした文字からさらに末尾に追加する分の文字数をカット
                    clone.context.innerText = clone.context.innerText.substr(0,textLen2-op.afterText.length);
                    break;
                }
            }

            if(link != undefined){
                op.afterText = "<a href="+link+" class='textCut-link'" +blank+">" + op.afterText + "</a>";
            }

            $this.html(clone.context.innerText+op.afterText);
        }
    });
}