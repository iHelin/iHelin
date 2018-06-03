<template>
    <div>
        <div v-html="content"></div>
        <div v-html="html"></div>
        <div class="markdown-con">
            <textarea id="iview_admin_markdown_editor"></textarea>
        </div>
    </div>
</template>

<script>
  import SimpleMDE from 'simplemde';

  export default {
        name: 'markdown-editor',
        data() {
            return {
                content: '',
                simplemde: undefined,
                html: ''
            }
        },
        mounted() {
            this.simplemde = new SimpleMDE({
                element: document.getElementById('iview_admin_markdown_editor'),
                toolbar: ['bold', 'italic', 'strikethrough', 'heading', 'heading-smaller', 'heading-bigger', 'heading-1', 'heading-2', 'heading-3', '|', 'code', 'quote', 'unordered-list', 'clean-block', '|', 'link', 'image', 'table', 'horizontal-rule', '|', 'preview', 'guide']
            });
            this.simplemde.codemirror.on('change', () => {
                this.content = this.simplemde.value();
                import('showdown').then(showdown => {
                    const converter = new showdown.Converter();
                    this.html = converter.makeHtml(this.content);
                });
            });
        }
    };
</script>
