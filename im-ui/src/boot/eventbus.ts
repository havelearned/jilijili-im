import {EventBus} from 'quasar'
import {boot} from 'quasar/wrappers'

export const bus = new EventBus()

export default boot(({app}) => {


    // 用于Options API
    app.config.globalProperties.$bus = bus

    // 用于Composition API
    app.provide('bus', bus)

})


// 获取好友聊天记录
export const UPDATE_FRIEND_MSG = "UPDATE_FRIEND_MSG";

// 清理消息点
export const CLEAR_MSG_DOT = "CLEAR_MSG_DOT";

export const UPDATE_FRIEND_MSG_DOT = "UPDATE_FRIEND_MSG_DOT";

// 清空聊天记录
export const DELETE_CLEAR_RECORD = "DELETE_CLEAR_RECORD";
