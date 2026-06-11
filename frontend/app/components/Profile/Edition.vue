<script setup lang="ts">
import type { IUpdateUserDto } from '~/dto/user/update.dto';
import type { IUser } from '~/types/user';

const props = defineProps<{
  user: IUser;
}>();

const emit = defineEmits(["handleSubmitUpdate", "cancelEdit"]);

const editForm = reactive<Omit<IUpdateUserDto, "id">>({
  username: props.user.username,
  avatar: props.user.avatar,
  description: props.user.description,
  email:props.user.email
});

const resetForm = () => {
editForm.username= props.user.username;
  editForm.avatar= props.user.avatar;
  editForm.description= props.user.description;
  editForm.email = props.user.email
};

watch(
  () => props.user,
  () => {
    resetForm();
  },
  { immediate: true },
);

const handleSubmit = () => {
  emit("handleSubmitUpdate", {
    username: editForm.username,
    description: editForm.description, 
email: editForm.email });
};

const handleCancel = () => {
  resetForm();
  emit("cancelEdit");
};

</script>
<template>
    <form class="edit-form" @submit.prevent="handleSubmit">
    <div class="form-group">
      <label for="username">Nom</label>
      <input id="username" v-model.trim="editForm.username" type="text" required />
    </div>
    <div>
        <label for="email">Email</label>
      <input id="email" v-model.trim="editForm.email" type="text" required />
    </div>
    <div class="form-group">
      <label for="description">Description</label>
      <input id="description" v-model.trim="editForm.description" type="text" required />
    </div>
    <div class="form-actions">
      <button type="button" class="button-secondary" @click="handleCancel">
        Annuler
      </button>
      <button type="submit" class="button-primary">Enregistrer</button>
    </div>
  </form>
  
</template>


<style scoped>

</style>