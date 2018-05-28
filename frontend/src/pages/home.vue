<template>
    <el-container>
        <el-header>
            <el-menu router :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                <el-menu-item index="/">首页</el-menu-item>
                <el-submenu index="2">
                    <template slot="title">我的工作台</template>
                    <el-menu-item index="/2-1">工作台1</el-menu-item>
                    <el-menu-item index="/2-2">工作台2</el-menu-item>
                    <el-menu-item index="/2-3">工作台3</el-menu-item>
                    <el-submenu index="2-4">
                        <template slot="title">工作台4</template>
                        <el-menu-item index="/2-4-1">选项1</el-menu-item>
                        <el-menu-item index="/2-4-2">选项2</el-menu-item>
                        <el-menu-item index="/2-4-3">选项3</el-menu-item>
                    </el-submenu>
                </el-submenu>
                <el-menu-item index="3" disabled>消息中心</el-menu-item>
            </el-menu>
        </el-header>
        <el-main>
            <router-view></router-view>
        </el-main>
        <el-footer>Footer</el-footer>
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
