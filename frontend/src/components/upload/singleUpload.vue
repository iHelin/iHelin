<template>
    <el-upload
        class="upload"
        style="text-align: center;"
        :action="uploadAction"
        :data="dataObj"
        :drag="drag"
        multiple
        v-loading="uploadLoading"
        :show-file-list="false"
        :before-upload="beforeUpload"
        :on-success="handleUploadSuccess">
        <template v-if="drag">
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        </template>
        <template v-else>
            <el-input :value="value" placeholder="" readonly></el-input>
        </template>
    </el-upload>
</template>
<script>
import {getUUID} from '@/utils'
import {ossRootDir, ossUploadAction} from "@/utils/config"

export default {
    name: 'singleUpload',
    props: {
        value: String,
        drag: {
            type: Boolean,
            default: false
        },
        //不加前缀/，要加后缀/，如"test/"
        dir: {
            type: String,
            default: ''
        }
    },
    computed: {},
    data() {
        return {
            uploadLoading: false,
            uploadAction: ossUploadAction,
            dataObj: {
                key: '',
                policy: '',
                signature: '',
                OSSAccessKeyId: ''
            }
        };
    },
    methods: {
        async beforeUpload(file) {
            let {data} = await this.$http({
                url: "/sys/oss/policy",
                method: "get",
                params: {}
            });
            if (data.code !== 0) {
                this.$message.error('服务端签名失败！');
                return false;
            } else {
                this.dataObj.policy = data.data.policy;
                this.dataObj.signature = data.data.signature;
                this.dataObj.OSSAccessKeyId = data.data.accessKeyId;
                this.dataObj.key = `${ossRootDir}${this.dir}${getUUID()}_${file.name}`;
                this.uploadLoading = true;
                return true;
            }
        },
        handleUploadSuccess(response, file, fileList) {
            this.uploadLoading = false;
            this.$emit('input', this.uploadAction + '/' + this.dataObj.key);
        }
    }
}
</script>
<style>
.upload .el-upload--text {
    width: 100%;
}
</style>


