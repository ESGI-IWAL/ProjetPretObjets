<script setup lang="ts">
import Toaster from '~/components/Toaster.vue'

const isOpen = ref<boolean>(false);
const {logout} = useAuth()
const handleClick = () => {
  isOpen.value = !isOpen.value;
};
</script>

<template>
  <div class="app-layout">
    <nav class="sidebar" :class="{ 'is-open': isOpen }">
      <button @click="handleClick" class="sidebar-toggle">☰</button>
      <div class="sidebar-menu" :class="{ 'is-open': isOpen }">
        <div>
          <h3 class="menu-title">Prêts</h3>
          <div class="menu-links">
            <NuxtLink to="/lendings" class="menu-link">En cours</NuxtLink>
            <NuxtLink to="/lendings/new" class="menu-link">Nouveau</NuxtLink>
          </div>
        </div>
        <div>
          <h3 class="menu-title">Objets</h3>
          <div class="menu-links">
            <NuxtLink to="/objects" class="menu-link">Liste</NuxtLink>
            <NuxtLink to="/objects/new" class="menu-link">Nouveau</NuxtLink>
          </div>
        </div>
      </div>
    </nav>
    <div class="main-content">
      <header class="header">
        <NuxtLink to="/" class="logo">
          Prête-moi ça !
        </NuxtLink>
        <div class="header-actions">
          <NuxtLink to="/profile" class="header-link">Profil</NuxtLink>
          <NuxtLink to="/login" @click="logout" class="header-link">Déconnexion</NuxtLink>
        </div>
      </header>
      <main class="content-area">
        <slot />
      </main>
      <Toaster />
      <footer class="footer">
        <p>&copy; 2026 Prête-moi ça ! - Tous droits réservés.</p>
      </footer>
    </div>
  </div>
</template>

<style scoped>
.app-layout {
  display: flex;
  min-height: screen;
  background-color: var(--color-background);
}

.sidebar {
  background-color: var(--color-secondary);
  color: var(--color-background);
  padding: 1rem;
  transition: width 0.3s ease;
  width: 4rem; /* 64px */
}

.sidebar.is-open {
  width: 12rem; /* 192px */
}

.sidebar-toggle {
  position: sticky;
  top: 1.25rem; /* 20px */
  color: var(--color-background);
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
}

.sidebar-menu {
  display: none;
  margin-top: 2rem;
  gap: 1rem;
  flex-direction: column;
  position: sticky;
  top: 4.5rem; /* 72px */
}

.sidebar-menu.is-open {
  display: flex;
}

.menu-title {
  font-size: 1.125rem; /* 18px */
  font-weight: 600;
  margin-bottom: 0.5rem;
  color: var(--color-background);
}

.menu-links {
  display: flex;
  flex-direction: column;
  gap: 0.75rem; /* 12px */
  margin-left: 1rem;
}

.menu-link {
  color: var(--color-surface);
  text-decoration: none;
  transition: color 0.3s ease;
}

.menu-link:hover {
  color: var(--color-accent);
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.header {
  background-color: var(--color-primary);
  color: var(--color-background);
  padding: 1rem 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: sticky;
  top: 0;
  z-index: 10;
}

.logo {
  font-size: 1.5rem; /* 24px */
  font-weight: 700;
  color: var(--color-background);
  text-decoration: none;
}

.header-actions {
  display: flex;
  gap: 1.5rem; /* 24px */
}

.header-link {
  color: var(--color-surface);
  text-decoration: none;
  transition: color 0.3s ease;
}

.header-link:hover {
  color: var(--color-accent);
}

.content-area {
  flex: 1;
  padding: 2rem;
}

.footer {
  background-color: var(--color-surface);
  color: var(--color-text);
  padding: 1rem;
  text-align: center;
  font-size: 0.875rem; /* 14px */
}
</style>
