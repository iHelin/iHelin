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
            <el-form-item label="图片地址" prop="picUrl">
                <SingleUpload
                    v-model="dataForm.picUrl"
                    :drag="false"
                    :dir="uploadDir"
                >
                </SingleUpload>
            </el-form-item>
            <el-form-item label="图片类型" prop="type">
                <el-select v-model="dataForm.type" placeholder="图片类型">
                    <el-option value="index" label="首页"></el-option>
                    <el-option value="start" label="启动页"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="链接地址" prop="linkUrl" v-if="dataForm.type==='index'">
                <el-input
                    v-model="dataForm.linkUrl"
                    placeholder="链接地址"
                ></el-input>
            </el-form-item>
            <el-form-item label="状态" prop="enabled">
                <el-switch
                    v-model="dataForm.enabled"
                    placeholder="状态"
                    active-text="启用"
                    inactive-text="禁用"
                    :active-value="true"
                    :inactive-value="false"
                >
                </el-switch>
            </el-form-item>
            <el-form-item label="排序" prop="bannerSort">
                <el-input
                    v-model="dataForm.bannerSort"
                    placeholder="排序"
                ></el-input>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取消</el-button>
            <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
        </span>
    </el-dialog>
</template>
<script>
import SingleUpload from "src/components/upload/singleUpload"
import { formatDate } from "@/utils"

export default {
    components: {
        SingleUpload,
    },
    data() {
        return {
            uploadDir: `${formatDate(new Date())}/`,
            visible: false,
            dataForm: {
                id: null,
                picUrl: "",
                linkUrl: "",
                type: "index",
                enabled: true,
                bannerSort: 0,
            },
            dataRule: {
                picUrl: [
                    {
                        required: true,
                        message: "图片地址不能为空",
                        trigger: "change",
                    },
                ],
                linkUrl: [
                    {
                        required: true,
                        message: "链接地址不能为空",
                        trigger: "blur",
                    },
                ],
                type: [
                    {
                        required: true,
                        message: "图片类型不能为空",
                        trigger: "change",
                    },
                ],
                enabled: [
                    {
                        required: true,
                        message: "状态不能为空",
                        trigger: "change",
                    },
                ],
                bannerSort: [
                    {
                        required: true,
                        message: "排序不能为空",
                        trigger: "blur",
                    },
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
                        url: "/sys/banner/info/" + this.dataForm.id,
                        method: "get",
                        params: {},
                    }).then(({ data }) => {
                        if (data && data.code === 0) {
                            this.dataForm.picUrl = data.data.picUrl
                            this.dataForm.linkUrl = data.data.linkUrl
                            this.dataForm.type = data.data.type
                            this.dataForm.enabled = data.data.enabled
                            this.dataForm.bannerSort = data.data.bannerSort
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
                            "/sys/banner/" +
                            (this.dataForm.id ? "update" : "save"),
                        method: this.dataForm.id ? "put" : "post",
                        data: {
                            id: this.dataForm.id,
                            picUrl: this.dataForm.picUrl,
                            linkUrl: this.dataForm.linkUrl,
                            type: this.dataForm.type,
                            enabled: this.dataForm.enabled,
                            bannerSort: this.dataForm.bannerSort,
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
