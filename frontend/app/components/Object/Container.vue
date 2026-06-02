<script setup lang="ts">
import type { IUpdateObjectDto } from "~/dto/object/update.dto";
import type { IObject } from "~/types/object";

const props = defineProps<{
  object: IObject;
}>();

const editMode = ref(false);
const deleteAsked = ref(false);

const emit = defineEmits(["handleSubmitUpdate", "handleDelete"]);

const handleSubmitUpdate = (updateLending: Omit<IUpdateObjectDto, "id">) => {
  emit("handleSubmitUpdate", { ...updateLending, id: props.object.id });
  editMode.value = false;
};

const handleDelete = () => {
  emit("handleDelete");
  deleteAsked.value = false;
};

const renderedImage = () => {
    
}
</script>
<template>
  <div v-if="deleteAsked">
    <ObjectFormDeletion
      @cancelEdit="() => (deleteAsked = !deleteAsked)"
      @handleDelete="handleDelete"
    />
  </div>
  <div>
    <header>
      <h1>{{ object.name }}</h1>
      <ButtonOptions
        :actions="[
          {
            function: () => {
              editMode = true;
            },
            label: 'Modifier',
            svg: '/icons/edit.svg',
          },
          {
            function: () => {
              deleteAsked = true;
            },
            label: 'Supprimer',
            svg: '/icons/delete.svg',
          },
        ]"
      />
    </header>
    <main>
        <div v-for="image in object.images">
            <img :src="image" alt="Object"/>
        </div>
    </main>
  </div>
</template>

<style scoped></style>
