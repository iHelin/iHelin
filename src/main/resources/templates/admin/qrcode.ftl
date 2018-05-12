<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<script>
    var app = new Vue({
        el: '#app',
        data: {
            url: '',
            form: {
                content: null
            }
        },
        mounted: function () {
            this.defaultActiveTag = 'qrcode';
        },
        methods: {
            generate: function () {
                if (!this.form.content) {
                    alert("请输入内容！");
                    return false;
                }
                this.url = '${request.contextPath}/admin/qrcode/generate?content=' + this.form.content;
            }
        }
    });

</script>
</#assign>
<@main.page title="二维码">
<el-row>
    <el-col :span="12">
        <el-form ref="form" :model="form" label-width="80px">
            <el-form-item label="内容">
                <el-input
                        type="textarea"
                        autosize
                        v-model="form.content">
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="generate">生成</el-button>
            </el-form-item>
        </el-form>
    </el-col>
    <el-col :span="12">
        <div style="min-width:300px;min-height:300px;margin: auto;" align="center">
            <img :src="url"
                 style="vertical-align: middle;width:300px;height:300px;"
                 alt="二维码生成区">
        </div>
    </el-col>
</el-row>
</@main.page>