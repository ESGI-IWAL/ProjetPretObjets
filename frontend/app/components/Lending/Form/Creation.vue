<script setup lang="ts">
    import type { IOption } from '~/components/AutoComplete.vue';
import type { ICreateLendingDto } from '~/dto/lending/create.dto';
    import { createLending } from '~/services/lending';
import { getObjects } from '~/services/object';
import { getUsers } from '~/services/user';
    import type { IObject } from '~/types/object';
    import type { IUser } from '~/types/user';

    interface IStep {
        id: number;
        title: string;
        description: string;
    }

    defineProps({
        users: {
            type: Array as () => IUser[],
            required: true
        },
        objects: {
            type: Array as () => IObject[],
            required: true
        }
    })

    const form = reactive<ICreateLendingDto>({
        borrowerId: 0,
        objectId: 0,
        startAt: new Date(),
        endAt: null
    })

    const namesOfSelected= reactive({
        borrowerName:"",
        objectName:""
    })
const objectsIOption = ref<IOption[]| null>(null)
const usersIOption = ref<IOption[]|null>(null)
onMounted(async () => {
    try{
        const objects = await getObjects()
        const users = await getUsers()
        objectsIOption.value = objects.map(objet => {return {id: objet.id, label: objet.name}} )
        usersIOption.value = users.map(user => {return {id: user.id, label: user.username}})

    } catch {
        objectsIOption.value= []
        usersIOption.value = []
    }
})
    const steps = ref<IStep[]>([
        {
            id: 1,
            title: "Sélection de l'utilisateur",
            description: "Choisissez l'utilisateur qui emprunte"
        },
        {id: 2,
            title: "Sélection de l'objet",
            description: "Choisissez l'objet à emprunter"
        },
        {
            id: 3,
            title: "Dates de prêt",
            description: "Indiquez les dates de début et de fin du prêt."
        }
    ])

    const currentStep = ref<number>(1)

    const isEntryValid = computed(() => {
        switch(currentStep.value) {
            case 1:
                return !!form.borrowerId
            case 2:
                return !!form.objectId
            case 3:
                return !!form.startAt && !!form.endAt && form.endAt >= form.startAt
            default:
                return true
        }
    })

    const nextStep = () => {
        if (currentStep.value < steps.value.length) {
            currentStep.value++
        }
    }

    const previousStep = () => {
        if (currentStep.value > 1) {
            currentStep.value--
        } else {
            resetForm()
            navigateTo('/lendings')
        }
    }

    const handleValidateForm = async () => {
        await createLending(form)
        resetForm()
        navigateTo('/lendings')
    }

    const resetForm = () => {
        form.borrowerId = 0
        form.objectId = 0
        form.startAt = new Date()
        form.endAt = null
        currentStep.value = 1
    }
</script>
<template>
  <form class="form-card form-content">
    <div class="form-header">
      <h2 class="form-title">{{ steps[currentStep - 1]?.title }}</h2>
      <p class="form-description">{{ steps[currentStep - 1]?.description }}</p>
    </div>

    <div>
      <div v-if="currentStep === 1" class="form-field">
        <label for="borrower" class="form-label">Utilisateur</label>
        <AutoComplete id="borrowerId" v-model:selectedId="form.borrowerId" v-model:modelValue="namesOfSelected.borrowerName" :options="usersIOption ?? []" :placeholder="'Nom de l\'utilisateur'"/>

      </div>

      <div v-if="currentStep === 2" class="form-field">
        <label for="object" class="form-label">Objet</label>
            <AutoComplete id="objectId" v-model:selectedId="form.objectId" v-model:modelValue="namesOfSelected.objectName" :options="objectsIOption ?? []" :placeholder="'Nom de l\'objet'"/>
      </div>

      <div v-if="currentStep === 3" class="form-grid">
        <div class="form-field">
          <label for="startAt" class="form-label">Date de début</label>
          <input id="startAt" type="date" v-model="form.startAt" class="form-input" />
        </div>

        <div class="form-field">
          <label for="endAt" class="form-label">Date de fin</label>
          <input id="endAt" type="date" v-model="form.endAt" class="form-input" />
        </div>
      </div>
       <div v-if="currentStep === 4" class="form-grid">
            
      </div>
    </div>

    <div class="form-actions">
      <ButtonStepsForm
          :nextStep="nextStep"
          :previousStep="previousStep"
          :validateForm="handleValidateForm"
          :finalStep="currentStep === steps.length"
          :firstStep="currentStep === 1"
          :isEntryValid="isEntryValid"
      />
    </div>
  </form>
</template>

<style scoped>
</style>