const path = require('path');

const resolve = dir => {
    return path.join(__dirname, dir)
};

module.exports = {
    chainWebpack: config => {
        config.resolve.alias
            .set('@', resolve('src')) // key,value自行定义，比如.set('@@', resolve('src/components'))
            .set('src', resolve('src'));
        // svg loader
        const svgRule = config.module.rule('svg')
        svgRule.uses.clear() // 清除已有的loader, 如果不这样做会添加在此loader之后
        svgRule // 添加svg新的loader处理
            .test(/\.svg$/)
            .use('svg-sprite-loader')
            .loader('svg-sprite-loader')
    },
    // 打包时不生成.map文件
    productionSourceMap: false,
    devServer: {
        port: 9999,
        proxy: {
            'api': {
                logLevel: 'debug',
                target: 'http://localhost:8080',
                changeOrigin: true,
                pathRewrite: {
                    '^/api': ''
                }
            }
        }
    }
};
