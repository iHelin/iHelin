<template>
    <div>
        <el-row>
            <el-col :span="24">
                <el-upload
                        class="avatar-uploader"
                        action="/ihelin/admin/upload"
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
                    <img :src="prefix+file.key" :alt="file.key" class="image">
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
    </div>
</template>
<script>
    export default {
        data: function () {
            return {
                files: [],
                imageUrl: '',
                prefix: 'https://resource.ianhe.me/'
            }
        },
        mounted() {
            this.init();
        },
        methods: {
            handleAvatarSuccess(res, file) {
                this.$message({
                    type: 'success',
                    message: '上传成功!'
                });
                this.imageUrl = res.data;
                this.init();
            },
            deleteFile(key) {
                this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$resource('/ihelin/admin/files').delete({
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
            init() {
                this.$http.get('/ihelin/admin/files').then(res => {
                    this.files = res.data;
                });
            },
            beforeAvatarUpload(file) {
                const isLt10M = file.size / 1024 / 1024 < 10;
                if (!isLt10M) {
                    this.$message.error('上传头像图片大小不能超过 10MB!');
                }
                return isLt10M;
            }
        }
    }
</script>
