<template>
    <div>
        <el-form ref="form" :model="article" label-width="80px">
            <el-form-item label="标题">
                <el-input v-model="article.title"></el-input>
            </el-form-item>
            <el-form-item label="摘要">
                <el-input type="textarea" v-model="article.summary"></el-input>
            </el-form-item>
            <el-form-item label="正文">
                <textarea id="markdown_editor"></textarea>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="save">保存文章</el-button>
                <el-button>取消</el-button>
            </el-form-item>
        </el-form>
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
                article: {
                    title: '',
                    summary: '',
                    content: ''
                }
            }
        },
        methods: {
            save() {
                this.$resource('/ihelin/admin/article').update({
                    id: this.article.id,
                    title: this.article.title,
                    summary: this.article.summary,
                    content: this.article.content
                }).then(res => {
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
            this.$resource('/ihelin/admin/articles/{id}').get({
                id: this.$route.query.id
            }).then(res => {
                this.article = res.data;
                this.simplemde.value(this.article.content);
            });
            this.simplemde.codemirror.on('change', () => {
                this.article.content = this.simplemde.value();
            });
        }
    };
</script>
