<script setup lang="ts">
import type { IOption } from "~/components/AutoComplete.vue";
import useToaster from "~/composables/useToaster";
import type { ICreateLendingDto } from "~/dto/lending/create.dto";
import type { ISearchObjectDto } from "~/dto/object/search.dto";
import { createLending, searchLendingsOnDateByIdObject } from "~/services/lending";
import type { IObject } from "~/types/object";
import type { IUser } from "~/types/user";
import type { ISearchLendingPeriodDto } from "~/dto/lending/search.dto";
import {VueDatePicker} from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'

interface IStep {
  id: number;
  title: string;
  description: string;
}

const props = defineProps({
  users: {
    type: Array as () => IUser[],
    required: true,
  },
  objects: {
    type: Array as () => IObject[] | null,
  },
});

const emit = defineEmits([ "handleSearchObjects"]);

const toaster = useToaster();

const form = reactive<ICreateLendingDto>({
  borrowerId: 0,
  objectId: 0,
  startAt: null,
  endAt: null,
});

const namesOfSelected = reactive({
  borrowerName: "",
  objectName: "",
});
const blockedDates = ref<ISearchLendingPeriodDto[]>([])
const usersIOption = ref<IOption[] | null>(null);
const currentStep = ref<number>(1);
const steps = ref<IStep[]>([
  {
    id: 1,
    title: "Sélection de l'utilisateur",
    description: "Choisissez l'utilisateur qui emprunte",
  },
  {
    id: 2,
    title: "Sélection de l'objet",
    description: "Choisissez l'objet à emprunter",
  },
  {
    id: 3,
    title: "Dates de prêt",
    description: "Indiquez les dates de début et de fin du prêt.",
  },
]);

onMounted(async () => {
  try {
    usersIOption.value = props.users.map((user) => {
      return { id: user.id, label: user.username };
    });
  } catch {
    usersIOption.value = [];
  }
});

const handleSearchPeriodOnObjects = async (id: number) => {
    try{
        blockedDates.value = await searchLendingsOnDateByIdObject(id)
    }
    catch {
        blockedDates.value= []
        toaster.show("Erreur lors de la récupération des objects correspondants à cette date", "error", 5000)
    }
}

const handleSearchObjects = async (
  dto: 
    ISearchObjectDto,
) => {
  emit("handleSearchObjects", dto);
};

const handleSelectionObject = (id: number) => {
  form.objectId = id;
};
const endDateVerification = (): boolean => {
  if (form.endAt &&  form.startAt) return form.startAt >= form.endAt;
  else return true;
};

const isEntryValid = computed(() => {
  switch (currentStep.value) {
    case 1:
      return !!form.borrowerId;
    case 2:
      return !!form.objectId;
    case 3:
      return !!form.startAt && endDateVerification();
    default:
      return true;
  }
});

const nextStep = () => {

  if (currentStep.value == 2) {
    console.log("piou")
    handleSearchPeriodOnObjects(form.objectId);
  }
  if (currentStep.value < steps.value.length) {
    currentStep.value++;
  }
};

const previousStep = () => {
  if (currentStep.value > 1) {
    currentStep.value--;
  } else {
    resetForm();
    navigateTo("/lendings");
  }
};

const handleValidateForm = async () => {
  try {
    await createLending(form);
    resetForm();
    navigateTo("/lendings");
    toaster.show("Le prêt a bien été créé");
  } catch {
    resetForm();
    navigateTo("/lendings");
    toaster.show("Erreur lors de la création de votre prêt", "error", 5000);
  }
};

const resetForm = () => {
  form.borrowerId = 0;
  form.objectId = 0;
  form.startAt = new Date();
  form.endAt = null;
  currentStep.value = 1;
};

function isDisabled(date: Date ) {
  return blockedDates.value.some(period => {
    const start = new Date(period.startedAt)
    const end = new Date(period.endedAt)
    end.setHours(23, 59, 59) // inclure le dernier jour
    return date >= start && date <= end
  })
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
        <AutoComplete
          id="borrowerId"
          v-model:selectedId="form.borrowerId"
          v-model:modelValue="namesOfSelected.borrowerName"
          :options="usersIOption ?? []"
          :placeholder="'Nom de l\'utilisateur'"
        />
      </div>
      
      <div v-if="currentStep === 2 && objects" class="form-field">
        <div> 
          <ObjectFormSearch @handleSearch="handleSearchObjects" :optionDisponibilityDate="false"/>
        </div>
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 mt-4">
          <ObjectListCardSelection
            v-for="object in objects"
            :key="object.id"
            :object="object"
            @handleSelection="handleSelectionObject"
            :selected="form.objectId === object.id"
          />
        </div>
      </div>
    </div>
    <div v-if="currentStep === 3" class="form-grid">
       <div class="form-field">
    <label class="form-label">Date de début</label>
    <VueDatePicker
      v-model="form.startAt"
      :disabled-dates="isDisabled"
      :enable-time-picker="false"
      format="dd/MM/yyyy"
      placeholder="Sélectionner une date"
      auto-apply
    />
  </div>

  <div class="form-field">
    <label class="form-label">Date de fin</label>
    <VueDatePicker
      v-model="form.endAt"
      :disabled-dates="isDisabled"
      :enable-time-picker="false"
      format="dd/MM/yyyy"
      placeholder="Sélectionner une date"
      auto-apply
    />
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

<style scoped></style>
