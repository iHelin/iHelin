<template>
    <ul>
        <li v-for="article in articles">
            <router-link :to="'/articles/'+article.id">{{article.title}}</router-link>
        </li>
    </ul>
</template>
<script>
    export default {
        data() {
            return {
                articles: []
            };
        },
        mounted() {
            this.$resource('/ihelin/articles').get({
                pageNum: 1,
                pageLength: 10
            }).then(res => {
                this.articles = res.data;
            }, e => {
                console.error(e);
            });
        }
    }
</script>
