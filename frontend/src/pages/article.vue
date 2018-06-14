<template>
    <div v-loading="loading">
        <div v-if="article">
            <h1 style="text-align: center">{{article.title}}</h1>
            <p style="text-align: center">
                <small>作者：{{article.author}}</small>
                <small>阅读数：{{article.readNum}}</small>
                <small>更新时间：{{article.updateTime | formatTime('{y}-{m}-{d}')}}</small>
            </p>
            <p><code>{{article.summary}}</code></p>
            <div v-html="article.content"></div>
        </div>
        <div v-else>文章不存在</div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                article: null,
                loading: true
            };
        },
        mounted() {
            this.$resource('/ihelin/articles/{id}').get({
                id: this.$route.params.id
            }).then(res => {
                this.article = res.data;
                this.loading = false;
            });
        }
    }
</script>
<style>

</style>
