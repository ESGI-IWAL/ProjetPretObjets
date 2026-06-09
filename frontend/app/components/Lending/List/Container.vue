<script setup lang="ts">
import type { ISearchLendingDto } from "~/dto/lending/search.dto";
import type { IUpdateLendingDto } from "~/dto/lending/update.dto";
import {
  deleteLending,
  getLendingById,
  updateLending,
} from "~/services/lending";
import type { ILending } from "~/types/lending";

const props = defineProps<{
  lendings: ILending[];
  refreshList: () => void;
}>();


const emit = defineEmits(["search"]);
const date = ref<string>(new Date().toISOString().split('T')[0] as string)

function search(dto: ISearchLendingDto) {
  if(dto.date) {
    date.value = dto.date
  } else {
    date.value = new Date().toISOString().split('T')[0] as string
  }
  emit("search", dto);
}
const filterIsOpen = ref<boolean>(false);

const toaster = useToaster();
const modificateLending = ref<ILending | null>(null);
const editMode = ref<boolean>(false);
const deleteMode = ref<boolean>(false);
const selectedLendingId = ref<number | null>(null);

const handleSubmitUpdate = async (updateLendingDto: IUpdateLendingDto) => {
  try {
    await updateLending(updateLendingDto);
    editMode.value  = false
    props.refreshList();
    toaster.show("Votre prêt a bien été modifié", "success");
  } catch {
    toaster.show("Erreur lors de la mise a jour de votre prêt", "error", 5000);
  }
};

const handleSubmitDelete = async () => {
  if (selectedLendingId.value === null) return;

  try {
    await deleteLending(selectedLendingId.value);
    props.refreshList();
    toaster.show("Le prêt a bien été supprimé", "success");
  } catch {
    toaster.show("Erreur lors de la suppression de votre prêt", "error", 5000);
  } finally {
    deleteMode.value = false;
    selectedLendingId.value = null;
  }
};

const handleLendingDeleteRequest = (id: number) => {
  selectedLendingId.value = id;
  deleteMode.value = true;
};

const handleLenginEditRequest = async (id: number) => {

  modificateLending.value = await getLendingById(id);
  editMode.value = true;
};
</script>

<template>
  <div class="space-y-6">
    <div v-if="filterIsOpen">
      <section class="surface-card space-y-4">
        <LendingFormSearch @search="search" />
      </section>
    </div>
    <div class="flex justify-end gap-4">
      <ButtonCreation
        :navigation-creation="() => navigateTo('/lendings/new')"
        label="Créer un prêt"
      />
      <ButtonFilter :open-filter="() => (filterIsOpen = !filterIsOpen)" />
    </div>

    <div
      v-if="lendings.length === 0"
      class="surface-card text-center text-gray-500"
    >
      Aucun prêt trouvé.
    </div>
    <div v-if="editMode && modificateLending">
      <LendingFormModification
        :lending="modificateLending"
        @handleSubmitUpdate="handleSubmitUpdate"
        @cancelEdit="editMode = false"
      />
    </div>
    <div v-if="deleteMode">
      <LendingFormDeletion
        @cancelEdit="() => { deleteMode = false; selectedLendingId = null }"
        @handleDelete="handleSubmitDelete"
      />
    </div>
    <div v-else class="list-grid">
      <div v-for="lending in lendings" :key="lending.id">
        <LendingListCard
          :lending="lending"
          :date="date"
          @lendingEdit="(id) => handleLenginEditRequest(id)"
          @lendingDelete="(id) => handleLendingDeleteRequest(id)"
        />
      </div>
    </div>
  </div>
</template>
