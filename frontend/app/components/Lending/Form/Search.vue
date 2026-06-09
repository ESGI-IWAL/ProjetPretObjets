<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import AutoComplete, { type IOption } from '~/components/AutoComplete.vue';
import useToaster from '~/composables/useToaster';
import type { ISearchLendingDto } from '~/dto/lending/search.dto';
import { getObjects } from '~/services/object';
import { getUsers } from '~/services/user';
import { fr } from 'date-fns/locale'

const emit = defineEmits(['search'])
const toaster = useToaster()
const form = reactive<ISearchLendingDto>({
    objectName: "",
    borrowerName: "",
    date: new Date().toISOString().split('T')[0] as string
})

const objectsIOption = ref<IOption[]| null>(null)
const usersIOption = ref<IOption[]|null>(null)
onMounted(async () => {
    try{
        const objects = await getObjects()
        const users = await getUsers()
        objectsIOption.value = objects.map(objet => {return { label: objet.name}} )
        usersIOption.value = users.map(user => {return {label: user.username}})
        form.date = new Date().toISOString().split('T')[0] as string

    } catch {
        objectsIOption.value= []
        usersIOption.value = []
        toaster.show("Erreur lors de la recherche des objets et des users", "error", 5000)
    }
})

const handleResetForm = () => {
    form.objectName= "";
    form.borrowerName= "";
    form.date = new Date().toISOString().split('T')[0] as string;

    emit("search", form)
}
</script>

<template>
    <form class="form-content" @submit.prevent="emit('search', form)">
        <div class="form-grid">
            <div class="form-field">
                <label class="form-label" for="objectName">Objet</label>
                <AutoComplete id="objectName" v-model:model-value="form.objectName" :options="objectsIOption ?? []" :placeholder="'Nom de l\'objet'"/>
            </div>

            <div class="form-field">
                <label class="form-label" for="borrowerName">Emprunteur</label>
                <AutoComplete id="borrowerName" v-model:model-value="form.borrowerName" :options="usersIOption ?? []" :placeholder="'Nom de l\'emprunteur'"/>
            </div>

            <div class="form-field">
                <label class="form-label" for="startAt"> Date </label>
                <input id="startAt" type="date" v-model="form.date" class="form-input"/>        <VueDatePicker
          v-model="form.date"
          :enable-time-picker="false"
          placeholder="Sélectionner une date"
          :locale="fr"
        />
            </div>
        </div>

        <div class="flex justify-end">
            <ButtonSearch :reset-form="() => handleResetForm"/>
        </div>
    </form>
</template>


<style>

</style>