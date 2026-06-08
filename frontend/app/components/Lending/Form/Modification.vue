<script setup lang="ts">
import { ref, watch } from 'vue';
import { toInputDate } from '~/composables/useDate';
import type { ILending } from '~/types/lending';
import type { IUpdateLendingDto } from '~/dto/lending/update.dto';

const props = defineProps<{
  lending: ILending
}>();

const emit = defineEmits(["handleSubmitUpdate", "cancelEdit"]);

const editableEndDate = ref<string>(toInputDate(props.lending.endedAt) ?? "");
const editableStartDate = ref<string>(toInputDate(props.lending.startedAt) ?? "");

watch(
  () => props.lending.endedAt,
  (newValue) => {
    editableEndDate.value = toInputDate(newValue) ?? "";
  },
);

watch(
  () => props.lending.startedAt,
  (newValue) => {
    editableStartDate.value = toInputDate(newValue) ?? "";
  },
);

const handleSubmitUpdate = async () => {
  const payload: IUpdateLendingDto = {
    id: props.lending.id,
    startAt: editableStartDate.value || null,
    endAt: editableEndDate.value || null,
  };
  emit("handleSubmitUpdate", payload);
};

const handleCancelEdit = () => {
  emit("cancelEdit");
  resetForm();
};

const resetForm = () => {
  editableEndDate.value = toInputDate(props.lending.endedAt) ?? "";
  editableStartDate.value = toInputDate(props.lending.startedAt) ?? "";
};
</script>

<template>
  <PopIn :title="'Modification du prêt'">
    <form class="flex flex-col gap-4">
      <div class="form-field">
        <label for="editableStartDate" class="form-label">Date de début</label>
        <input
          id="editableStartDate"
          type="date"
          v-model="editableStartDate"
          class="form-input"
        />
      </div>

      <div class="form-field">
        <label for="editableEndDate" class="form-label">Date de fin</label>
        <input
          id="editableEndDate"
          type="date"
          v-model="editableEndDate"
          class="form-input"
        />
      </div>
    </form>
    <template #buttons>
      <ButtonForm
        :cancelForm="handleCancelEdit"
        :validateForm="() => handleSubmitUpdate()"
      />
    </template>
  </PopIn>
</template>

<style></style>
