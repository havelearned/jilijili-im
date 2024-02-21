import { EventBus } from 'quasar'
import { boot } from 'quasar/wrappers'

export const bus = new EventBus()

export default boot(({ app }) => {


  // 用于Options API
  app.config.globalProperties.$bus = bus

  // 用于Composition API
  app.provide('bus', bus)

})

/**
 * 更新聊天记录事件
 */
export const UPDATE_MSG_RECORD_MAP = "UPDATE_MSG_RECORD_MAP";
export const UPDATE_FRIEND_MSG = "UPDATE_FRIEND_MSG";
