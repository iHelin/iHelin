<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<script>
    new Vue({
        el: '#app',
        data: function () {
            return {
                tableData: []
            }
        },
        mounted: function () {
            this.defaultActiveTag = 'mapping';
            this.init();
        },
        methods: {
            init: function () {
                this.$http.get('${request.contextPath}/admin/mappings').then(res => {
                    this.tableData = res.data.data;
                });
            }
        }
    });
</script>
</#assign>
<@main.page title="请求路径">
<el-table
        :data="tableData"
        border
        stripe>
    <el-table-column
            inline-template
            type="index"
            align="right"
            width="60"
            label="序号">
        <span>{{$index + 1}}</span>
    </el-table-column>
    <el-table-column
            prop="url"
            show-overflow-tooltip
            label="请求路径">
    </el-table-column>
    <el-table-column
            align="center"
            prop="method"
            width="100"
            label="请求方法">
    </el-table-column>
    <el-table-column
            show-overflow-tooltip
            prop="className"
            label="类名">
    </el-table-column>
    <el-table-column
            show-overflow-tooltip
            prop="classMethod"
            label="方法名">
    </el-table-column>
</el-table>
</@main.page>