<template>
    <div>
        <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="您的邮箱" prop="email"
                          :rules="[
                  { required: true, message: '请输入您的邮箱', trigger: 'blur' },
                  { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                ]">
                <el-input v-model="ruleForm.email" placeholder="请输入您的邮箱"></el-input>
            </el-form-item>
            <el-form-item label="您的建议" prop="message"
                          :rules="[
                  {required: true, message: '请输入您的建议', trigger: 'blur'},
                  {min: 3, message: '至少3个字符', trigger: 'blur'}
                ]">
                <el-input type="textarea" v-model="ruleForm.message" placeholder="请输入您的建议"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm')">立即提交</el-button>
                <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                ruleForm: {
                    email: 'ahaqhelin@163.com',
                    message: ''
                }
            }
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        const loading = this.$loading();
                        this.$resource("/ihelin/advices").save(this.ruleForm).then(res => {
                            loading.close();
                            this.$message[res.data.status]('感谢您的反馈！');
                        })
                    } else {
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    }
</script>
