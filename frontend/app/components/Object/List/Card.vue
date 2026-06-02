<script setup lang="ts">
import type { IObjectInfos } from '~/pages/objects/index.vue';
import type { IObject } from '~/types/object';

const route = useRouter()
defineProps<{
    object: IObject
    objectInfo?: IObjectInfos
}>()

</script>

<template>
    <div @click="route.push(`${object.id}`)">
        <img :src="object?.images?.[0] ?? '/objectImage.png'" alt="Objet"/>
        <p> object.name </p>
        <p v-if="objectInfo?.nextLendingDate"> Disponible jusqu'au {{objectInfo.nextLendingDate }}</p>
        <p v-else-if="objectInfo?.endCurrentLending"> Fin du prêt le : {{ objectInfo.endCurrentLending }}</p>
        <p v-else> Disponible </p>
    </div>

</template>

<style scoped>

</style>