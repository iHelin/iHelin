<template>
    <el-dialog
        title="云存储配置"
        :close-on-click-modal="false"
        :visible.sync="visible">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
                 label-width="120px">
            <el-form-item label="域名">
                <el-input v-model="dataForm.aliyunDomain" placeholder="阿里云绑定的域名"></el-input>
            </el-form-item>
            <el-form-item label="路径前缀">
                <el-input v-model="dataForm.aliyunPrefix" placeholder="不设置默认为空"></el-input>
            </el-form-item>
            <el-form-item label="EndPoint">
                <el-input v-model="dataForm.aliyunEndPoint" placeholder="阿里云EndPoint"></el-input>
            </el-form-item>
            <el-form-item label="AccessKeyId">
                <el-input v-model="dataForm.aliyunAccessKeyId" placeholder="阿里云AccessKeyId"></el-input>
            </el-form-item>
            <el-form-item label="AccessKeySecret">
                <el-input v-model="dataForm.aliyunAccessKeySecret" placeholder="阿里云AccessKeySecret"></el-input>
            </el-form-item>
            <el-form-item label="BucketName">
                <el-input v-model="dataForm.aliyunBucketName" placeholder="阿里云BucketName"></el-input>
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
            dataForm: {},
            dataRule: {}
        }
    },
    methods: {
        init(id) {
            this.visible = true
            this.$http({
                url: '/sys/oss/config',
                method: 'get',
                params: {}
            }).then(({data}) => {
                this.dataForm = data && data.code === 0 ? data.config : []
            })
        },
        // 表单提交
        dataFormSubmit() {
            this.$refs['dataForm'].validate((valid) => {
                if (valid) {
                    this.$http({
                        url: '/sys/oss/saveConfig',
                        method: 'post',
                        data: this.dataForm
                    }).then(({data}) => {
                        if (data && data.code === 0) {
                            this.$message({
                                message: '操作成功',
                                type: 'success',
                                duration: 1500,
                                onClose: () => {
                                    this.visible = false
                                }
                            })
                        } else {
                            this.$message.error(data.msg)
                        }
                    })
                }
            })
        }
    }
}
</script>

