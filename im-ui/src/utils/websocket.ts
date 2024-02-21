import { getToken } from '@/utils/auth';
import { Notify } from 'quasar';
import {bus} from '@/boot/eventbus'

let socketUrl = '';
let websocket: WebSocket | null = null;
let heartTime: NodeJS.Timeout | null = null;
let socketHeart = 0;
const HeartTimeOut = 60000; // 10 seconds
let socketError = 0;



// Initialize socket
export const initWebSocket = (url: string): WebSocket | null => {
  if (import.meta.env.VITE_APP_WEBSOCKET === 'false') {
    return null;
  }

  socketUrl = url;
  // Initialize WebSocket
  // websocket = new WebSocket(`${url}?Authorization=Bearer ${getToken()}&clientid=${import.meta.env.VITE_APP_CLIENT_ID}`);

  websocket = new WebSocket(`${url}`);


  // Set up event handlers
  websocket.onopen = websocketonopen;
  websocket.onerror = websocketonerror;
  websocket.onclose = websocketclose;
  websocket.onmessage = websocketonmessage;

  // Start sending heartbeat
  sendSocketHeart();

  return websocket;
};

// WebSocket connection successful
export const websocketonopen = (): void => {
  console.log('WebSocket connection successful');
  resetHeart();
};

// WebSocket connection error
export const websocketonerror = (e: Event): void => {
  console.error('WebSocket connection error', e);
};

// WebSocket connection closed
export const websocketclose = (e: CloseEvent): void => {
  console.log('WebSocket connection closed', e);
  Notify.create({
    caption: 'Message',
    message: 'websocket 连接关闭=>'+e,
    type: 'positive',
    timeout: 3000,
  });
};

// Reset heartbeat
export const resetHeart = (): void => {
  socketHeart = 0;
  socketError = 0;
  clearInterval(heartTime!);
  sendSocketHeart();
};

// Send heartbeat
export const sendSocketHeart = (): void => {
  heartTime = setInterval(() => {
    if (websocket && websocket.readyState === WebSocket.OPEN) {
      websocket.send(JSON.stringify({ type: 'ping' }));
      socketHeart++;
    } else {
      reconnect();
    }
  }, HeartTimeOut);
};

// Reconnect WebSocket
export const reconnect = (): void => {
  if (socketError <= 2) {
    clearInterval(heartTime!);
    initWebSocket(socketUrl);
    socketError++;
    console.log('WebSocket reconnect', socketError);
  } else {
    console.log('Retry attempts exhausted');
    clearInterval(heartTime!);
  }
};

/**
 * Send data through WebSocket
 * @param data 默认转json
 */
export const sendMsg = (data: any): void => {
  if (websocket && websocket.readyState === WebSocket.OPEN) {
    let d = JSON.stringify(data);
    websocket.send(d);
  }
};

// Receive data from WebSocket
export const websocketonmessage = (e: MessageEvent): any => {
  if (e.data.indexOf('heartbeat') > 0) {
    resetHeart();
  }
  if (e.data.indexOf('ping') > 0) {
    return;
  }
  Notify.create({
    caption: 'Message',
    message: e.data,
    type: 'positive',
    timeout: 3000,
  });
  // 根据消息的类型, vue发送bus事件
  bus.emit('test1',e.data)


  return e.data;
};
