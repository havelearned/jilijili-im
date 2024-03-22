// 创建音频上下文
const audioContext = new AudioContext();

// 生成假的音频数据
function generateAudioData(): number[] {
  const data: number[] = [];
  for (let i = 0; i < 1024; i++) {
    data[i] = Math.floor(Math.random() * 256); // 生成随机数作为音频数据
  }
  return data;
}


// 将音频数据播放
export function playAudio(data: number[]) {
  // 创建一个空的音频缓冲区
  const buffer = audioContext.createBuffer(1, data.length, audioContext.sampleRate);

  // 将音频数据填充到缓冲区中
  const channelData = buffer.getChannelData(0);
  for (let i = 0; i < data.length; i++) {
    channelData[i] = data[i] / 128 - 1; // 转换音频数据范围为 [-1, 1]
  }

  // 创建音频源节点
  const source = audioContext.createBufferSource();
  source.buffer = buffer;

  // 连接音频源到音频目的地（扬声器）
  source.connect(audioContext.destination);

  // 播放音频
  source.start();
}
