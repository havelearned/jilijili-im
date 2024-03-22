import {ref} from "vue";

export interface Rtc {
  rtcPeerConnection: RTCPeerConnection,

  localStream: MediaStream,
  remoteStream: MediaStream,

  audioApp: HTMLAudioElement,
  audioRemoteApp: HTMLAudioElement,
  videoApp: HTMLVideoElement,
  videoRemoteApp: HTMLVideoElement,
}

// @ts-ignore
const rtc = ref<Rtc>({localStream: undefined, remoteStream: undefined, rtcPeerConnection: undefined})

// 初始化RTCPeerConnection实例
export function initializePeerConnection( audioApp: HTMLAudioElement,
                                          audioRemoteApp: HTMLAudioElement,
                                          videoApp: HTMLVideoElement,
                                          videoRemoteApp: HTMLVideoElement): void {
  // 创建RTCPeerConnection实例
  const configuration: RTCConfiguration = {
    'iceServers': [
      {'urls': 'stun:stun.example.org'},
      {'urls': 'turn:turn.example.org', 'credential': 'password', 'username': 'user'}
    ]
  };

  // 创建RTC实例
  rtc.value.rtcPeerConnection = new RTCPeerConnection(configuration);

  rtc.value.audioApp = audioApp;
  rtc.value.audioRemoteApp = audioRemoteApp;
  rtc.value.videoApp = videoApp;
  rtc.value.videoRemoteApp = videoRemoteApp;
  rtc.value.remoteStream = new MediaStream();

  console.log(rtc.value.audioApp)

  // startLocalStream().then(r => {
  //   // 添加本地流
  //   rtc.value.localStream.getTracks().forEach((track) => {
  //     rtc.value.rtcPeerConnection.addTrack(track, rtc.value.localStream);
  //   });
  // })



  // 处理ICE连接状态
  rtc.value.rtcPeerConnection.oniceconnectionstatechange = () => {
    console.log('ICE 连接状态为:', rtc.value.rtcPeerConnection.iceConnectionState);
  };

  // 处理远程流
  rtc.value.rtcPeerConnection.ontrack = (event) => {
    rtc.value.remoteStream.addTrack(event.track);
  };

}

// 开始采集本地音视频流
export async function startLocalStream(): Promise<void> {
  try {
    // 使用navigator.mediaDevices.getUserMedia获取本地音视频流
    rtc.value.localStream = await navigator.mediaDevices.getUserMedia({audio: true, video: false});
    console.log("获取音视频")
  } catch (error) {
    console.log('无法启动本地流:', error);
  }
}

// 开启本地音频流
export async function statLocalStreamForAudio() {
  try {
    rtc.value.localStream = await navigator.mediaDevices.getUserMedia({audio: true, video: false});
    rtc.value.audioApp.srcObject = rtc.value.localStream;
    // 播放音频（可选）
    rtc.value.audioApp.play().catch((error) => {
      console.error('无法播放音频:', error);
    });
    console.log("开启本地音频流:", rtc.value.localStream);
  } catch (error) {
    console.error('无法启动本地流:', error);
  }
}

// 关闭本地音频流
export async function closeLocalStreamForAudio() {
  try {
    // 获取已存在的本地音频流
    const localStream = rtc.value.localStream;
    // 检查是否存在本地音频流
    if (localStream) {
      // 停止本地音频流，释放资源
      const tracks = localStream.getAudioTracks();
      tracks.forEach(track => track.stop());

      // @ts-ignore
      // 设置 rtc.value.localStream 为 null，表示没有本地音频流
      rtc.value.localStream = null;

      console.log("关闭本地音频流成功");
    } else {
      console.log("没有找到本地音频流");
    }
  } catch (error) {
    console.error('无法关闭本地音频流:', error);
  }
}

// 创建offer并发送给远端
export async function createOffer(): Promise<void> {
  try {
    const offer = await rtc.value.rtcPeerConnection.createOffer();
    await rtc.value.rtcPeerConnection.setLocalDescription(offer);
    // TODO 这里将offer发送给远端，例如通过WebSocket发送
    console.log('Offer sent to server:', offer);
  } catch (error) {
    console.error('Failed to create offer:', error);
  }
}


// 处理远端传回的answer
export async function handleAnswer(answer: RTCSessionDescriptionInit): Promise<void> {
  try {
    await rtc.value.rtcPeerConnection.setRemoteDescription(answer);
    console.log('Remote description set');
  } catch (error) {
    console.error('Failed to set remote description:', error);
  }
}


// 启动整个过程
export async function startCall(): Promise<void> {
  // await startLocalStream();
  // // initializePeerConnection();
  // await createOffer();
}






