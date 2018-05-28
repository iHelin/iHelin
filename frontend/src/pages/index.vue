<template>
    <div>
        <ul>
            <li v-for="article in articles">
                <router-link :to="'/articles/'+article.id">{{article.title}}</router-link>
            </li>
        </ul>
        <h2 style="text-align: center">{{poem.content}}</h2>
        <p style="text-align: right">{{poem.title}}</p>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                articles: [],
                poem: {
                    title: '',
                    content: ''
                }
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
                    this.articles = res.data;
                }, e => {
                    console.error(e);
                });
            },
            getPoem() {
                this.$resource('/ihelin/poem').get({}).then(res => {
                    this.poem = res.data;
                }, e => {
                    console.error(e);
                });
            }
        }
    }
</script>
