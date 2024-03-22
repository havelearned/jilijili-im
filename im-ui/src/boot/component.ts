import notifications from "@/components/notifications/index.vue"
import {boot} from 'quasar/wrappers'
import msg from "@/views/msg/index.vue"

import GlobalAudio from '@/components/global/audio/index.vue';
import GlobalRecordAudio from '@/components/global/recordAudio/index.vue';
import dotOnline from '@/components/dot/online/index.vue';
import GlobalBottomMe from '@/components/global/bottomMe/index.vue';
import GlobalLeft from '@/components/global/left/index.vue';
import GlobaRight from '@/components/global/rgith/index.vue';
import ImagePreview from '@/components/ImagePreview/index.vue';
import ImageUpload from '@/components/ImageUpload/index.vue';
import Message from '@/components/message/index.vue';
import Notifications from '@/components/notifications/index.vue';
import PopupMenu from '@/components/PopupMenu/index.vue';
import SkeletonAvatar from '@/components/skeleton/SkeletonAvatar.vue';
import SkeletonList from '@/components/skeleton/SkeletonList.vue';
import Upload from '@/components/upload/index.vue';
import UserUserinfo from '@/components/user/userinfo/index.vue';
import UserUserList from '@/components/user/userlist/index.vue';
import ImSelect from '@/components/ImSelect.vue';
import CommonCardPale from "@/components/card/CommonCardPale.vue";
import Communicate from "@/components/communicate/Communicate.vue";

export default boot(({app}) => {
    app.component("notifications", notifications)
    app.component("msg", msg)
    app.component("localAudio", GlobalAudio)
    app.component("recordAudio", GlobalRecordAudio)
    app.component("dotOnline",dotOnline)
    app.component("GlobalBottomMe",GlobalBottomMe)
    app.component("GlobalLeft",GlobalLeft)
    app.component("GlobaRight",GlobaRight)
    app.component("ImagePreview",ImagePreview)
    app.component("ImageUpload",ImageUpload)
    app.component("Message",Message)
    app.component("Notifications",Notifications)
    app.component("PopupMenu",PopupMenu)
    app.component("SkeletonAvatar",SkeletonAvatar)
    app.component("SkeletonList",SkeletonList)
    app.component("Upload",Upload)
    app.component("UserUserinfo",UserUserinfo)
    app.component("UserUserList",UserUserList)
    app.component("ImSelect",ImSelect)
    app.component("CommonCardPale",CommonCardPale)
    app.component("Communicate",Communicate)



})



