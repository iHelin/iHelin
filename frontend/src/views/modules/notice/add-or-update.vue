<template>
    <el-dialog
        :title="dataForm.id ? '修改' : '新增'"
        :close-on-click-modal="false"
        :visible.sync="visible">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
                 label-width="100px">
            <el-form-item label="标题" prop="title">
                <el-input v-model="dataForm.title" placeholder="标题"></el-input>
            </el-form-item>
            <el-form-item label="内容" prop="content">
                <tinymce v-model="dataForm.content" :height="300"/>
            </el-form-item>
            <el-form-item label="状态" prop="enabled">
                <el-switch
                    v-model="dataForm.enabled"
                    placeholder="状态"
                    active-text="启用"
                    inactive-text="禁用"
                    :active-value="true"
                    :inactive-value="false">
                </el-switch>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
    </el-dialog>
</template>

<script>
import Tinymce from '@/components/Tinymce'

export default {
    components: {Tinymce},
    data() {
        return {
            visible: false,
            dataForm: {
                id: null,
                title: '',
                version: 1,
                content: '',
                enabled: true
            },
            dataRule: {
                title: [
                    {required: true, message: '标题不能为空', trigger: 'blur'}
                ],
                content: [
                    {required: true, message: '内容不能为空', trigger: 'blur'}
                ],
                enabled: [
                    {required: true, message: '状态不能为空', trigger: 'blur'}
                ]
            }
        }
    },
    methods: {
        init(id) {
            this.dataForm.id = id;
            this.visible = true;
            this.$nextTick(() => {
                this.$refs['dataForm'].resetFields();
                if (this.dataForm.id) {
                    this.$http({
                        url: '/sys/notice/info/' + this.dataForm.id,
                        method: 'get',
                        params: {}
                    }).then(({data}) => {
                        if (data && data.code === 0) {
                            this.dataForm.title = data.data.title;
                            this.dataForm.version = data.data.version;
                            this.dataForm.content = data.data.content;
                            this.dataForm.enabled = data.data.enabled;
                        } else {
                            this.$message.error(data.msg);
                        }
                    });
                }
            });
        },
        // 表单提交
        dataFormSubmit() {
            this.$refs['dataForm'].validate((valid) => {
                if (valid) {
                    this.$http({
                        url: '/sys/notice/' + (this.dataForm.id ? 'update' : 'save'),
                        method: this.dataForm.id ? 'put' : 'post',
                        data: {
                            id: this.dataForm.id,
                            title: this.dataForm.title,
                            version: this.dataForm.version,
                            content: this.dataForm.content,
                            enabled: this.dataForm.enabled
                        }
                    }).then(({data}) => {
                        if (data && data.code === 0) {
                            this.$message({
                                message: '操作成功',
                                type: 'success',
                                duration: 1500,
                                onClose: () => {
                                    this.visible = false;
                                    this.$emit('refreshDataList');
                                }
                            });
                        } else {
                            this.$message.error(data.msg);
                        }
                    });
                }
            })
        }
    }
}
</script>
