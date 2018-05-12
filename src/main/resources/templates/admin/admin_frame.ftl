<#macro page title>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>${title!} - Ian He 管理后台</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content="细心在任何时候都不是多余的"/>
    <meta name="author" content="Ian He"/>
    <link rel="icon" href="${request.contextPath}/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/plugins/element-ui/index.css">
    <style>
        body {
            margin: 0;
        }
    </style>
</head>
<body>
<div id="app" style="display: none;" v-show="defaultShow">
    <el-row class="tac">
        <el-col :span="4">
            <el-menu
                    :default-active="defaultActiveTag"
                    class="el-menu-vertical-demo"
                    @select="handleSelect"
                    background-color="#545c64"
                    text-color="#fff"
                    active-text-color="#ffd04b">
                <el-menu-item index="index">
                    <i class="el-icon-info"></i>
                    <span slot="title">首页</span>
                </el-menu-item>
                <el-menu-item index="user">
                    <i class="el-icon-menu"></i>
                    <span slot="title">用户</span>
                </el-menu-item>
                <el-menu-item index="article">
                    <i class="el-icon-setting"></i>
                    <span slot="title">文章</span>
                </el-menu-item>
                <el-menu-item index="qrcode">
                    <i class="el-icon-setting"></i>
                    <span slot="title">二维码</span>
                </el-menu-item>
                <el-menu-item index="file">
                    <i class="el-icon-setting"></i>
                    <span slot="title">文件管理</span>
                </el-menu-item>
                <el-menu-item index="property">
                    <i class="el-icon-setting"></i>
                    <span slot="title">系统属性</span>
                </el-menu-item>
                <el-menu-item index="mapping">
                    <i class="el-icon-setting"></i>
                    <span slot="title">请求路径</span>
                </el-menu-item>
            </el-menu>
        </el-col>
        <el-col :span="20">
            <el-card style="min-height: 500px;">
                <div slot="header" class="clearfix">
                    <span>${title!}</span>
                </div>
                <#nested>
            </el-card>
        </el-col>
    </el-row>
</div>
<script src="${request.contextPath}/plugins/vue/vue.js"></script>
<script src="${request.contextPath}/plugins/vue/vue-resource.js"></script>
<script src="${request.contextPath}/plugins/element-ui/index.js"></script>

<script type='text/javascript'>
    Vue.http.interceptors.push((request, next) => {
        next(res => {
            switch (res.status) {
                case 200:
//                    console.log("ok");
                    break;
                case 403:
                    break;
                case 404:
                    console.log("404");
                default:
                    break;
            }
        });
    });

    Date.prototype.format = function (format) {
        var o = {
            "M+": this.getMonth() + 1, //month
            "d+": this.getDate(), //day
            "H+": this.getHours(), //hour
            "m+": this.getMinutes(), //minute
            "s+": this.getSeconds(), //second
            "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
            "S": this.getMilliseconds() //millisecond
        };

        if (/(y+)/i.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }

        for (let k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    };

    Vue.mixin({
        data: function () {
            return {
                defaultActiveTag: 'index',
                defaultShow: false
            }
        },
        mounted: function () {
            this.defaultShow = true;
        },
        computed: {},
        methods: {
            handleSelect(key, keyPath) {
                window.location.href = '${request.contextPath}/admin/' + key;
            }
        },
    });
</script>
    ${html_other_script!}
</body>
</html>
</#macro>
