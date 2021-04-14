<template>
    <div class="site-wrapper site-page--login">
        <div class="site-content__wrapper">
            <div class="site-content">
                <div class="brand-info">
                    <h2 class="brand-info__text">iHelin后台管理</h2>
                    <p class="brand-info__intro">iHelin</p>
                </div>
                <div class="login-main">
                    <h3 class="login-title">管理员登录</h3>
                    <el-form
                        :model="dataForm"
                        :rules="dataRule"
                        ref="dataForm"
                        @keyup.enter.native="dataFormSubmit()"
                        status-icon
                    >
                        <el-form-item prop="username">
                            <el-input
                                v-model="dataForm.username"
                                placeholder="帐号"
                            ></el-input>
                        </el-form-item>
                        <el-form-item prop="password">
                            <el-input
                                v-model="dataForm.password"
                                type="password"
                                placeholder="密码"
                                show-password
                            ></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button
                                class="login-btn-submit"
                                type="primary"
                                @click="dataFormSubmit()"
                                >登录</el-button
                            >
                        </el-form-item>
                    </el-form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            dataForm: {
                username: "",
                password: "",
            },
            dataRule: {
                username: [
                    {
                        required: true,
                        message: "帐号不能为空",
                        trigger: "blur",
                    },
                ],
                password: [
                    {
                        required: true,
                        message: "密码不能为空",
                        trigger: "blur",
                    },
                ],
            },
        }
    },
    created() {},
    methods: {
        // 提交表单
        dataFormSubmit() {
            this.$refs["dataForm"].validate((valid) => {
                if (valid) {
                    this.$http({
                        url: "/sys/login",
                        method: "post",
                        data: {
                            username: this.dataForm.username,
                            password: this.dataForm.password,
                        },
                    }).then(({ data }) => {
                        if (data && data.code === 0) {
                            localStorage.setItem("token", data.data)
                            this.$message({
                                message: "登录成功",
                                type: "success",
                                duration: 1500,
                                onClose: () => {
                                    this.$router.replace({ name: "home" })
                                },
                            })
                        } else {
                            this.$message.error(data.msg)
                        }
                    })
                }
            })
        },
    },
}
</script>

<style lang="scss">
.site-wrapper.site-page--login {
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-color: rgba(38, 50, 56, 0.6);
    overflow: hidden;

    &:before {
        position: fixed;
        top: 0;
        left: 0;
        z-index: -1;
        width: 100%;
        height: 100%;
        content: "";
        background-image: url(~@/assets/img/login_bg.jpg);
        background-size: cover;
    }

    .site-content__wrapper {
        position: absolute;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        padding: 0;
        margin: 0;
        overflow-x: hidden;
        overflow-y: auto;
        background-color: transparent;
    }

    .site-content {
        min-height: 100%;
        padding: 30px 500px 30px 30px;
    }

    .brand-info {
        margin: 220px 100px 0 90px;
        color: #fff;
    }

    .brand-info__text {
        margin: 0 0 22px 0;
        font-size: 48px;
        font-weight: 400;
        text-transform: uppercase;
    }

    .brand-info__intro {
        margin: 10px 0;
        font-size: 16px;
        line-height: 1.58;
        opacity: 0.6;
    }

    .login-main {
        position: absolute;
        top: 0;
        right: 0;
        padding: 150px 60px 180px;
        width: 470px;
        min-height: 100%;
        background-color: #fff;
    }

    .login-title {
        font-size: 16px;
    }

    .login-btn-submit {
        width: 100%;
        margin-top: 38px;
    }
}
</style>
