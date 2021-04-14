import router from '@/router';
import store from '@/store';


export function formatDate(date) {
    const y = date.getFullYear();
    let m = date.getMonth() + 1;
    m = m < 10 ? '0' + m : m;
    let d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    return y + '-' + m + '-' + d;
}

/**
 * 获取uuid
 */
export function getUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
        return (c === 'x' ? (Math.random() * 16 | 0) : ('r&0x3' | '0x8')).toString(16)
    })
}

/**
 * 是否有权限
 * @param {*} key
 */
export function isAuth(key) {
    return JSON.parse(sessionStorage.getItem('permissions') || '[]').indexOf(key) !== -1 || false;
    // return true;
}

/**
 * 树形数据转换
 * @param {*} data
 * @param {*} id
 * @param {*} pid
 */
export function treeDataTranslate(data, id = 'id', pid = 'parentId') {
    var res = []
    var temp = {}
    for (var i = 0; i < data.length; i++) {
        temp[data[i][id]] = data[i]
    }
    for (var k = 0; k < data.length; k++) {
        if (temp[data[k][pid]] && data[k][id] !== data[k][pid]) {
            if (!temp[data[k][pid]]['children']) {
                temp[data[k][pid]]['children'] = []
            }
            if (!temp[data[k][pid]]['_level']) {
                temp[data[k][pid]]['_level'] = 1
            }
            data[k]['_level'] = temp[data[k][pid]]._level + 1
            temp[data[k][pid]]['children'].push(data[k])
        } else {
            res.push(data[k])
        }
    }
    return res
}

/**
 * 清除登录信息
 */
export function clearLoginInfo() {
    localStorage.removeItem('token');
    store.commit('resetStore')
    router.options.isAddDynamicMenuRoutes = false
}

export function formatStatus(status) {
    if (status === 100) {
        return '待付款';
    } else if (status === 200) {
        return '待发货';
    } else if (status === 300) {
        return '待收货';
    } else if (status === 400) {
        return '待评价';
    } else if (status === 500) {
        return '已取消';
    } else if (status === 600) {
        return '售后中';
    } else if (status === 1000) {
        return '交易成功';
    }
    return '未知';
}

export function formatPayType(type) {
    if (type === 'wx') {
        return '微信支付';
    }
    return '';
}
