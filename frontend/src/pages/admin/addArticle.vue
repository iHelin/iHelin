<template>
    <div>
        <el-form ref="form" :model="form" label-width="80px">
            <el-form-item label="标题">
                <el-input v-model="form.title"></el-input>
            </el-form-item>
            <el-form-item label="摘要">
                <el-input type="textarea" v-model="form.summary"></el-input>
            </el-form-item>
            <el-form-item label="正文">
                <textarea id="markdown_editor"></textarea>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="save">保存文章</el-button>
                <el-button>取消</el-button>
            </el-form-item>
        </el-form>
        <!--<div v-html="content"></div>-->
        <!--<div v-html="html"></div>-->
    </div>
</template>

<script>
  import SimpleMDE from 'simplemde';

  export default {
        name: 'markdown-editor',
        data() {
            return {
                simplemde: null,
                html: '',
                form: {
                    title: '',
                    summary: '',
                    content: ''
                }
            }
        },
        methods: {
            save() {
                this.$resource('/ihelin/admin/articles').save(this.form).then(res => {
                    if (res.data.status === 'success') {
                        this.$router.push("/admin/articles");
                    }
                })
            }
        },
        mounted() {
            this.simplemde = new SimpleMDE({
                element: document.getElementById('markdown_editor'),
                toolbar: ['bold', 'italic', 'strikethrough', 'heading', 'heading-smaller', 'heading-bigger', 'heading-1', 'heading-2', 'heading-3', '|', 'code', 'quote', 'unordered-list', 'clean-block', '|', 'link', 'image', 'table', 'horizontal-rule', '|', 'preview', 'guide']
            });
            this.simplemde.codemirror.on('change', () => {
                this.form.content = this.simplemde.value();
            });
        }
    };
</script>
