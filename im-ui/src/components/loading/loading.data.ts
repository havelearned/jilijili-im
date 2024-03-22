

export const useLoading = (props: { message: string }) => {
  const $q = useQuasar()
  let timer: string | number | NodeJS.Timeout | undefined

  onBeforeUnmount(() => {
    if (timer !== void 0) {
      clearTimeout(timer)
      $q.loading.hide()
    }
  })

  function showLoading() {
    $q.loading.show({
      message: props.message,
    })

    // hiding in 10s
    timer = setTimeout(() => {
      $q.loading.hide()
      timer = void 0
    }, 10000)
  }

  function hideLoading() {
    $q.loading.hide()
  }

  return {
    showLoading,
    hideLoading
  }
}

