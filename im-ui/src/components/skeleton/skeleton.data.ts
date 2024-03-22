

const useSkeleton = () => {
  const loading = ref(true);
  const count = ref(10);
  const hideLoading = () => {
    loading.value = false;


  }
  const showLoading=()=>{
    loading.value = true;
  }
  return {
    loading,
    count,
    hideLoading,
    showLoading

  }
}

export {
  useSkeleton
}
