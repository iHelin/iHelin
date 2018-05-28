<template>
    <div>
        <div class="panel panel-primary">
            <el-form :model="loginForm"
                     action="/ihelin/admin/login"
                     method="post"
                     label-position="left" label-width="0px"
                     class="login-container">
                <h3 style="text-align: center;" class="title">系统登录</h3>
                <el-form-item>
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
                <a href="/ihelin/oauth2/authorization/github">GitHub 登录</a>
            </el-form>
        </div>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                logining: false,
                loginForm: {
                    username: '',
                    password: '',
                    captcha: ''
                },
                display: false,
                kaptchaSrc: '/ihelin/kaptcha'
            };
        },
        methods: {
            handleSubmit() {
                if (this.loginForm.username && this.loginForm.password && this.loginForm.captcha) {
                    this.logining = true;
                }
            },
            changeKaptcha() {
                this.kaptchaSrc = '/ihelin/kaptcha?t=' + Math.random();
            }
        }
    }
</script>
<style scoped>
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
