<template>
    <div>
        <el-form :inline="true" @keyup.enter.native="getDataList()">
            <el-form-item>
                <el-input v-model="key" placeholder="参数名" clearable></el-input>
            </el-form-item>
            <el-form-item>
                <el-button v-if="isAuth('mall:banner:list')"
                           @click="getDataList()">查询
                </el-button>
                <el-button v-if="isAuth('mall:banner:save')"
                           type="primary"
                           @click="addOrUpdateHandle()">
                    新增
                </el-button>
                <el-button v-if="isAuth('mall:banner:delete')"
                           type="danger"
                           @click="deleteHandle()"
                           :disabled="dataListSelections.length <= 0">
                    批量删除
                </el-button>
            </el-form-item>
        </el-form>
        <el-table
            :data="dataList"
            border
            stripe
            v-loading="dataListLoading"
            @selection-change="selectionChangeHandle"
            style="width: 100%;">
            <el-table-column
                type="selection"
                header-align="center"
                align="center"
                width="50">
            </el-table-column>
            <el-table-column
                prop="id"
                header-align="center"
                align="center"
                label="id">
            </el-table-column>
            <el-table-column
                prop="picUrl"
                header-align="center"
                align="center"
                label="图片地址">
                <template slot-scope="scope">
                    <el-image
                        style="width: 80px; height: 80px"
                        :src="scope.row.picUrl"
                        fit="contain"/>
                </template>
            </el-table-column>
            <el-table-column
                prop="linkUrl"
                header-align="center"
                align="center"
                label="链接地址">
            </el-table-column>
            <el-table-column
                prop="picUrl"
                header-align="center"
                align="center"
                label="图片类型">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.type==='start'">启动页</el-tag>
                    <el-tag v-else-if="scope.row.type==='index'">首页</el-tag>
                </template>
            </el-table-column>
            <el-table-column
                prop="picUrl"
                header-align="center"
                align="center"
                label="状态">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.enabled" type="success">启用</el-tag>
                    <el-tag v-else type="danger">禁用</el-tag>
                </template>
            </el-table-column>
            <el-table-column
                prop="bannerSort"
                header-align="center"
                align="center"
                label="排序">
            </el-table-column>
            <el-table-column
                prop="createTime"
                header-align="center"
                align="center"
                label="创建时间">
            </el-table-column>
            <el-table-column
                prop="updateTime"
                header-align="center"
                align="center"
                label="更新时间">
            </el-table-column>
            <el-table-column
                fixed="right"
                header-align="center"
                align="center"
                width="150"
                label="操作">
                <template slot-scope="scope">
                    <el-button type="text" size="small"
                               @click="addOrUpdateHandle(scope.row.id)">修改
                    </el-button>
                    <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            @size-change="sizeChangeHandle"
            @current-change="currentChangeHandle"
            :current-page="pageIndex"
            :page-sizes="[10, 20, 50, 100]"
            :page-size="pageSize"
            :total="totalPage"
            layout="total, sizes, prev, pager, next, jumper">
        </el-pagination>
        <!-- 弹窗, 新增 / 修改 -->
        <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    </div>
</template>

<script>
import AddOrUpdate from './add-or-update'

export default {
    data() {
        return {
            key: '',
            dataList: [],
            pageIndex: 1,
            pageSize: 10,
            totalPage: 0,
            dataListLoading: false,
            dataListSelections: [],
            addOrUpdateVisible: false
        }
    },
    components: {
        AddOrUpdate
    },
    activated() {
        this.getDataList();
    },
    methods: {
        // 获取数据列表
        getDataList() {
            if (!this.isAuth('mall:banner:list')) {
                return;
            }
            this.dataListLoading = true;
            this.$http({
                url: '/sys/banner/list',
                method: 'get',
                params: {
                    'page': this.pageIndex,
                    'limit': this.pageSize,
                    'key': this.key
                }
            }).then(({data}) => {
                if (data && data.code === 0) {
                    this.dataList = data.data.records;
                    this.totalPage = data.data.total;
                } else {
                    this.$message.error(data.msg);
                    this.dataList = [];
                    this.totalPage = 0;
                }
                this.dataListLoading = false;
            });
        },
        // 每页数
        sizeChangeHandle(val) {
            this.pageSize = val;
            this.pageIndex = 1;
            this.getDataList();
        },
        // 当前页
        currentChangeHandle(val) {
            this.pageIndex = val;
            this.getDataList();
        },
        // 多选
        selectionChangeHandle(val) {
            this.dataListSelections = val;
        },
        // 新增 / 修改
        addOrUpdateHandle(id) {
            this.addOrUpdateVisible = true;
            this.$nextTick(() => {
                this.$refs.addOrUpdate.init(id);
            });
        },
        // 删除
        deleteHandle(id) {
            let ids = id ? [id] : this.dataListSelections.map(item => item.id);
            this.$confirm("确定删除？", '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$http({
                    url: '/sys/banner/delete',
                    method: 'delete',
                    data: ids
                }).then(({data}) => {
                    if (data && data.code === 0) {
                        this.$message({
                            message: '操作成功',
                            type: 'success',
                            duration: 1500,
                            onClose: () => {
                                this.getDataList();
                            }
                        });
                    } else {
                        this.$message.error(data.msg);
                    }
                });
            });
        }
    }
}
</script>
