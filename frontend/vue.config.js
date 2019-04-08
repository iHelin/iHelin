const path = require('path');

const resolve = dir => {
    return path.join(__dirname, dir)
}

module.exports = {
    chainWebpack: config => {
        config.resolve.alias
            .set('@', resolve('src')) // key,value自行定义，比如.set('@@', resolve('src/components'))
            .set('src', resolve('src'))
    },
    // 打包时不生成.map文件
    productionSourceMap: false,
    // 这里写你调用接口的基础路径，来解决跨域，如果设置了代理，那你本地开发环境的axios的baseUrl要写为 '' ，即空字符串
    devServer: {
        port: 9091,
        proxy: {
            '/ihelin/': {
                logLevel: 'debug',
                // target: 'http://localhost:8888/',
                target: 'http://112.74.203.130:8888/',
                changeOrigin: true,
                pathRewrite: {
                    '^/ihelin/': '/'
                }
            },
            '/hitokoto/': {
                logLevel: 'debug',
                target: 'https://api.imjad.cn/hitokoto/',
                changeOrigin: true,
                pathRewrite: {
                    '^/hitokoto/': '/'
                }
            },
            '/eat/': {
                logLevel: 'warn',
                target: 'https://dev.fluttercn.com/',
                changeOrigin: true,
                pathRewrite: {
                    '^/eat/': '/'
                }
            },
        },
    }
};
