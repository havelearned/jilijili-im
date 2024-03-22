export default {


    /**
     * 浏览器获取设备信息
     */
    getWebInfo() {
        let info = {
            // 浏览器类型
            browser: window.navigator.userAgent,
            // 浏览器语言
            language: window.navigator.language,
            // 浏览器屏幕宽度
            screenWidth: window.screen.width,
            // 浏览器屏幕高度
            screenHeight: window.screen.height,
            // 浏览器窗口宽度
            windowWidth: window.innerWidth,
            // 浏览器窗口高度
            windowHeight: window.innerHeight,
            // 浏览器窗口可见宽度
            visibleWidth: window.outerWidth,
            // 浏览器窗口可见高度
            visibleHeight: window.outerHeight,
            // 浏览器窗口左上角到屏幕可见区域的水平距离
            screenLeft: window.screenLeft,
            // 浏览器窗口左上角到屏幕可见区域的垂直距离
            screenTop: window.screenTop,
            // 浏览器窗口的大小
            windowSize: `${window.innerWidth} x ${window.innerHeight}`,
            // 浏览器窗口的可见大小
            visibleSize: `${window.outerWidth} x ${window.outerHeight}`,
            // 浏览器窗口的左上角坐标
            windowPosition: `${window.screenLeft} x ${window.screenTop}`,
        }
        console.log("浏览器获取设备信息",info)
        return info;
    },

    /**
     * 获取浏览器信息
     */
    getBrowserInfo() {
        let browser = window.navigator.userAgent.toLowerCase();
        let browserInfo = {
            // 浏览器类型
            browser: browser,
            // 浏览器语言
            language: window.navigator.language,
            // 浏览器屏幕宽度
            screenWidth: window.screen.width,
            // 浏览器屏幕高度
            screenHeight: window.screen.height,
            // 浏览器窗口宽度
            windowWidth: window.innerWidth,
            // 浏览器窗口高度
        }
        console.log("获取浏览器信息",browserInfo)
        return browserInfo;
    },

    /**
     * 获取音频权限
     */
    getAudioPermission() {
        return new Promise((resolve, reject) => {
            navigator.mediaDevices.getUserMedia({ audio: true }).then((stream) => {
                resolve(stream)
            }).catch((error) => {
                reject(error)
            })
        })
    },

    /**
     * 获取视频权限
     */
    getVideoPermission() {
        return new Promise((resolve, reject) => {
            navigator.mediaDevices.getUserMedia({ video: true }).then((stream) => {
                resolve(stream)
            }).catch((error) => {
                reject(error)
            })
        })
    },

}

