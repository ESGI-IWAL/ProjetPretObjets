<script setup lang="ts">
import type { ISearchLendingDto } from '~/dto/lending/search.dto';
import { ELendingStatus } from '~/enums/lending/status.enum';

const emit = defineEmits(['search'])

const form = reactive<ISearchLendingDto>({
    objectName: "",
    borrowerName: "",
    startAt: undefined,
    endAt: undefined,
    status: ELendingStatus.IN_PROGRESS
})

</script>

<template>
    <form class="form-content" @submit.prevent="emit('search', form)">
        <div class="form-grid">
            <div class="form-field">
                <label class="form-label" for="objectName">Objet</label>
                <input id="objectName" v-model="form.objectName" class="form-input" placeholder="Nom de l'objet"/>
            </div>

            <div class="form-field">
                <label class="form-label" for="borrowerName">Emprunteur</label>
                <input id="borrowerName" v-model="form.borrowerName" class="form-input" placeholder="Nom de l'emprunteur"/>
            </div>

            <div class="form-field">
                <label class="form-label" for="startAt">Début</label>
                <input id="startAt" type="date" v-model="form.startAt" class="form-input"/>
            </div>

            <div class="form-field">
                <label class="form-label" for="endAt">Fin</label>
                <input id="endAt" type="date" v-model="form.endAt" class="form-input"/>
            </div>

            <div class="form-field sm:col-span-2">
                <label class="form-label" for="status">Statut</label>
                <select id="status" v-model="form.status" class="form-select">
                    <option value="">Tous</option>
                    <option value="ACTIVE">Actifs</option>
                    <option value="COMPLETED">Terminés</option>
                    <option value="CANCELED">Annulés</option>
                </select>
            </div>
        </div>

        <div class="flex justify-end">
            <ButtonSearch />
        </div>
    </form>
</template>


<style>

</style>