import {boot} from 'quasar/wrappers'

import VueCropper from 'vue-cropper';
import 'vue-cropper/dist/index.css'



export default boot(({app}) => {
  app.use(VueCropper)

})
