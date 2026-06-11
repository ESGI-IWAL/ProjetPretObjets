<script setup lang="ts">
import type { IOption } from "~/components/AutoComplete.vue";
import useToaster from "~/composables/useToaster";
import type { ICreateLendingDto } from "~/dto/lending/create.dto";
import type { ISearchObjectDto } from "~/dto/object/search.dto";
import { createLending, searchLendingsOnDateByIdObject } from "~/services/lending";
import type { IObject } from "~/types/object";
import type { IUser } from "~/types/user";
import type { ISearchLendingPeriodDto } from "~/dto/lending/search.dto";
import { VueDatePicker } from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'
import { fr } from 'date-fns/locale'

interface IStep {
  id: number;
  title: string;
  description: string;
}

const today = new Date().toISOString().split('T')[0] as string

const initalValues:ICreateLendingDto = {
  borrowerId: 0,
  objectId: 0,
  startAt: today,
  endAt: null,
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

const emit = defineEmits(["handleSearchObjects"]);

const toaster = useToaster();

const form = reactive<ICreateLendingDto>(initalValues);

const namesOfSelected = reactive({
  borrowerName: "",
  objectName: "",
});

const blockedDates = ref<ISearchLendingPeriodDto[]>([]);
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

// ─── Computed v-model pour VueDatePicker (string <-> Date) ───────────────────

const startDatePicker = computed({
  get: () => (form.startAt ? new Date(form.startAt) : null),
  set: (val: Date | null) => {
    form.startAt =  val?.toISOString().split('T')[0] ?? "";
  },
});

const endDatePicker = computed({
  get: () => (form.endAt ? new Date(form.endAt) : null),
  set: (val: Date | null) => {
    form.endAt = val?.toISOString().split('T')[0] ?? null;
  },
});

// ─── Format d'affichage dd/MM/yyyy ──────────────────────────────────────────

const formatDate = (date: Date) => {
  const day = String(date.getDate()).padStart(2, '0');
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const year = date.getFullYear();
  return `${day}/${month}/${year}`;
};

// ─── Dates désactivées (gestion timezone) ───────────────────────────────────

function isDisabled(date: Date): boolean {
  const d = new Date(date);
  d.setHours(0, 0, 0, 0);

return blockedDates.value.some((period) => {
    const startStr = period.startedAt.split('T')[0];
    const endStr = period.endedAt?.split('T')[0] ?? "";

    if (!startStr || !endStr) return false;

    const [sy, sm, sd] = startStr.split('-').map(Number);
    const [ey, em, ed] = endStr.split('-').map(Number);

    if(!sy || !sm || !sd || !ey || !em || !ed) return false
    const start = new Date(sy, sm - 1, sd, 0, 0, 0);
    const end = new Date(ey, em - 1, ed, 23, 59, 59);

    return d >= start && d <= end;
  });
}

// ─── Handlers ────────────────────────────────────────────────────────────────

const handleSearchPeriodOnObjects = async (id: number) => {
  try {
    blockedDates.value = await searchLendingsOnDateByIdObject(id);
  } catch {
    blockedDates.value = [];
    toaster.show(
      "Erreur lors de la récupération des objects correspondants à cette date",
      "error",
      5000
    );
  }
};

const handleSearchObjects = async (dto: ISearchObjectDto) => {
  emit("handleSearchObjects", dto);
};

const handleSelectionObject = (id: number) => {
  form.objectId = id;
};

const isEntryValid = computed(() => {
  switch (currentStep.value) {
    case 1:
      return !!form.borrowerId;
    case 2:
      return !!form.objectId;
    case 3:
      return !!form.startAt ;
    default:
      return true;
  }
});

const nextStep = () => {
  if (currentStep.value === 2) {
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

const hasConflict = computed(() => {
  if (!form.startAt || !form.endAt) return false;

  const start = new Date(form.startAt);
  const end = new Date(form.endAt);

  return blockedDates.value.some((period) => {

    const pStart = new Date(period.startedAt);
    const pEnd = new Date(period.endedAt ?? "");

    // Chevauchement si les périodes se croisent
    return start <= pEnd && end >= pStart;
  });
});



const handleValidateForm = async () => {
    if (hasConflict.value) {
    toaster.show("Un prêt existe déjà sur cette période", "error", 5000);
    return;
  }
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

const resetForm = () => Object.assign(form, initalValues)

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
          <ObjectFormSearch
            @handleSearch="handleSearchObjects"
            :optionDisponibilityDate="false"
          />
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
          v-model="startDatePicker"
          :disabled-dates="isDisabled"
          :enable-time-picker="false"
          :format="formatDate"
          placeholder="Sélectionner une date"
          :locale="fr"
          auto-apply
        />
      </div>

      <div class="form-field">
        <label class="form-label">Date de fin</label>
        <VueDatePicker
          v-model="endDatePicker"
          :disabled-dates="isDisabled"
          :enable-time-picker="false"
          :key="form.startAt"
          :format="formatDate"
          placeholder="Sélectionner une date"
          :locale="fr"
          auto-apply
          :min-date="startDatePicker ?? undefined"
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

<style>
/* ── Surcharges VueDatePicker — thème naturel ── */
.dp__main {
  --dp-background-color: #FFFFFF;
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
  background-color: #FFFFFF!important;
  color: var(--color-text) !important;
  border-color: #c9c0ae !important;
  border-radius: 8px !important;
  padding: 10px 16px !important;
  transition: border-color 0.2s ease, box-shadow 0.2s ease;
}
.dp__input:focus {
  border-color: var(--color-primary) !important;
  box-shadow: 0 0 0 3px rgba(98, 148, 96, 0.2) !important;
  outline: none !important;
}
.dp__input_icon { color: var(--color-primary) !important; }
.dp__menu {
  background-color: #FFFFFF !important;
  border-color: #c9c0ae !important;
  border-radius: var(--border-radius) !important;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1) !important;
}
.dp__calendar_header_item { color: var(--color-title) !important; font-weight: 600; }
.dp__active_date, .dp__range_start, .dp__range_end {
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
.dp__cell_disabled {
  color: #b0a898 !important;
  background-color: transparent !important;
  cursor: not-allowed !important;
  text-decoration: line-through;
  opacity: 0.5;
}
.dp__nav_icon { color: var(--color-primary) !important; }
.dp__nav_btn:hover { background-color: rgba(98, 148, 96, 0.12) !important; border-radius: 8px !important; }
.dp__overlay { background-color: var(--color-surface) !important; }
.dp__overlay_cell_active {
  background-color: var(--color-primary) !important;
  color: var(--color-background) !important;
  border-radius: 8px !important;
}
</style>