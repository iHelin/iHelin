<template>
    <el-container>
        <el-header>
            <el-menu router :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                <el-menu-item index="/admin">首页</el-menu-item>
                <el-menu-item index="/admin/articles">文章</el-menu-item>
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
