<script setup lang="ts">
import type { ISearchLendingDto } from '~/dto/lending/search.dto';
import { ELendingStatus } from '~/enums/lending/status.enum';

const emit = defineEmits(['search'])

const form = reactive<ISearchLendingDto>({
    objectName: "",
    borrowerName: "",
    startDate: undefined,
    endDate: undefined,
    status: ELendingStatus.ACTIVE
})

</script>

<template>
    <form @submit.prevent>
        <input v-model="form.objectName" placeholder="Nom de l'objet"/>
        <input v-model="form.borrowerName" placeholder="Nom de l'emprunteur"/>
        <input type="date" v-model="form.startDate"/>
        <input type="date" v-model="form.endDate"/>
        <select v-model="form.status">
            <option value="">Tous</option>
            <option value="ACTIVE">Actifs</option>
            <option value="COMPLETED">Terminés</option>
            <option value="CANCELED">Annulés</option>
        </select>
        <ButtonSearch @click="emit('search', form)"/>
    </form>
</template>


<style>

</style>