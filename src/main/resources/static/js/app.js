//进入就执行的方法
getTreeView();

function getTreeView() {
    $.getJSON("/categorys", function (data) {
        $('#tree').treeview({
                                data: JSON.stringify(data),
                                collapseIcon: "glyphicon glyphicon-folder-open",
                                expandIcon: "glyphicon glyphicon-folder-close",
                                color: "#2c3f51",
                                selectedBackColor: "#338FFF",
                                onhoverColor: "#F1FBE8",
                                onNodeSelected: function (event, data) {
                                    getArticle(data);
                                }
                            });
    });
}
//刷新文档
$("#refresh-file").click(function () {
    var nodes = $('#tree').treeview('getSelected', 1);
    if (nodes[0].path != ""){
        getArticle(nodes[0]);
    }
});
//刷新目录
$("#categoryRefresh").click(function () {
    getTreeView();
});
//生成文档
function getArticle(data) {
    $.ajax({
               type: "POST",
               url: "/article",
               data: {path: data.path},
               beforeSend:function () {
                   $(".preview-panel").empty(1000);
               },
               success: function (result) {
                   //转换为markdown
                   md2Html(result.content, $(".preview-panel"), function(html) {
                        $('pre').addClass("hljs-dark");
                   });

                   //代码高亮
                   $('pre code').each(function(i, block) {
                       hljs.highlightBlock(block);
                   });
                   //生成文档回到顶部
                   $('body,html').animate({scrollTop:0},1000);
               }
           });
}
//回到顶部
$("#top").click(function(){
    $('body,html').animate({scrollTop:0},1000);
    return false;
});