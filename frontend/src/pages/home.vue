<template>
    <el-container>
        <el-header>
            <el-menu router :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                <el-menu-item index="/">首页</el-menu-item>
                <el-menu-item index="/articles">文章</el-menu-item>
                <el-menu-item index="/music">音乐</el-menu-item>
                <el-menu-item index="/test">测试</el-menu-item>
                <el-submenu index="2">
                    <template slot="title">我的工作台</template>
                    <el-menu-item index="/2-1">工作台1</el-menu-item>
                    <el-submenu index="2-4">
                        <template slot="title">工作台4</template>
                        <el-menu-item index="/2-4-1">选项1</el-menu-item>
                    </el-submenu>
                </el-submenu>
            </el-menu>
        </el-header>
        <el-main>
            <router-view></router-view>
        </el-main>
        <el-footer style="color: #999;">
            <p style="text-align: center;">Copyright © Ian He 2018</p>
        </el-footer>
    </el-container>
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
