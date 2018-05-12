<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<style>
    .time {
        font-size: 13px;
        color: #999;
    }

    .bottom {
        margin-top: 13px;
        line-height: 12px;
    }

    .button {
        padding: 0;
        float: right;
    }

    .image {
        width: 100%;
        display: block;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }

    .clearfix:after {
        clear: both
    }

    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }

    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }

    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
</style>
<script>
    new Vue({
        el: '#app',
        data: function () {
            return {
                files: [],
                imageUrl: ''
            }
        },
        mounted: function () {
            this.defaultActiveTag = 'file';
            this.init();
        },
        methods: {
            init: function () {
                this.$http.get('${request.contextPath}/admin/files').then(res => {
                    this.files = res.data;
                });
            },
            deleteFile: function (key) {
                this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$resource('${request.contextPath}/admin/files').delete({
                        key: key
                    }).then(res => {
                        if (res.data.status === 'success') {
                            this.init();
                            this.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                        }
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            handleAvatarSuccess(res, file) {
                this.$message({
                    type: 'success',
                    message: '上传成功!'
                });
                this.imageUrl = res;
                this.init();
            },
            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg';
                const isLt10M = file.size / 1024 / 1024 < 10;
                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt10M) {
                    this.$message.error('上传头像图片大小不能超过 10MB!');
                }
                return isJPG && isLt10M;
            }
        }
    });
</script>
</#assign>
<@main.page title="文件管理">
<el-row>
    <el-col :span="24">
        <el-upload
                class="avatar-uploader"
                action="${request.contextPath}/admin/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
    </el-col>
</el-row>
<el-row :gutter="10">
    <el-col :span="6" v-for="file in files" :key="file.key" v-if="file.mimeType.indexOf('image')>=0">
        <el-card :body-style="{ padding: '0px' }">
            <img :src="'${prefix!}'+file.key" :alt="file.key" class="image">
            <div style="padding: 14px;">
                <span>{{file.key}}</span>
                <div class="bottom clearfix">
                    <time class="time">{{ file.putTime }}</time>
                    <el-button type="text" @click="deleteFile(file.key)" class="button">删除</el-button>
                </div>
            </div>
        </el-card>
    </el-col>
</el-row>
</@main.page>