import Vue from 'vue'
import App from './App'
import router from './router'
import VueResource from 'vue-resource';
import ElementUI from 'element-ui';
import 'src/styles/element-variables.scss';
import 'src/styles/app.css';

import Vuelidate from 'vuelidate';

Vue.use(Vuelidate);

Vue.config.productionTip = false;

Vue.use(ElementUI);
Vue.use(VueResource);

Vue.http.interceptors.push((request, next) => {
    next(response => {
        if (200 === response.status) {
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
