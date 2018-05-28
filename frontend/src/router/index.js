import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);

const router = new Router({
    routes: [
        {
            path: '/admin/login',
            meta: {
                title: '登录'
            },
            component: () => import('src/pages/login'),
        },
        {
            path: '/',
            component: () => import('src/pages/home'),
            children: [
                {
                    path: '/',
                    name: 'index',
                    meta: {
                        title: '首页'
                    },
                    component: () => import('src/pages/index')
                },
                {
                    path: '/articles',
                    name: 'articles',
                    meta: {
                        title: '文章列表'
                    },
                    component: () => import('src/pages/articles')
                },
                {
                    path: '/articles/:id',
                    name: 'article',
                    meta: {
                        title: '文章'
                    },
                    component: () => import('src/pages/article')
                },
                {
                    path: '/music',
                    name: 'music',
                    meta: {
                        title: '音乐'
                    },
                    component: () => import('src/pages/music')
                }, {
                    path: '/test',
                    name: 'test',
                    meta: {
                        title: 'test'
                    },
                    component: () => import('src/pages/test')
                }
            ]
        }
    ]
});

router.afterEach((to, from) => {
    if (to.meta.title) {
        document.title = to.meta.title + ' | Ian He'
    }
    window.scrollTo(0, 0);
});

export default router;
