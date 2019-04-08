<template>
    <div style="margin:100px 80px;text-align:center;">
        <audio autoplay style="width: 100%" loop="loop" controls="controls" controlsList="nodownload">
            <source :src="ShapeofYou" type="audio/ogg"/>
            Your browser does not support the audio element.
        </audio>
    </div>
</template>
<script>
    import ShapeofYou from 'src/assets/ShapeofYou.mp3'

    export default {
        data() {
            return {
                ShapeofYou
            }
        }
    }
</script>
