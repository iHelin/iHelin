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
import {formatTime, parseTime} from 'src/components/index';

Vue.filter('formatTime', formatTime);

Vue.config.productionTip = false;

Vue.prototype.$parseTime = parseTime;
Vue.use(ElementUI);
Vue.use(VueResource);

Vue.http.interceptors.push((request, next) => {
    next(response => {
        if (200 === response.status || 400 === response.status) {
        } else if (401 === response.status) {
            router.push({
                path: '/login',
            });
            // store.dispatch('setFrom', router.from);
        } else if (403 === response.status) {
            ElementUI.Message.error('权限不足');
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
    router,
    store,
    render: h => h(App)
}).$mount('#app');
