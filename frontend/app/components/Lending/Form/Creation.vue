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
        }
    }

    const handleValidateForm = async () => {
        await createLending(form)
    }
</script>
<template>
    <form>
        <div>
            <h2>{{ steps[currentStep - 1]?.title }}</h2>
            <p>{{ steps[currentStep - 1]?.description }}</p>
        </div>
        <div v-if="currentStep === 1">
            <select id="user" name="user" v-model="form.borrowerId">
                <option value="" disabled>Choisissez un utilisateur</option>
                <option v-for="user in users" :key="user.id" :value="user.id">
                    {{ user.pseudo }}
                </option>
            </select>
        </div>
        <div v-if="currentStep === 2">
            <select id="object" name="object" v-model="form.objectId">
                <option value="" disabled>Choisissez un objet</option>
                <option v-for="object in objects" :key="object.id" :value="object.id">
                    {{ object.name }}
                </option>
            </select>
        </div>
        <div v-if="currentStep === 3">
            <input type="date" v-model="form.startDate"/>
            <input type="date" v-model="form.endDate"/>
        </div>
        <ButtonStepsForm 
            :nextStep="nextStep" 
            :previousStep="previousStep" 
            :validateForm="handleValidateForm"
            :finalStep="currentStep === steps.length" 
            :firstStep="currentStep === 1" 
            :isEntryValid="isEntryValid"
        />
    </form>
</template>

<style scoped>
</style>