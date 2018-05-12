<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content="细心在任何时候都不是多余的"/>
    <meta name="author" content="Ian He"/>
    <title>WebSocket Test</title>
    <link rel="icon" href="${request.contextPath}/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/plugins/element-ui/index.css">
</head>
<body>
<div id="app">
    <h1>WebSocket Test</h1>
    <el-input
            type="textarea"
            :rows="2"
            placeholder="请输入内容"
            v-model="data">
    </el-input>
    <el-button @click="send">发送</el-button>
</div>
<script src="${request.contextPath}/plugins/vue/vue.js"></script>
<script src="${request.contextPath}/plugins/vue/vue-resource.js"></script>
<script src="${request.contextPath}/plugins/element-ui/index.js"></script>
<script>
    new Vue({
        el: '#app',
        data: {
            webSocket: null,
            data: 'test'
        },
        mounted: function () {
            this.init();
        },
        beforeDestroy: function () {
            this.webSocket.close();
        },
        methods: {
            send: function () {
                this.$http.post('${request.contextPath}/ws', {
                    data: this.data
                }).then(res => {
//                    console.log(res);
                });
            },
            init: function () {
                var that = this;
                if ('WebSocket' in window) {
                    this.webSocket = new WebSocket('ws://${serverName!}${request.contextPath}/webSocket');
                } else {
                    alert("该浏览器不支持webSocket！");
                }
                this.webSocket.onopen = function (event) {
                    console.log('建立连接！');
                };
                this.webSocket.onclose = function (event) {
                    console.log('关闭连接！');
                };
                this.webSocket.onmessage = function (event) {
                    that.$message({
                        message: '收到消息：' + event.data,
                        type: 'success'
                    });
                };
                this.webSocket.onerror = function (event) {
                    that.$message({
                        message: 'webSocket通信发生错误！',
                        type: 'error'
                    });
                };
            }
        }
    });
</script>
</body>
</html>