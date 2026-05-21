<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import AutoComplete from '~/components/AutoComplete.vue';
import type { ISearchLendingDto } from '~/dto/lending/search.dto';
import { ELendingStatus } from '~/enums/lending/status.enum';
import { getObjects } from '~/services/object';

const emit = defineEmits(['search'])

const form = reactive<ISearchLendingDto>({
    objectName: "",
    borrowerName: "",
    startAt: null,
    endAt: null,
    status: ELendingStatus.IN_PROGRESS
})

const objectNames = ref<string[]| null>(null)
onMounted(async () => {
    try{
        const objects = await getObjects()
        objectNames.value = objects.map(objet => objet.name)
    } catch {
        objectNames.value= []
    }
})

</script>

<template>
    <form class="form-content" @submit.prevent="emit('search', form)">
        <div class="form-grid">
            <div class="form-field">
                <label class="form-label" for="objectName">Objet</label>
                <AutoComplete id="objectName" v-model="form.objectName" :options="objectNames ?? []" :placeholder="'Nom de l\'objet'"/>
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
                    <option value="PENDING">En attente</option>
                    <option value="REFUSED">Refusés</option>
                    <option value="VALIDATED">Validés</option>
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