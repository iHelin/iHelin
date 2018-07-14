<template>
    <div>
        <draggable v-model="myArray" :options="dragOptions" @end="onEnd">
            <el-tag closable v-for="element in myArray" :key="element.id" style="margin-right: 5px;">{{element.name}}
            </el-tag>
        </draggable>
    </div>
</template>
<script>
  import draggable from 'vuedraggable'

  export default {
        data() {
            return {
                editable: true,
                myArray: [{
                    id: 1,
                    name: '111111111111'
                }, {
                    id: 2,
                    name: '2222222'
                }]
            }
        },
        computed: {
            dragOptions() {
                return {
                    animation: 0,
                    group: 'description',
                    disabled: !this.editable,
                    ghostClass: 'ghost'
                };
            }
        },
        methods: {
            onEnd() {
                console.log(this.myArray);
            }
        },
        components: {
            draggable,
        },
    }
</script>
