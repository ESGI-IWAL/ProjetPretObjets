<script setup lang="ts">
import type { ILending } from '~/types/lending';

defineProps<{
  lending: ILending,
}>()

const showDetails = ref<boolean>(false)

const handleClick = () => {
    showDetails.value = !showDetails.value
}
</script>

<template>
    <div>
        <div>
            <img :src="lending.borrower.avatar" alt="Avatar de l'emprunteur" />
        </div>
        <div>
            <h3>{{ lending.object.name }}</h3>
            <p>jusqu'au {{ lending.endDate }}</p>
        </div>
        <div>
            <img :src="lending.object.image" alt="Objet prêté" />
        </div>
        <div @click="handleClick">
             <!-- Icône de flèche vers le bas -->
        </div>
    </div>
    <div v-if="showDetails">
        <LendingDetailledCard :lending="lending" />
    </div>
</template>


<style>

</style>