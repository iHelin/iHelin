<template>
    <div>
        <ul v-loading="loading">
            <li v-for="article in articles">
                <router-link :to="'/articles/'+article.id">{{article.title}}</router-link>
            </li>
        </ul>
        <el-collapse v-model="activeNames">
            <el-collapse-item :title="poem.title" name="1">
                <div>{{poem.content}}</div>
            </el-collapse-item>
        </el-collapse>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                activeNames: ['1'],
                articles: [],
                loading: true,
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
                    this.loading = false;
                    this.articles = res.data.list;
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
