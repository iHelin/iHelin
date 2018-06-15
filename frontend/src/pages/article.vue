<template>
    <div v-loading="loading" style="margin: 0 auto;padding-top: 20px;padding-bottom: 40px;width: 880px;">
        <div v-if="article">
            <h1 style="text-align: center">{{article.title}}</h1>
            <p style="text-align: center">
                <small>作者：{{article.author}}</small>
                <small>阅读数：{{article.readNum}}</small>
                <small>更新时间：{{article.updateTime | formatTime('{y}-{m}-{d}')}}</small>
            </p>
            <p><code>{{article.summary}}</code></p>
            <div v-html="html"></div>
        </div>
        <div v-else>文章不存在</div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                article: {
                    title: '',
                    author: '',
                    readNum: 0,
                    updateTime: Date(),
                    summary: ''
                },
                loading: true,
                html: ''
            };
        },
        mounted() {
            this.$resource('/ihelin/articles/{id}').get({
                id: this.$route.params.id
            }).then(res => {
                this.article = res.data;
                this.loading = false;
                import('showdown').then(showdown => {
                    const converter = new showdown.Converter();
                    this.html = converter.makeHtml(this.article.content);
                });
            });
        }
    }
</script>
<style>
    pre {
        margin-bottom: 20px;
        padding: 15px;
        font-size: 13px;
        word-wrap: normal;
        white-space: pre;
        overflow: auto;
        border-radius: 4px;
        display: block;
        color: #abb2bf;
        background: #282c34;
    }

    code {
        color: #c7254e;
        border-radius: 4px;
        padding: 2px 4px;
        background-color: #f6f6f6;
        border: none;
        font-size: 12px;
        white-space: pre-wrap;
        vertical-align: middle;
    }

    pre code {
        padding: 0;
        background-color: transparent;
        white-space: pre;
        border: none;
        font-size: 12px;
        color: inherit;
        vertical-align: middle;
    }


</style>
