<script setup lang="ts">
 defineProps<{
    title: string;
}>()
</script>

<template>
<section>

    <header>
        <h1> {{ title }}</h1>
    </header>
    <main>
        <slot />
    </main>
    <div>
        <slot name="buttons"/>
    </div>
    </section>
    
</template>