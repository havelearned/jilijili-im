import {defineStore} from "pinia";
import {ref} from "vue";


export const useWs = defineStore('wsStore', () => {

  // @ts-ignore
  let websocket = ref<WebSocket>(null);

  // @ts-ignore
  const initWs = (url: string): Ref<UnwrapRef<WebSocket>> => {
    if (import.meta.env.VITE_APP_WEBSOCKET === 'false') {
      return null;
    }
    websocket.value = new WebSocket(`${url}`);
    return  websocket;
  }


  return {
    initWs,
    websocket,
  }
})
