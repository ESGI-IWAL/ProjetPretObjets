<script setup lang="ts">

    const props = defineProps<{
        endDate: Date|null,
        startDate:Date,
        editMode: boolean
    }>()

    const emit = defineEmits(['handleSubmitUpdate', 'cancelEdit'])
    const toInputDate = (value: Date | null) => value ? value.toISOString().slice(0, 10) : ''
    const toDateValue = (value: string) => value ? new Date(value) : null
    const editableEndDate = ref<string>(toInputDate(props.endDate))
    const editableStartDate = ref<string>(toInputDate(props.endDate))

    watch(() => props.endDate, (newValue) => {
        editableEndDate.value = toInputDate(newValue)
    })

        watch(() => props.startDate, (newValue) => {
        editableStartDate.value = toInputDate(newValue)
    })

    const handleSubmitUpdate = async() => {
        emit('handleSubmitUpdate', {})
    }

    const handleCancelEdit = () => {
        emit('cancelEdit')
        resetForm()
    }

    const resetForm = () => {
        editableEndDate.value = toInputDate(props.endDate)
    }
</script>
<template>
    <form>
        <input type="date" v-model="editableEndDate" />
        <input type="date" v-model="editableStartDate" />
        <ButtonForm :cancelForm="handleCancelEdit" :validateForm="() => handleSubmitUpdate()" />
    </form>
</template>


<style>

</style>