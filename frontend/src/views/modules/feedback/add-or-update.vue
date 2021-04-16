<template>
    <el-dialog
        :title="dataForm.id ? '修改' : '新增'"
        :close-on-click-modal="false"
        :visible.sync="visible">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
                 label-width="100px">
            <el-form-item label="userId" prop="userId">
                <el-input v-model="dataForm.userId" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="name" prop="name">
                <el-input v-model="dataForm.name" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="contract" prop="contract">
                <el-input v-model="dataForm.contract" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="content" prop="content">
                <el-input v-model="dataForm.content" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="createTime" prop="createTime">
                <el-input v-model="dataForm.createTime" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="handled" prop="handled">
                <el-input v-model="dataForm.handled" placeholder=""></el-input>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
    </el-dialog>
</template>

<script>
export default {
    data() {
        return {
            visible: false,
            dataForm: {
                id: null,
                userId: '',
                name: '',
                contract: '',
                content: '',
                createTime: '',
                handled: ''
            },
            dataRule: {
                userId: [
                    {required: true, message: '不能为空', trigger: 'blur'}
                ],
                name: [
                    {required: true, message: '不能为空', trigger: 'blur'}
                ],
                contract: [
                    {required: true, message: '不能为空', trigger: 'blur'}
                ],
                content: [
                    {required: true, message: '不能为空', trigger: 'blur'}
                ],
                createTime: [
                    {required: true, message: '不能为空', trigger: 'blur'}
                ],
                handled: [
                    {required: true, message: '不能为空', trigger: 'blur'}
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
                        url: '/sys/feedback/info/' + this.dataForm.id,
                        method: 'get',
                        params: {}
                    }).then(({data}) => {
                        if (data && data.code === 0) {
                            this.dataForm.userId = data.data.userId;
                            this.dataForm.name = data.data.name;
                            this.dataForm.contract = data.data.contract;
                            this.dataForm.content = data.data.content;
                            this.dataForm.createTime = data.data.createTime;
                            this.dataForm.handled = data.data.handled;
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
                        url: '/sys/feedback/' + (this.dataForm.id ? 'update' : 'save'),
                        method: this.dataForm.id ? 'put' : 'post',
                        data: {
                            id: this.dataForm.id,
                            userId: this.dataForm.userId,
                            name: this.dataForm.name,
                            contract: this.dataForm.contract,
                            content: this.dataForm.content,
                            createTime: this.dataForm.createTime,
                            handled: this.dataForm.handled
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
