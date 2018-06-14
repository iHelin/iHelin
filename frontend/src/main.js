import Vue from 'vue'
import App from './App'
import router from './router'
import VueResource from 'vue-resource';
import store from './store';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'simplemde/dist/simplemde.min.css';
// import 'src/styles/element-variables.scss';
import 'src/styles/app.css';
import {formatTime} from 'src/components/index';

import Vuelidate from 'vuelidate';

Vue.filter('formatTime', formatTime);

Vue.use(Vuelidate);

Vue.config.productionTip = false;

Vue.use(ElementUI);
Vue.use(VueResource);

Vue.http.interceptors.push((request, next) => {
    console.log(router);
    next(response => {
        if (200 === response.status) {
        } else if (403 === response.status) {
            router.push({
                path: '/login',
            });
            // store.dispatch('setFrom', router.from);
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
    store,
    components: {App},
    template: '<App/>'
});
