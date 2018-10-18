<template>
    <div>
        <div style="float: right">
            <el-button type="primary" @click="()=>$router.push('/admin/articles/add')">新增文章</el-button>
        </div>
        <el-table
                v-loading="loading"
                border
                stripe
                :data="tableData"
                style="width: 100%">
            <el-table-column
                    prop="title"
                    showOverflowTooltip
                    width="300"
                    label="标题">
            </el-table-column>
            <el-table-column
                    prop="summary"
                    showOverflowTooltip
                    label="摘要">
            </el-table-column>
            <el-table-column
                    prop="readNum"
                    width="100"
                    align="right"
                    label="阅读数">
            </el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作"
                    align="center"
                    width="100">
                <template slot-scope="scope">
                    <!--<el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>-->
                    <el-button type="text" size="small" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button type="text" size="small" @click="handleDelete(scope.row)">删除</el-button>
                </template>
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
            this.init();
        },
        methods: {
            handleSizeChange(val) {
                this.pageNum = 1;
                this.pageSize = val;
                this.init();
            },
            handleCurrentChange(val) {
                this.pageNum = val;
                this.init();
            },
            handleDelete(article) {
                this.$resource('/ihelin/admin/articles/{id}').remove({
                    id: article.id
                }).then(res => {
                    this.pageNum = 1;
                    this.init();
                }, error => {
                    // console.error(error);
                })
            },
            handleEdit(article) {
                this.$router.push({
                    path: '/admin/articles/edit',
                    query: {
                        id: article.id
                    }
                })
            },
            init() {
                this.loading = true;
                this.$resource('/ihelin/admin/articles').get({
                    pageNum: this.pageNum,
                    pageSize: this.pageSize
                }).then(res => {
                    this.loading = false;
                    this.tableData = res.data.list;
                    this.total = res.data.total;
                }, error => {
                    // console.error(error);
                })
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
