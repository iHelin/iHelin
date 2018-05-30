<template>
    <div>
        <div v-if="article">
            <h1 style="text-align: center">{{article.title}}</h1>
            <p style="text-align: center">
                <small>作者：{{article.author}}</small>
                <small>阅读数：{{article.readNum}}</small>
                <small>更新时间：{{article.updateTime}}</small>
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
                article: undefined
            };
        },
        mounted() {
            this.$resource('/ihelin/articles/{id}').get({
                id: this.$route.params.id,
                pageNum: 1,
                pageLength: 10
            }).then(res => {
                console.log(res.data);
                this.article = res.data;
            });
        }
    }
</script>
<style>

</style>
