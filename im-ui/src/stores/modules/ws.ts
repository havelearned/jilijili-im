import {defineStore} from "pinia";



export const useWs = defineStore('wsStore', () => {

    // @ts-ignore
    let websocket = ref<WebSocket>(null);

    // @ts-ignore
    const initWs = (url: string): Ref<UnwrapRef<WebSocket>> | null => {
        if (import.meta.env.VITE_APP_WEBSOCKET === 'false') {
            return null;
        }
        websocket.value = new WebSocket(`${url}`);
        return websocket;
    }


    /**
     * 发送消息
     * @param msg
     */
    const sendMsg = (msg: string) => {
        if (websocket.value) {
            websocket.value.send(JSON.stringify(msg));
        }
    }
    return {
        sendMsg,
        initWs,
        websocket,
    }
})
