<template>
    <el-dialog
        title="上传文件"
        :close-on-click-modal="false"
        @close="closeHandle"
        :visible.sync="visible">
        <SingleUpload @input="successHandle"
                      drag
                      :upload-action="uploadAction"
                      :dir="uploadDir">
        </SingleUpload>
    </el-dialog>
</template>

<script>
import SingleUpload from 'src/components/upload/singleUpload';
import {formatDate} from '@/utils';
import {ossUploadAction} from "@/utils/config";

export default {
    components: {
        SingleUpload
    },
    data() {
        return {
            uploadDir: `${formatDate(new Date())}/`,
            visible: false,
            successUrl: '',
            uploadAction: ossUploadAction
        }
    },
    methods: {
        init() {
            this.visible = true;
        },
        // 上传成功
        successHandle(successUrl) {
            this.visible = false;
            this.successUrl = successUrl;
        },
        // 弹窗关闭时
        closeHandle() {
            this.$emit('refreshDataList', this.successUrl);
        }
    }
}
</script>
