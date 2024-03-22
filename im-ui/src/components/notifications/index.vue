<template>
    <div class="q-gutter-y-md global-dark" style="max-width: 600px">
        <q-card dark>
            <q-toolbar>
                <q-btn icon="chat" flat label="消息"/>
                <q-space/>
                <q-btn icon="clear_all" flat label="消息清空"/>
            </q-toolbar>
            <q-tabs
                v-model="tab"
                dense
                class=" global-dark"
                active-color="primary"
                indicator-color="purple"
                align="justify"
            >
                <q-tab name="为您精选" label="为您精选"/>
                <q-tab name="未读消息" label="未读消息"/>
                <q-tab name="提及" label="提及"/>
            </q-tabs>
            <q-tab-panels v-model="tab" animated class=" global-dark">
                <q-tab-panel name="为您精选">
                    <div class="text-h6">Mails</div>
                    Lorem ipsum dolor sit amet consectetur adipisicing elit.
                </q-tab-panel>

                <q-tab-panel name="未读消息">
                    <div class="text-h6">Alarms</div>
                    Lorem ipsum dolor sit amet consectetur adipisicing elit.
                </q-tab-panel>

                <q-tab-panel name="提及">
                    <div class="text-h6">Movies</div>
                    Lorem ipsum dolor sit amet consectetur adipisicing elit.
                </q-tab-panel>
            </q-tab-panels>
        </q-card>

    </div>
</template>

<script setup>

const tab = ref('为您精选')
</script>
