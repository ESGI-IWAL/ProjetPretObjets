<script setup lang="ts">
    import type { ICreateLendingDto } from '~/dto/lending/create.dto';
import { createLending } from '~/services/lending';
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
        borrowerId: '',
        objectId: '',
        startDate: new Date(),
        endDate: new Date()
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
        },
        {
            id: 4,
            title: "Confirmation",
            description: "Vérifiez les informations et confirmez le prêt."
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
                return !!form.startDate && !!form.endDate && form.endDate >= form.startDate
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
            navigateTo('/lending')
        }
    }

    const handleValidateForm = async () => {
        await createLending(form)
        resetForm()
        navigateTo('/lending')
    }

    const resetForm = () => {
        form.borrowerId = ''
        form.objectId = ''
        form.startDate = new Date()
        form.endDate = new Date()
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
        <label for="user" class="form-label">Utilisateur</label>
        <select id="user" v-model="form.borrowerId" class="form-select">
          <option value="" disabled>Choisissez un utilisateur</option>
          <option v-for="user in users" :key="user.id" :value="user.id">
            {{ user.pseudo }}
          </option>
        </select>
      </div>

      <div v-if="currentStep === 2" class="form-field">
        <label for="object" class="form-label">Objet</label>
        <select id="object" v-model="form.objectId" class="form-select">
          <option value="" disabled>Choisissez un objet</option>
          <option v-for="object in objects" :key="object.id" :value="object.id">
            {{ object.name }}
          </option>
        </select>
      </div>

      <div v-if="currentStep === 3" class="form-grid">
        <div class="form-field">
          <label for="startDate" class="form-label">Date de début</label>
          <input id="startDate" type="date" v-model="form.startDate" class="form-input" />
        </div>

        <div class="form-field">
          <label for="endDate" class="form-label">Date de fin</label>
          <input id="endDate" type="date" v-model="form.endDate" class="form-input" />
        </div>
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