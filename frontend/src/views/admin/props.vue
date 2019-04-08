<template>
    <el-table
            v-loading="loading"
            :data="tableData"
            border
            stripe
            style="width: 100%">
        <el-table-column
                prop="key"
                showOverflowTooltip
                label="key">
        </el-table-column>
        <el-table-column
                prop="value"
                showOverflowTooltip
                label="value">
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
            this.$resource('/ihelin/admin/commons/props').get({}).then(res => {
                this.loading = false;
                Object.keys(res.data.data).forEach(key => {
                    this.tableData.push({
                        key: key,
                        value: res.data.data[key]
                    })
                })
                // this.tableData = res.data.data;
            })
        }
    }
</script>
