<script setup lang="ts">
    definePageMeta({
      middleware: 'auth'
    })
    export interface IModificateUser {
        description: string | null,
        avatar: string | null,
        loanActive: boolean,
        numberOfLoans: number,
        email: string
    }
    const editMode = ref(false)
    const activeUser = useActiveUser()

    const handleSubmit = ({description, avatar, loanActive, numberOfLoans, email}: IModificateUser) => {
        console.log("Profile update submitted with description:", description, "and avatar:", avatar);
        console.log("Loan active:", loanActive, "Number of loans:", numberOfLoans);
        console.log("Email:", email);
    }

    const handleEditMode = () => {
        editMode.value = !editMode.value
    }
</script>
<template>
    <div v-if="editMode">
        <ProfileEdition :user="activeUser" @handleSubmit="handleSubmit" @desactivateEditMode="handleEditMode" />
    </div>
    <div v-else>
        <ProfileContainer :user="activeUser"  @activateEditMode="handleEditMode" />
    </div>
</template>


<style>
img {
    width: 100px;
    height: 100px;
    border-radius: 50%;
}
</style>    