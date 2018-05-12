<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<script>
    new Vue({
        el: '#app',
        data: function () {
            return {

            }
        },
        mounted: function () {
            this.defaultActiveTag = 'index';
        }

    })
</script>
</#assign>
<@main.page title="首页">
<div>index</div>
</@main.page>