<template>
    <el-container>
        <el-header style="padding:0">
            <el-menu
                    router
                    :default-active="activeIndex"
                    class="el-menu-demo"
                    mode="horizontal"
                    @select="handleSelect"
                    background-color="#545c64"
                    text-color="#fff"
                    active-text-color="#ffd04b">
                <el-menu-item index="/admin">首页</el-menu-item>
                <el-menu-item index="/admin/articles">文章</el-menu-item>
                <el-menu-item index="/admin/mappings">Mappings</el-menu-item>
                <el-menu-item index="/admin/props">Props</el-menu-item>
                <el-menu-item index="/admin/files">文件管理</el-menu-item>
                <el-menu-item style="float: right;" index="">{{$store.state.username}}</el-menu-item>
            </el-menu>
        </el-header>
        <el-main>
            <el-card>
                <router-view></router-view>
            </el-card>
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
