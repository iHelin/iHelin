<template>
    <div>
        <div v-if="article" v-html="article.content"></div>
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
