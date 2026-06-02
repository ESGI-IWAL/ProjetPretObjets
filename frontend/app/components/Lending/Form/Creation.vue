<script setup lang="ts">
import type { IOption } from "~/components/AutoComplete.vue";
import useToaster from "~/composables/useToaster";
import type { ICreateLendingDto } from "~/dto/lending/create.dto";
import type { ISearchLendingDto } from "~/dto/lending/search.dto";
import type { ISearchObjectDto } from "~/dto/object/search.dto";
import Id from "~/pages/lendings/[id].vue";
import { createLending } from "~/services/lending";
import { getObjects, searchObject } from "~/services/object";
import { getUsers } from "~/services/user";
import type { IObject } from "~/types/object";
import type { IUser } from "~/types/user";

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
  objects : {
    type: Array as () => IObject[]|null
  }
});

const emit = defineEmits(["handleSearchObjectsOnDate", "handleSearchObjects"])

const toaster = useToaster()

const form = reactive<ICreateLendingDto>({
  borrowerId: 0,
  objectId: 0,
  startAt: new Date(),
  endAt: null,
});

const formatDateForInput = (d: Date | null) => {
  if (!d) return "";
  const year = d.getFullYear();
  const month = String(d.getMonth() + 1).padStart(2, "0");
  const day = String(d.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
};

const onStartAtInput = (e: Event) => {
  const v = (e.target as HTMLInputElement).value;
  form.startAt = v ? new Date(v + "T00:00:00") : new Date();
};

const onEndAtInput = (e: Event) => {
  const v = (e.target as HTMLInputElement).value;
  form.endAt = v ? new Date(v + "T00:00:00") : null;
};

const namesOfSelected = reactive({
  borrowerName: "",
  objectName: "",
});

const objectsIOption = ref<IOption[] | null>(null);
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
    title: "Dates de prêt",
    description: "Indiquez les dates de début et de fin du prêt.",
  },
  {
    id: 3,
    title: "Sélection de l'objet",
    description: "Choisissez l'objet à emprunter",
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

const handleObjectsStep = () => {
    emit("handleSearchObjectsOnDate", {
      disponibilityEndDate: form.endAt,
      disponibilityStartDate: form.endAt,
    })

    objectsIOption.value = props?.objects?.map((objet) => {
      return { label: objet.name };
    }) ?? [];
}

  const handleSearchObject = async (dto : Omit<ISearchObjectDto, 'disponibilityStartDate' | "disponibilityEndDate">) => {
      emit('handleSearchObjects', dto)
  }

  const handleSelectionObject = (id: number)=> {
    form.objectId = id
  }
const endDateVerification = (): boolean => {
  if (form.endAt) return form.startAt >= form.endAt;
  else return true;
};

const isEntryValid = computed(() => {
  switch (currentStep.value) {
    case 1:
      return !!form.borrowerId;
    case 3:
      return !!form.objectId;
    case 2:
      return !!form.startAt && endDateVerification();
    default:
      return true;
  }
});

const nextStep = () => {
    if(currentStep.value == 1){
        handleObjectsStep()
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
    try{
        await createLending(form);
        resetForm();
        navigateTo("/lendings");
        toaster.show("Le prêt a bien été créé")
    }
    catch {
         resetForm();
        navigateTo("/lendings");
        toaster.show("Erreur lors de la création de votre prêt", 'error', 5000)
    }
};

const resetForm = () => {
  form.borrowerId = 0;
  form.objectId = 0;
  form.startAt = new Date();
  form.endAt = null;
  currentStep.value = 1;
};

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
      <div v-if="currentStep === 2" class="form-grid">
        <div class="form-field">
          <label for="startAt" class="form-label">Date de début</label>
          <input
            id="startAt"
            type="date"
            :value="formatDateForInput(form.startAt)"
            @input="onStartAtInput"
            class="form-input"
          />
          <div style="margin-top: 6px; font-size: 0.9rem; color: #666">
            Date du jour: {{ formatDateLong(form.startAt) }}
          </div>
        </div>

        <div class="form-field">
          <label for="endAt" class="form-label">Date de fin</label>
          <input
            id="endAt"
            type="date"
            :value="formatDateForInput(form.endAt)"
            @input="onEndAtInput"
            class="form-input"
          />
        </div>
      </div>
      <div v-if="currentStep === 3 && objects" class="form-field">
        <label for="object" class="form-label">Objet</label>
        <div>
          <ObjectFormSearch @handleSearchObject="handleSearchObject" />
        </div>
         <div v-for="object in objects" :key="object.id">
           <ObjectListCardSelection :object="object" @handleSelection="handleSelectionObject" :selected="form.objectId === object.id"/>
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

<style scoped></style>
