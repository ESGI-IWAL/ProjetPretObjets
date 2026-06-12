<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import AutoComplete, { type IOption } from '~/components/AutoComplete.vue';
import useToaster from '~/composables/useToaster';
import type { ISearchLendingDto } from '~/dto/lending/search.dto';
import { getObjectsOfConnectedUser } from '~/services/object';
import { getUsersOfConnectedUser } from '~/services/user';
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
        const objects = await getObjectsOfConnectedUser()
        const users = await getUsersOfConnectedUser()
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
/* ── Labels & inputs ── */
.form-label { color: var(--color-title) !important; }

.form-input {
  background-color: #FFFFFF !important;
  color: var(--color-text) !important;
  border-color: #c9c0ae !important;
  border-radius: 8px !important;
}
.form-input:focus {
  border-color: var(--color-primary) !important;
  box-shadow: 0 0 0 3px rgba(98, 148, 96, 0.2) !important;
  outline: none !important;
}

/* ── VueDatePicker ── */
.dp__main {
  --dp-background-color: var(--color-surface);
  --dp-text-color: var(--color-text);
  --dp-hover-color: var(--color-primary);
  --dp-hover-text-color: var(--color-background);
  --dp-primary-color: var(--color-primary);
  --dp-primary-text-color: var(--color-background);
  --dp-border-color: #c9c0ae;
  --dp-border-color-hover: var(--color-primary);
  --dp-menu-border-color: #c9c0ae;
  --dp-border-radius: 12px;
  --dp-font-family: var(--font-family-sans);
  --dp-font-size: 0.875rem;
}
.dp__input {
  background-color: var(--color-surface) !important;
  color: var(--color-text) !important;
  border-color: #c9c0ae !important;
  border-radius: 8px !important;
  padding: 10px 16px !important;
}
.dp__input:focus {
  border-color: var(--color-primary) !important;
  box-shadow: 0 0 0 3px rgba(98, 148, 96, 0.2) !important;
  outline: none !important;
}
.dp__input_icon { color: var(--color-primary) !important; }
.dp__menu {
  background-color: var(--color-surface) !important;
  border-color: #c9c0ae !important;
  border-radius: var(--border-radius) !important;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1) !important;
}
.dp__calendar_header_item { color: var(--color-title) !important; font-weight: 600; }
.dp__active_date {
  background-color: var(--color-primary) !important;
  color: var(--color-background) !important;
  border-radius: 8px !important;
}
.dp__cell_inner:hover {
  background-color: rgba(98, 148, 96, 0.15) !important;
  color: var(--color-primary) !important;
  border-radius: 8px !important;
}
.dp__today { border-color: var(--color-accent) !important; color: var(--color-accent) !important; font-weight: 700; }
.dp__nav_icon { color: var(--color-primary) !important; }
.dp__nav_btn:hover { background-color: rgba(98, 148, 96, 0.12) !important; border-radius: 8px !important; }
.dp__overlay { background-color: var(--color-surface) !important; }
.dp__overlay_cell_active {
  background-color: var(--color-primary) !important;
  color: var(--color-background) !important;
  border-radius: 8px !important;
}
</style>