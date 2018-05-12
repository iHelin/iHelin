<#import "frame.ftl" as main>
<#assign html_other_script in main>
<script>
    new Vue({
        el: '#wrap',
        data: function () {
            return {
                text: '',
                speed: 2
            }
        },
        methods: {
            read: function () {
                this.$refs.v.play();
            }
        },
        mounted: function () {

        }
    });
</script>
</#assign>
<@main.page title="post">
    <div id="wrap" style="margin-top: 50px;">
        <label for="speed">语速</label><input id="speed" placeholder="1-10" type="text" v-model="speed">
        <audio style="display: none;" ref="v"
               :src="'http://tts.baidu.com/text2audio?lan=zh&ie=UTF-8&spd='+speed+'&text='+text"
               controls="controls">
            Your browser does not support the audio element.
        </audio>
        <div>
            <textarea v-model="text"></textarea>
            <button @click="read">播放</button>
        </div>
    </div>
</@main.page>
