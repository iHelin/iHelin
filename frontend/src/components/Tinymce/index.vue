<template>
    <div :class="{fullscreen:fullscreen}"
         class="tinymce-container"
         :style="{width:width}">
        <textarea :id="tinymceId" class="tinymce-textarea"/>
    </div>
</template>

<script>
import load from './dynamicLoadScript'
import {formatDate, getUUID} from "@/utils";
import {ossRootDir, ossUploadAction} from "@/utils/config";

const toolbar = [`fontselect | styleselect | fontsizeselect | forecolor backcolor | outdent indent | undo redo | removeformat`,
    `bullist numlist | hr link image charmap preview anchor pagebreak insertdatetime media table emoticons subscript superscript code codesample fullscreen`];
const plugins = [`advlist anchor autolink code codesample colorpicker colorpicker contextmenu directionality emoticons
 fullscreen hr image imagetools insertdatetime link lists media nonbreaking noneditable pagebreak paste preview print
  save searchreplace spellchecker tabfocus table template textcolor textpattern visualblocks visualchars wordcount`];

const tinymceCDN = 'https://cdn.jsdelivr.net/npm/tinymce-all-in-one@4.9.3/tinymce.min.js'

export default {
    name: 'Tinymce',
    props: {
        id: {
            type: String,
            default() {
                return 'vue-tinymce-' + +new Date() + ((Math.random() * 1000).toFixed(0) + '')
            }
        },
        value: {
            type: String,
            default: ''
        },
        toolbar: {
            type: Array,
            required: false,
            default() {
                return []
            }
        },
        height: {
            type: [Number, String],
            required: false,
            default: 360
        },
        width: {
            type: [Number, String],
            required: false,
            default: 'auto'
        }
    },
    data() {
        return {
            hasChange: false,
            hasInit: false,
            tinymceId: this.id,
            fullscreen: false
        }
    },
    computed: {},
    watch: {
        value(val) {
            if (!this.hasChange && this.hasInit) {
                this.$nextTick(() =>
                    window.tinymce.get(this.tinymceId).setContent(val || ''))
            }
        }
    },
    mounted() {
        this.init();
    },
    activated() {
        if (window.tinymce) {
            this.initTinymce();
        }
    },
    deactivated() {
        this.destroyTinymce();
    },
    destroyed() {
        this.destroyTinymce();
    },
    methods: {
        init() {
            load(tinymceCDN, (err) => {
                if (err) {
                    this.$message.error(err.message);
                    return;
                }
                this.initTinymce();
            });
        },
        initTinymce() {
            window.tinymce.init({
                selector: `#${this.tinymceId}`,
                language: 'zh_CN',
                height: this.height,
                object_resizing: false,
                toolbar: toolbar,
                plugins: plugins,
                menubar: false,
                end_container_on_empty_block: true,
                powerpaste_word_import: 'clean',
                advlist_bullet_styles: 'square',
                advlist_number_styles: 'default',
                default_link_target: '_blank',
                link_title: false,
                nonbreaking_force_tab: true, // inserting nonbreaking space &nbsp; need Nonbreaking Space Plugin
                init_instance_callback: editor => {
                    if (this.value) {
                        editor.setContent(this.value);
                    }
                    this.hasInit = true;
                    editor.on('NodeChange Change KeyUp SetContent', () => {
                        this.hasChange = true;
                        this.$emit('input', editor.getContent());
                    });
                },
                setup(editor) {
                    editor.on('FullscreenStateChanged', (e) => {
                        this.fullscreen = e.state;
                    });
                },
                convert_urls: false,
                urlconverter_callback: (url, node, onSave, name) => {
                    if (node === 'img' && url.startsWith('blob:')) {
                        // Do some custom URL conversion
                        tinymce.activeEditor && tinymce.activeEditor.uploadImages()
                    }
                    return url;
                },
                images_upload_handler: async (blobInfo, success, failure) => {
                    let {data} = await this.$http({
                        url: "/sys/oss/policy",
                        method: "get",
                        params: {}
                    });
                    if (data.code !== 0) {
                        this.$message.error('服务端签名失败！');
                        return false;
                    } else {
                        const file = blobInfo.blob();//转化为易于理解的file对象
                        const uploadDir = `${ossRootDir}${formatDate(new Date())}/`;
                        const fileKey = `${uploadDir}${getUUID()}_${file.name}`;
                        let xhr = new XMLHttpRequest();
                        xhr.withCredentials = false;
                        xhr.open('POST', ossUploadAction);
                        xhr.onload = () => {
                            success(`${ossUploadAction}/${fileKey}`);
                        };
                        let formData = new FormData();
                        formData.append("signature", data.data.signature);
                        formData.append("policy", data.data.policy);
                        formData.append("key", fileKey);
                        formData.append("OSSAccessKeyId", data.data.accessKeyId);
                        formData.append("file", file);
                        xhr.send(formData);
                    }
                }
            });
        },
        destroyTinymce() {
            const tinymce = window.tinymce.get(this.tinymceId);
            if (this.fullscreen) {
                tinymce.execCommand('mceFullScreen')
            }

            if (tinymce) {
                tinymce.destroy()
            }
        },
        setContent(value) {
            window.tinymce.get(this.tinymceId).setContent(value)
        },
        getContent() {
            window.tinymce.get(this.tinymceId).getContent()
        }
    }
}
</script>

<style lang="scss" scoped>
.tinymce-container {
    position: relative;
    line-height: normal;
}

.tinymce-container {
    ::v-deep {
        .mce-fullscreen {
            z-index: 2003;
        }
    }
}

.tinymce-textarea {
    visibility: hidden;
    z-index: -1;
}

.editor-upload-btn {
    display: inline-block;
}
</style>
