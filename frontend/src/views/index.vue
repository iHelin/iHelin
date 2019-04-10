<template>
    <div v-loading="loading">
        <div class="post">
            <template v-for="article in articles">
                <h1 class="post-title">
                    <router-link :to="'/articles/'+article.id">{{article.title}}</router-link>
                </h1>
                <span class="post-date">
                {{article.createTime | formatTime('{y}-{m}-{d}')}}
                ·
                {{article.author}}
                ·
                {{article.readNum}}
                </span>
                <p>{{article.summary}}</p>
                <p>
                    <router-link :to="'/articles/'+article.id">Read the full post</router-link>
                </p>
            </template>
        </div>
        <blockquote>{{content}}</blockquote>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                articles: [],
                loading: true,
                content: ''
            };
        },
        mounted() {
            this.getArticles();
            this.getPoem();
        },
        methods: {
            getArticles() {
                this.$resource('/ihelin/articles').get({
                    pageNum: 1,
                    pageLength: 5
                }).then(res => {
                    this.loading = false;
                    this.articles = res.data.list;
                }, e => {
                    console.error(e);
                });
            },
            getPoem() {
                this.$resource('/hitokoto/').get({}).then(res => {
                    this.content = res.data;
                }, e => {
                    console.error(e);
                });
            }
        }
    }
</script>
