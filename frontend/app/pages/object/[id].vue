<script setup lang="ts">

import type { IUpdateObjectDto } from '~/dto/object/update.dto';
import { getObjectById, updateObject } from '~/services/object';

const route = useRoute()

const object = await getObjectById(route.params.id as string)

const handleSubmitUpdate = (dto : IUpdateObjectDto) => {
    updateObject(route.params.id as string, dto)
}
</script>
<template>
  
</template>


<style>

</style>