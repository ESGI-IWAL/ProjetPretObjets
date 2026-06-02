<script setup lang="ts">

import type { IObject } from '~/types/object';

const props = defineProps<{
    object: IObject
    selected: boolean
}>()

const emit = defineEmits(["handleSelection"])

const handleSelection = () => {
    emit('handleSelection', props.object.id)
}

</script> 

<template>
    <div @click="handleSelection" :class="props.selected ? `` : ``">
        <img :src="object?.images?.[0] ?? '/objectImage.png'" alt="Objet"/>
        <p> object.name </p>
    </div>
</template>

<style scoped>

</style>