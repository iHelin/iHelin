<template>
    <div v-loading="loading" class="page">
        <h1 class="page-title">Posts</h1>
        <ul>
            <li v-for="article in articles">
                <p>
                    {{article.updateTime | formatTime('{y}-{m}-{d}')}} »
                    <router-link :to="'/articles/'+article.id">{{article.title}}</router-link>
                </p>
            </li>
        </ul>
        <h1 id="tags">
            Tags
        </h1>
        <p>
            <router-link to="/blog/java/">Java</router-link>
            ·
            <router-link to="/blog/js">Javascript</router-link>
        </p>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                articles: [],
                loading: true
            };
        },
        mounted() {
            this.$resource('/ihelin/articles').get({
                pageNum: 1,
                pageLength: 1000
            }).then(res => {
                this.articles = res.data.list;
                this.loading = false;
            }, e => {
                console.error(e);
            });
        }
    }
</script>
