<script setup lang="ts">
import type { IObjectInfos } from '~/pages/objects/index.vue';
import type { IObject } from '~/types/object';
import formatDateLong from '~/utils/date';

const route = useRouter()
defineProps<{
    object: IObject
    objectInfo?: IObjectInfos
}>()

</script>

<template>
    <div @click="route.push(`/objects/${object.id}`)">
        <img :src="object?.images?.[0] ?? '/objectImage.png'" alt="Objet"/>
        <p>{{ object.name }}</p>
        <p v-if="objectInfo?.nextLendingDate"> Disponible jusqu'au {{ formatDateLong(objectInfo.nextLendingDate) }}</p>
        <p v-else-if="objectInfo?.currentLendingStart"> Prêt en cours depuis le {{ formatDateLong(objectInfo.currentLendingStart) }}</p>
        <p v-else-if="objectInfo?.endCurrentLending"> Fin du prêt le : {{ formatDateLong(objectInfo?.endCurrentLending) }}</p>
        <p v-else> Disponible </p>
    </div>

</template>

<style scoped>

</style>