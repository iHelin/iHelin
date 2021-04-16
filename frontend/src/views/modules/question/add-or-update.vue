<template>
    <el-dialog
        :title="dataForm.id ? '修改' : '新增'"
        :close-on-click-modal="false"
        :visible.sync="visible"
    >
        <el-form
            :model="dataForm"
            :rules="dataRule"
            ref="dataForm"
            @keyup.enter.native="dataFormSubmit()"
            label-width="100px"
        >
            <el-form-item label="问题" prop="question">
                <el-input v-model="dataForm.question" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="答案" prop="answer">
                <el-input v-model="dataForm.answer" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="sort" prop="sort">
                <el-input v-model="dataForm.sort" placeholder=""></el-input>
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
                question: "",
                answer: "",
                sort: 0,
            },
            dataRule: {
                question: [
                    { required: true, message: "不能为空", trigger: "blur" },
                ],
                answer: [
                    { required: true, message: "不能为空", trigger: "blur" },
                ],
                sort: [
                    { required: true, message: "不能为空", trigger: "blur" },
                ],
            },
        }
    },
    methods: {
        init(id) {
            this.dataForm.id = id
            this.visible = true
            this.$nextTick(() => {
                this.$refs["dataForm"].resetFields()
                if (this.dataForm.id) {
                    this.$http({
                        url: "/sys/question/info/" + this.dataForm.id,
                        method: "get",
                        params: {},
                    }).then(({ data }) => {
                        if (data && data.code === 0) {
                            this.dataForm.question = data.data.question
                            this.dataForm.answer = data.data.answer
                            this.dataForm.sort = data.data.sort
                        } else {
                            this.$message.error(data.msg)
                        }
                    })
                }
            })
        },
        // 表单提交
        dataFormSubmit() {
            this.$refs["dataForm"].validate((valid) => {
                if (valid) {
                    this.$http({
                        url:
                            "/sys/question/" +
                            (this.dataForm.id ? "update" : "save"),
                        method: this.dataForm.id ? "put" : "post",
                        data: {
                            id: this.dataForm.id,
                            question: this.dataForm.question,
                            answer: this.dataForm.answer,
                            sort: this.dataForm.sort,
                        },
                    }).then(({ data }) => {
                        if (data && data.code === 0) {
                            this.$message({
                                message: "操作成功",
                                type: "success",
                                duration: 1500,
                                onClose: () => {
                                    this.visible = false
                                    this.$emit("refreshDataList")
                                },
                            })
                        } else {
                            this.$message.error(data.msg)
                        }
                    })
                }
            })
        },
    },
}
</script>
