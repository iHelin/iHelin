import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            component: () => import('src/pages/home'),
            children: [
                {
                    path: '/',
                    name: 'index',
                    component: () => import('src/pages/index')
                },
                {
                    path: '/articles/:id',
                    name: 'articles',
                    component: () => import('src/pages/article')
                }
            ]
        }
    ]
})
