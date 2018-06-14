<template>
    <div>
        <el-table
                v-loading="loading"
                :data="tableData"
                style="width: 100%">
            <el-table-column
                    prop="title"
                    label="标题">
            </el-table-column>
            <el-table-column
                    prop="summary"
                    label="摘要">
            </el-table-column>
            <el-table-column
                    prop="readNum"
                    width="100"
                    label="阅读数">
            </el-table-column>
        </el-table>
        <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="pageNum"
                :page-sizes="[10, 20, 50]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
        </el-pagination>
    </div>
</template>
<script>
    export default {
        mounted() {
            this.$resource('/ihelin/admin/articleList').get({
                pageNum: this.pageNum,
                pageSize: this.pageSize
            }).then(res => {
                this.loading = false;
                this.tableData = res.data.data;
                this.total = res.data.totalCount;
            }, error => {
                // console.error(error);
            })
        },
        methods: {
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
            }
        },
        data() {
            return {
                pageNum: 1,
                pageSize: 10,
                total: 0,
                loading: true,
                tableData: []
            }
        }
    }
</script>
