<template>
    <div>
        <div v-html="content"></div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                content: ''
            };
        },
        mounted() {
            this.$resource('/ihelin/articles/{id}').get({
                id: this.$route.params.id,
                pageNum: 1,
                pageLength: 10
            }).then(res => {
                console.log(res.data);
                this.content = res.data.content;
            });
        }
    }
</script>
<style>

</style>
