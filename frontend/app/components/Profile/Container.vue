<script setup lang="ts">
import type { IUpdateUserDto } from "~/dto/user/update.dto";
import type { IUser } from "~/types/user";

const props = defineProps<{
  user: IUser;
}>();

const editMode = ref<boolean>(false);


const emit = defineEmits(["handleSubmitUpdate"]);

const handleSubmitUpdate = (updateObject: Omit<IUpdateUserDto, "id">) => {
  emit("handleSubmitUpdate", { ...updateObject, id: props.user.id });
  editMode.value = false;
};

const handleCancelEdit = () => {
  editMode.value = false;
};

</script>
<template>
    <div>
      <ButtonOptions
        :actions="[
          {
            function: () => {
              editMode = true;
            },
            label: 'Modifier',
            svg: '/icons/edit.svg',
          }
        ]"
      />
      <ProfileEdition
        v-if="editMode"
        :user="user"
        @handleSubmitUpdate="handleSubmitUpdate"
        @cancelEdit="handleCancelEdit"
      />
       <div v-else>

         <div>
           <img src="/image.png" alt="Profil utilisateur"/>
          </div>
          <div>
            <label> Pseudo du profil </label>
            <p> {{ user.username }}</p>
          </div>
          <div>
            <label> Email </label>
            <p> {{ user.email }}</p>
          </div>
          <div>
            <label> Votre déscription </label>
            <p> {{  user.description }}</p>
          </div>
        </div>
    </div>

</template>

<style scoped></style>
