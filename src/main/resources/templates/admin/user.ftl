<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<script>
    new Vue({
        el: '#app',
        mounted: function () {
            this.defaultActiveTag = 'user';
        }
    })
</script>
</#assign>
<@main.page title="用户">
<div>users</div>
</@main.page>