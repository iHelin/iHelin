import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from '@/store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import '@/icons'
import '@/assets/scss/index.scss'
import httpRequest from '@/utils/httpRequest' // api: https://github.com/axios/axios
import {formatPayType, formatStatus, isAuth} from '@/utils';
import cloneDeep from 'lodash/cloneDeep';


Vue.use(ElementUI);

Vue.config.productionTip = false

// 挂载全局
Vue.prototype.$http = httpRequest; // ajax请求方法
Vue.prototype.isAuth = isAuth;     // 权限方法
Vue.filter('formatStatus', formatStatus);
Vue.filter('formatPayType', formatPayType);

// 保存整站vuex本地储存初始状态
window.SITE_CONFIG = {};
window.SITE_CONFIG['storeState'] = cloneDeep(store.state)

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
