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
            this.defaultActiveTag = 'property';
            this.init();
        },
        methods: {
            init: function () {
                this.$http.get('${request.contextPath}/admin/props').then(res => {
                    for (item in res.data.data) {
                        this.tableData.push({
                            key: item,
                            value: res.data.data[item]
                        });
                    }
                });
            }
        }
    });
</script>
</#assign>
<@main.page title="系统属性">
<el-table
        :data="tableData"
        border
        stripe>
    <el-table-column
            inline-template
            type="index"
            width="60"
            align="right"
            label="序号">
        <span>{{key}}</span>
    </el-table-column>
    <el-table-column
            prop="key"
            show-overflow-tooltip
            label="key">
    </el-table-column>
    <el-table-column
            prop="value"
            show-overflow-tooltip
            label="value">
    </el-table-column>
</el-table>
</@main.page>