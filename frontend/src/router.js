import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);

const routes = [
    {
        path: '/login',
        meta: {
            title: '登录'
        },
        component: () => import('src/views/login')
    },
    {
        path: '/admin',
        component: () => import('src/views/admin/home'),
        children: [
            {
                path: '/',
                meta: {
                    title: '首页'
                },
                component: () => import('src/views/admin/index')
            },
            {
                path: 'articles',
                meta: {
                    title: '文章列表'
                },
                component: () => import('src/views/admin/article')
            },
            {
                path: 'articles/add',
                meta: {
                    title: '新建文章'
                },
                component: () => import('src/views/admin/addArticle')
            },
            {
                path: 'articles/edit',
                meta: {
                    title: '编辑文章'
                },
                component: () => import('src/views/admin/editArticle')
            },
            {
                path: 'mappings',
                meta: {
                    title: 'Mappings'
                },
                component: () => import('src/views/admin/mappings')
            },
            {
                path: 'props',
                meta: {
                    title: 'Props'
                },
                component: () => import('src/views/admin/props')
            },
            {
                path: 'files',
                meta: {
                    title: '文件管理'
                },
                component: () => import('src/views/admin/files')
            }
        ]
    },
    {
        path: '/',
        component: () => import('src/views/home'),
        children: [
            {
                path: '/',
                name: 'index',
                meta: {
                    title: '首页'
                },
                component: () => import('src/views/index')
            },
            {
                path: '/posts',
                name: 'posts',
                meta: {
                    title: '文章列表'
                },
                component: () => import('src/views/articles')
            },
            {
                path: '/articles/:id',
                name: 'article',
                meta: {
                    title: '文章'
                },
                component: () => import('src/views/article')
            },
            {
                path: '/music',
                name: 'music',
                meta: {
                    title: '音乐'
                },
                component: () => import('src/views/music')
            }, {
                path: '/feedback',
                name: 'feedback',
                meta: {
                    title: '反馈'
                },
                component: () => import('src/views/feedback')
            },{
                path: '/me',
                name: 'me',
                meta: {
                    title: 'Me'
                },
                component: () => import('src/views/me')
            },{
                path: '/eat',
                name: 'eat',
                meta: {
                    title: 'Eating'
                },
                component: () => import('src/views/eat')
            }, {
                path: '/switch',
                name: 'switch',
                meta: {
                    title: 'Switch Theme'
                },
                component: () => import('src/views/switch')
            },{
                path: '*',
                name: '404',
                meta: {
                    title: '404'
                },
                component: () => import('src/views/404')
            }
        ]
    }
];

const router = new Router({
    routes: routes,
  mode: 'history',
    scrollBehavior(to, from, savedPosition) {
        if (savedPosition) {
            return savedPosition
        } else {
            return {x: 0, y: 0}
        }
    }
});

router.beforeEach((to, from, next) => {
    next()
});

router.afterEach((to, from) => {
    if (to.meta.title) {
        document.title = to.meta.title + ' | Ian Blog'
    }
    window.scrollTo(0, 0);
});

export default router;
