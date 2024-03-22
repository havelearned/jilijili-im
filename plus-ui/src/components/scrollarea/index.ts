const useScrollArea = () => {
  const thumbStyle = {
    right: '4px',
    borderRadius: '7px',
    backgroundColor: '#027be3',
    width: '4px',
    opacity: 0.75
  }
  const barStyle = {
    right: '2px',
    borderRadius: '9px',
    backgroundColor: '#027be3',
    width: '8px',
    opacity: 0.2
  }
  return {
    thumbStyle,
    barStyle

  }
}

export {
  useScrollArea
}
