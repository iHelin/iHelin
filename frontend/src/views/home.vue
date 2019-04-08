<template>
    <!--    <el-container>-->
    <!--        <el-header>-->
    <!--            <el-menu router :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">-->
    <!--                <el-menu-item index="/">首页</el-menu-item>-->
    <!--                <el-menu-item index="/articles">文章</el-menu-item>-->
    <!--                <el-menu-item index="/music">音乐</el-menu-item>-->
    <!--                <el-menu-item index="/feedback">反馈</el-menu-item>-->
    <!--            </el-menu>-->
    <!--        </el-header>-->
    <!--        <el-main>-->
    <!--            <el-card>-->
    <!--                <router-view></router-view>-->
    <!--            </el-card>-->
    <!--        </el-main>-->
    <!--        <el-footer style="color: #999;">-->
    <!--            <p style="text-align: center;">Copyright © Ian He 2018</p>-->
    <!--        </el-footer>-->
    <!--    </el-container>-->
    <div class="container content">
        <div class="masthead">
            <h3 class="masthead-title">
                <router-link to="/" title="Home">Ian Blog</router-link>
                &nbsp;&nbsp;&nbsp;
                <small>
                    <router-link to="/posts">Posts</router-link>
                </small>
                &nbsp;&nbsp;&nbsp;
                <small>
                    <router-link to="/music">Music</router-link>
                </small>
                &nbsp;&nbsp;&nbsp;
                <small>
                    <router-link to="/feedback">Feed</router-link>
                </small>
                &nbsp;&nbsp;&nbsp;
                <small>
                    <router-link to="/me">Me</router-link>
                </small>
                &nbsp;&nbsp;&nbsp;
                <small>
                    <router-link to="/switch">Switch Theme</router-link>
                </small>
            </h3>
        </div>
        <router-view></router-view>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                activeIndex: '1',
            }
        },
        methods: {
            handleSelect(key, keyPath) {
            }
        }
    }
</script>
