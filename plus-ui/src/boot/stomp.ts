// // import VueStomp from "vue-stomp";
// // import { boot } from 'quasar/wrappers'
//
//
// // export default boot(({ app }) => {
//
//
// //     app.use(VueStomp,"ws://localhost:8080/gs-guide-websocke")
//
// //   })
// @ts-ignore
import SockJS from 'sockjs-client/dist/sockjs.min.js';
import Stomp from "stompjs";

// const baseUrl: string = 'http://localhost:8080/im-websocket';
//
// const sockjsClient = new SockJS(baseUrl);
// const stompClient = Stomp.over(sockjsClient);
//
// export const initStomp = () => {
//   stompClient.connect({}, (frame) => {
//     console.log('Connected: ' + frame);
//   })
//
// };
