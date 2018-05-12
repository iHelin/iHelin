<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>管理后台</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="iHelin">
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/plugins/element-ui/index.css">
    <style>
        .login-container {
            -webkit-border-radius: 5px;
            border-radius: 5px;
            -moz-border-radius: 5px;
            background-clip: padding-box;
            margin: 180px auto;
            width: 350px;
            padding: 10px 35px 15px 35px;
            background: #fff;
            border: 1px solid #eaeaea;
            box-shadow: 0 0 25px #cac6c6;
        }

        .title {
            margin: 0px auto 40px auto;
            text-align: center;
            color: #505458;
        }

    </style>
</head>
<body>
<div id="app" style="display: none;" v-show="display">
    <div class="panel panel-primary">
        <el-form :model="loginForm"
                 action="${request.contextPath}/admin/login"
                 method="post"
                 label-position="left" label-width="0px"
                 class="login-container">
            <#if _csrf??>
                 <input type="hidden"
                        name="${_csrf.parameterName}"
                        value="${_csrf.token}"/>
            </#if>
            <h3 style="text-align: center;" class="title">系统登录</h3>
            <el-form-item>
            <#--<el-input type="text" name="username" v-model="loginForm.username" auto-complete="off"
                      placeholder="用户名"></el-input>-->
                <div class="el-input">
                    <input
                            autocomplete="off"
                            placeholder="用户名"
                            name="username"
                            type="text"
                            v-model="loginForm.username"
                            required
                            rows="2"
                            validateevent="true"
                            class="el-input__inner">
                </div>
            </el-form-item>
            <el-form-item>
            <#--<el-input type="password" name="password" v-model="loginForm.password" auto-complete="off"
                      placeholder="密码"></el-input>-->
                <div class="el-input">
                    <input
                            autocomplete="off"
                            placeholder="密码"
                            name="password"
                            type="password"
                            required
                            v-model="loginForm.password"
                            rows="2"
                            validateevent="true"
                            class="el-input__inner">
                </div>
            </el-form-item>
            <el-form-item>
                <el-row type="flex" justify="space-between">
                    <el-col :span="12">
                        <img :src="kaptchaSrc"
                             style="width: 100%;"
                             @click="changeKaptcha">
                    </el-col>
                    <el-col :span="11">
                    <#--<el-input
                            style="width: 100%;"
                            type="text"
                            name="captcha"
                            v-model="loginForm.captcha"
                            placeholder="验证码"
                            prefix-icon="el-icon-picture"
                            required>-->
                        <div class="el-input el-input--prefix" required="required" style="width: 100%;">
                            <input
                                    autocomplete="off"
                                    required
                                    placeholder="验证码"
                                    name="captcha"
                                    type="text"
                                    rows="2"
                                    v-model="loginForm.captcha"
                                    validateevent="true"
                                    prefixicon="el-icon-picture"
                                    class="el-input__inner">
                            <span class="el-input__prefix">
                                <i class="el-input__icon el-icon-picture"></i>
                            </span>
                        </div>
                    </el-col>
                </el-row>
            </el-form-item>
        <#if SPRING_SECURITY_LAST_EXCEPTION??>
            <el-alert
                    type="error"
                    center
                    style="margin-bottom: 10px;"
                    show-icon
                    title="${SPRING_SECURITY_LAST_EXCEPTION.message!}"></el-alert>
        </#if>
            <el-form-item>
                <el-button
                        type="primary"
                        native-type="submit"
                        style="width:100%;"
                        @click="handleSubmit"
                        :loading="logining">
                    登录
                </el-button>
            </el-form-item>
        </el-form>
    </div>
</div>
<script src="${request.contextPath}/plugins/vue/vue.js"></script>
<script src="${request.contextPath}/plugins/element-ui/index.js"></script>
<script>
    new Vue({
        el: '#app',
        data() {
            return {
                logining: false,
                loginForm: {
                    username: '',
                    password: '',
                    captcha: ''
                },
                display: false,
                kaptchaSrc: '${request.contextPath}/kaptcha'
            };
        },
        methods: {
            handleSubmit: function () {
                if (this.loginForm.username && this.loginForm.password && this.loginForm.captcha) {
                    this.logining = true;
                }
            },
            changeKaptcha: function () {
                this.kaptchaSrc = '${request.contextPath}/kaptcha?t=' + Math.random();
            }
        },
        mounted: function () {
            this.display = true;
        }
    });
</script>
</body>
</html>