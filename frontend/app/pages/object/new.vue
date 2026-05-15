<script setup lang="ts">
import type { ICreateObjectDto } from '~/dto/object/create.dto';
import { createObject } from '~/services/object';


const handleCreationSubmit = (dto: ICreateObjectDto) => {
    createObject(dto)
}
</script>
<template>

</template>

<style scoped>
</style>