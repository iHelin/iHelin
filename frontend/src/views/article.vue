<template>
    <div v-loading="loading" class="post">
        <template v-if="article">
            <h1 class="post-title">{{article.title}}</h1>
            <span class="post-date">
                {{article.updateTime | formatTime('{y}-{m}-{d}')}}
                ·
                {{article.author}}
                ·
                {{article.readNum}}
            </span>
            <blockquote>{{article.summary}}</blockquote>
            <div v-html="html" class="content"></div>
        </template>
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
