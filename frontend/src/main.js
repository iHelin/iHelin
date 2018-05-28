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

Vue.http.interceptors.push((request, next) => {
    next(response => {
        if (200 === response.status) {
            /*ElementUI.Notification.success({
                title: response.status + ' ' + response.statusText,
                message: response.data.message
            });*/
        } else {
            ElementUI.Notification.error({
                title: response.status + ' ' + response.statusText,
                message: 'path: ' + response.data.path
            });
        }
        return response;
    });
});
Vue.http.options.emulateJSON = true;

new Vue({
    el: '#app',
    router,
    components: {App},
    template: '<App/>'
});
