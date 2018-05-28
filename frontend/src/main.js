// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import VueResource from 'vue-resource';
import ElementUI from 'element-ui';
import 'src/styles/element-variables.scss';
import 'src/styles/app.css';

Vue.config.productionTip = false;

Vue.use(ElementUI);
Vue.use(VueResource);
/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    components: {App},
    template: '<App/>'
});
