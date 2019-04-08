<template>
    <el-table
            v-loading="loading"
            :data="tableData"
            border
            stripe
            style="width: 100%">
        <el-table-column
                prop="url"
                showOverflowTooltip
                width="200"
                label="url">
        </el-table-column>
        <el-table-column
                prop="method"
                width="150"
                align="center"
                showOverflowTooltip
                label="method">
        </el-table-column>
        <el-table-column
                prop="className"
                showOverflowTooltip
                label="className">
        </el-table-column>
        <el-table-column
                prop="classMethod"
                showOverflowTooltip
                label="classMethod">
        </el-table-column>
    </el-table>
</template>
<script>
    export default {
        data() {
            return {
                tableData: [],
                loading: true
            }
        },
        mounted() {
            this.$resource('/ihelin/admin/commons/mappings').get({}).then(res => {
                this.loading = false;
                this.tableData = res.data.data;
            })
        }
    }
</script>
