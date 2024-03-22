import notifications from "@/components/notifications/index.vue"
import {boot} from 'quasar/wrappers'


export default boot(({app}) => {
  app.component("notifications", notifications)
})



