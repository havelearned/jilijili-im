import {Dialog, QSpinnerGears} from 'quasar'
import {boot} from "quasar/wrappers";


export const confirm = (title: string, msg: any, func: any) => {
    return Dialog.create({
        title: title,
        message: msg,
        cancel: true,
        persistent: true,
        color: "dark",
    }).onOk(() => {
        func()

    }).onCancel(() => {

    }).onDismiss(() => {

    })
}

export const progress = (title: string, msg: any) => {
    const dialog = Dialog.create({
        title: title,
        dark: true,
        message: msg + '0%',
        progress: {
            spinner: QSpinnerGears,
            color: 'amber'
        },
        persistent: true,
        ok: false
    })
    return dialog;
}


export default boot(({app}) => {
    // for use inside Vue files (Options API) through this.$axios and this.$api

    app.config.globalProperties.$log_confirm = confirm;
    app.config.globalProperties.$log_progress = progress;


});
