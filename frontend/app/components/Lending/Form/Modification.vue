<script setup lang="ts">
import type { ILending } from "~/types/lending";
import type { IUpdateLendingDto } from "~/dto/lending/update.dto";
import { VueDatePicker } from "@vuepic/vue-datepicker";
import "@vuepic/vue-datepicker/dist/main.css";
import type { ISearchLendingPeriodDto } from "~/dto/lending/search.dto";
import { searchLendingsOnDateByIdObject } from "~/services/lending";
import { fr } from 'date-fns/locale'


const props = defineProps<{
  lending: ILending;
}>();

const toaster = useToaster();
const emit = defineEmits(["handleSubmitUpdate", "cancelEdit"]);

const editableStartDate = ref<string>(props.lending.startedAt ?? "");
const editableEndDate = ref<string>(props.lending.endedAt ?? "");
const blockedDates = ref<ISearchLendingPeriodDto[]>([]);

// ─── Période originale du prêt ───────────────────────────────────────────────

const originalPeriod = reactive({
  startedAt: props.lending.startedAt,
  endedAt: props.lending.endedAt,
});

// ─── Computed v-model (string <-> Date) ──────────────────────────────────────

const startDatePicker = computed({
  get: () => (editableStartDate.value ? new Date(editableStartDate.value) : null),
  set: (val: Date | null) => {
    editableStartDate.value = val?.toISOString().split('T')[0] ?? "";
  },
});

const endDatePicker = computed({
  get: () => (editableEndDate.value ? new Date(editableEndDate.value) : null),
  set: (val: Date | null) => {
    editableEndDate.value =  val?.toISOString().split('T')[0] ?? "";
  },
});

// ─── Format d'affichage dd/MM/yyyy ───────────────────────────────────────────

const formatDate = (date: Date) => {
  const day = String(date.getDate()).padStart(2, '0');
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const year = date.getFullYear();
  return `${day}/${month}/${year}`;
};

// ─── Dates désactivées ───────────────────────────────────────────────────────

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

function isDisabled(date: Date): boolean {
  const d = new Date(date);
  d.setHours(0, 0, 0, 0);

  // Si la date est dans la période originale du prêt, elle est autorisée
  if (originalPeriod.startedAt && originalPeriod.endedAt) {
    const origStart = new Date(originalPeriod.startedAt.split('T')[0] as string);
    const origEnd = new Date(originalPeriod.endedAt.split('T')[0]as string);
    origEnd.setHours(23, 59, 59);
    if (d >= origStart && d <= origEnd) return false;
  }

  return blockedDates.value.some((period) => {
    const startStr = period.startedAt.split('T')[0];
    const endStr = period.endedAt?.split('T')[0] ?? "";

    if (!startStr || !endStr) return false;

    const [sy, sm, sd] = startStr.split('-').map(Number);
    const [ey, em, ed] = endStr.split('-').map(Number);

    if (!sy || !sm || !sd || !ey || !em || !ed) return false;

    const start = new Date(sy, sm - 1, sd, 0, 0, 0);
    const end = new Date(ey, em - 1, ed, 23, 59, 59);

    return d >= start && d <= end;
  });
}

// ─── Détection de conflit ────────────────────────────────────────────────────

const hasConflict = computed(() => {
  if (!editableStartDate.value || !editableEndDate.value) return false;

  const start = new Date(editableStartDate.value);
  const end = new Date(editableEndDate.value);

  return blockedDates.value.some((period) => {
    // Ignorer la période originale du prêt
    if (
      period.startedAt === originalPeriod.startedAt &&
      period.endedAt === originalPeriod.endedAt
    ) return false;

    const pStart = new Date(period.startedAt);
    const pEnd = new Date(period.endedAt ?? "");

    return start <= pEnd && end >= pStart;
  });
});

// ─── Handlers ────────────────────────────────────────────────────────────────

onMounted(async () => {
  await handleSearchPeriodOnObjects(props.lending.object.id);
});

const handleSubmitUpdate = async () => {
  if (hasConflict.value) {
    toaster.show("Un prêt existe déjà sur cette période", "error", 5000);
    return;
  }

  const payload: IUpdateLendingDto = {
    id: props.lending.id,
    startedAt: editableStartDate.value || null,
    endedAt: editableEndDate.value || null,
  };
  emit("handleSubmitUpdate", payload);
};

const handleCancelEdit = () => {
  emit("cancelEdit");
  resetForm();
};

const resetForm = () => {
  editableStartDate.value = props.lending.startedAt ?? "";
  editableEndDate.value = props.lending.endedAt ?? "";
};
</script>

<template>
  <PopIn :title="'Modification du prêt'">
    <form class="flex flex-col gap-4">
      <div class="form-field">
        <label class="form-label">Date de début</label>
        <VueDatePicker
          v-model="startDatePicker"
          :enable-time-picker="false"
          :disabled-dates="isDisabled"
          :format="formatDate"
          :locale="fr"
          auto-apply
        />
      </div>

      <div class="form-field">
        <label class="form-label">Date de fin</label>
        <VueDatePicker
          v-model="endDatePicker"
          :key="editableStartDate"
          :enable-time-picker="false"
          :disabled-dates="isDisabled"
          :format="formatDate"
          :locale="fr"
          auto-apply
          :min-date="startDatePicker ?? undefined"
        />
      </div>
    </form>

    <template #buttons>
      <ButtonForm
        :cancelForm="handleCancelEdit"
        :validateForm="() => handleSubmitUpdate()"
        :disabled="hasConflict"
      />
    </template>
  </PopIn>
</template>

<style>
/* ── Surcharges VueDatePicker — thème naturel ── */
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
  transition: border-color 0.2s ease, box-shadow 0.2s ease;
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