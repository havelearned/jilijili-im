<template>
    <audio ref="localMsgAudioRef" controls style="display: none">
        <source src="@/assets/audio/def_01.wav" type="audio/wav">
    </audio>
</template>
<script lang="ts" setup>


const localMsgAudioRef = ref()

function play(type: any) {
    if (localMsgAudioRef.value) {
        localMsgAudioRef.value.play();
    }
}

function pause() {
    if (localMsgAudioRef.value) {
        localMsgAudioRef.value.pause();
    }
}

defineExpose({ play,pause})

</script>


<style scoped>

</style>
