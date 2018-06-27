import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);

const routes = [
    {
        path: '/login',
        meta: {
            title: '登录'
        },
        component: () => import('src/pages/login')
    },
    {
        path: '/admin',
        component: () => import('src/pages/admin/home'),
        children: [
            {
                path: '/',
                meta: {
                    title: '首页'
                },
                component: () => import('src/pages/admin/index')
            },
            {
                path: 'articles',
                meta: {
                    title: '文章列表'
                },
                component: () => import('src/pages/admin/article')
            },
            {
                path: 'articles/add',
                meta: {
                    title: '新建文章'
                },
                component: () => import('src/pages/admin/addArticle')
            },
            {
                path: 'articles/edit',
                meta: {
                    title: '编辑文章'
                },
                component: () => import('src/pages/admin/editArticle')
            },
            {
                path: 'mappings',
                meta: {
                    title: 'Mappings'
                },
                component: () => import('src/pages/admin/mappings')
            },
            {
                path: 'props',
                meta: {
                    title: 'Props'
                },
                component: () => import('src/pages/admin/props')
            },
            {
                path: 'files',
                meta: {
                    title: '文件管理'
                },
                component: () => import('src/pages/admin/files')
            }
        ]
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
                path: '/feedback',
                name: 'feedback',
                meta: {
                    title: '反馈'
                },
                component: () => import('src/pages/feedback')
            },{
                path: '/test',
                name: 'test',
                meta: {
                    title: 'test'
                },
                component: () => import('src/pages/test')
            },{
                path: '/eat',
                name: 'eat',
                meta: {
                    title: 'Eating'
                },
                component: () => import('src/pages/eat')
            }, {
                path: '*',
                name: '404',
                meta: {
                    title: '404'
                },
                component: () => import('src/pages/404')
            }
        ]
    }
];

const router = new Router({
    routes: routes,
    // mode: 'history',
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
        document.title = to.meta.title + ' | Ian He'
    }
    window.scrollTo(0, 0);
});

export default router;
