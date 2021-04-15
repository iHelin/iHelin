<template>
    <div>
        <el-form :model="dataForm" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
                 label-width="100px">
            <el-form-item label="内容详情" prop="title">
                <tinymce v-if="instance" v-model="dataForm.content" :height="300"/>
            </el-form-item>
        </el-form>
        <div style="text-align: center;">
            <el-button type="primary" @click="dataFormSubmit()">保存</el-button>
        </div>
    </div>
</template>
<script>
import Tinymce from '@/components/Tinymce'

export default {
    data() {
        return {
            instance: false,
            dataForm: {
                id: null,
                type: 'index',
                version: null,
                content: ''
            }
        }
    },
    methods: {
        // 表单提交
        dataFormSubmit() {
            this.$http({
                url: '/sys/article/update',
                method: 'put',
                data: {
                    type: 'index',
                    id: this.dataForm.id,
                    version: this.dataForm.version,
                    content: this.dataForm.content
                }
            }).then(({data}) => {
                if (data && data.code === 0) {
                    this.$message({
                        message: '保存成功',
                        type: 'success',
                        duration: 2000
                    });
                    this.getArticleInfo();
                } else {
                    this.$message.error(data.msg);
                }
            });
        },
        async getArticleInfo() {
            const {data} = await this.$http({
                url: '/sys/article/info',
                method: 'get',
                params: {
                    type: "index"
                }
            });
            if (data && data.code === 0) {
                this.dataForm.id = data.data.id;
                this.dataForm.version = data.data.version;
                this.dataForm.content = data.data.content;
            } else {
                this.$message.error(data.msg);
            }
        }
    },
    components: {Tinymce},
    activated() {
        this.instance = true;
        this.getArticleInfo();
    },
    deactivated() {
        this.instance = false;
    }
}
</script>
<style scoped>
.editor-content {
    margin-top: 20px;
}
</style>
