<script setup lang="ts">
import type { ICreateLendingDto } from '~/dto/lending/create.dto';
import { createLending } from '~/services/lending';


const handleCreationSubmit = (dto : ICreateLendingDto) => {
    createLending(dto)
}

</script>
<template>
</template>

<style scoped>
</style>