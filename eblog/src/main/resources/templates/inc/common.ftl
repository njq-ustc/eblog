<#macro paging pageData>
    <div style="text-align: center">
        <div id="laypage-main">

        </div>
        <script>
            layui.use('laypage', function(){
                var laypage = layui.laypage;

                //执行一个laypage实例
                laypage.render({
                    elem: 'laypage-main' //注意，这里的 test1 是 ID，不用加 # 号
                    ,count: ${pageData.total} //数据总数，从服务端得到
                    ,curr: ${pageData.current}//当前页
                    ,limit: ${pageData.size}//每页条数
                    ,jump: function(obj, first){
                        //obj包含了当前分页的所有参数，比如：
                        console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                        console.log(obj.limit); //得到每页显示的条数
                        //首次不执行
                        if(!first){
                            location.href = "?pn=" + obj.curr;
                        }
                    }
                });
            });
        </script>
    </div>
</#macro>