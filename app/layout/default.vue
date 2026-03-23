<script setup lang="ts">

</script>

<template>
    <div>
        <nav>
            MenuBurger
        </nav>
        <header>
            <NuxtLink to="/">Home</NuxtLink>
        </header>
        <main>
            <slot/>
        </main>
        <footer>
            Footer
        </footer>
    </div>
</template>

<style scoped>

</style>