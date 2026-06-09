<script setup lang="ts">
// Pas de script nécessaire pour cette page d'accueil simple
</script>

<template>
  <div class="home-container">
    <header class="home-header">
      <h1 class="title">Bienvenue sur "Prête-moi ça !"</h1>
      <p class="subtitle">La plateforme d'échange d'objets entre voisins, simple et écologique.</p>
    </header>

    <section class="features-grid">
      <div class="feature-card">
        <h2 class="feature-title">Empruntez Facilement</h2>
        <p>Trouvez l'objet dont vous avez besoin près de chez vous et empruntez-le en quelques clics.</p>
      </div>
      <div class="feature-card">
        <h2 class="feature-title">Prêtez et Partagez</h2>
        <p>Donnez une seconde vie à vos objets en les prêtant à votre communauté.</p>
      </div>
      <div class="feature-card">
        <h2 class="feature-title">Économisez et Écologisez</h2>
        <p>Réduisez le gaspillage et faites des économies en participant à une consommation collaborative.</p>
      </div>
    </section>

    <section class="cta-section">
      <h2 class="cta-title">Prêt à commencer ?</h2>
      <div class="cta-buttons">
        <NuxtLink to="/objects" class="btn btn-primary">Voir les objets disponibles</NuxtLink>
        <NuxtLink to="/lendings/new" class="btn btn-secondary">Proposer un prêt</NuxtLink>
      </div>
    </section>
  </div>
</template>

<style scoped>
.home-container {
  display: flex;
  flex-direction: column;
  gap: 3rem;
  padding: 2rem;
}

.home-header {
  text-align: center;
  padding: 2rem 0;
}

.title {
  font-size: 2.5rem; /* 40px */
  color: var(--color-title);
  margin-bottom: 0.5rem;
}

.subtitle {
  font-size: 1.25rem; /* 20px */
  color: var(--color-text);
  max-width: 600px;
  margin: 0 auto;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 2rem;
}

.feature-card {
  background-color: var(--color-surface);
  padding: 2rem;
  border-radius: var(--border-radius);
  text-align: center;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
}

.feature-title {
  font-size: 1.5rem; /* 24px */
  color: var(--color-primary);
  margin-bottom: 1rem;
}

.cta-section {
  text-align: center;
  padding: 3rem 0;
  background-color: var(--color-surface);
  border-radius: var(--border-radius);
}

.cta-title {
  font-size: 2rem; /* 32px */
  color: var(--color-title);
  margin-bottom: 1.5rem;
}

.cta-buttons {
  display: flex;
  justify-content: center;
  gap: 1rem;
  flex-wrap: wrap;
}
</style>