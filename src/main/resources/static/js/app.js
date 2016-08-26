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
                                    $.ajax({
                                               type: "POST",
                                               url: "/article",
                                               data: {path: data.path},
                                               success: function (result) {
                                                   var parser = new HyperDown;
                                                   var html = parser.makeHtml(result.content);
                                                   //转换markdown
                                                   $(".preview-panel").html(html);
                                                   //代码高亮
                                                   $('pre code').each(function(i, block) {
                                                       hljs.highlightBlock(block);
                                                   });
                                               },
                                               complete: function () {
                                                   $(".category").empty();
                                                   setTimeout(function () {
                                                       //生成目录
                                                       $("h2,h3,h4,h5,h6").each(function(i,item){
                                                           var tag = $(item).get(0).localName;
                                                           $(item).attr("id","wow"+i);
                                                           $(".category").append('<a class="new'+tag+'" href="#wow'+i+'">'+$(this).text()+'</a></br>');
                                                       });
                                                   },1000);
                                               }
                                           });
                                }
                            });
    });
}

$("#categoryRefresh").click(function () {
    getTreeView();
});
