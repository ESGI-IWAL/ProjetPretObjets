# Prête-moi ça

## 🧰 Description

**Prête-moi ça** est une plateforme de **prêt d’objets entre particuliers**, pensée pour encourager le partage, réduire la surconsommation et favoriser l’entraide locale.

Le principe est simple :
- proposer des objets que l’on utilise peu,
- emprunter près de chez soi,
- créer du lien plutôt que d’acheter.

---

## 👥 L’équipe

Nous sommes une équipe de 4 personnes ayant travaillé sur la conception et le développement du projet.

| Membre  | Rôle                           |
|---------|--------------------------------|
| Gweltaz | Développement / Backend        |
| Katel   | Gestion de projet / Full stack |
| Noémie  | Développement / Frontend       |
| Rémi    | Développement / Backend        |

---

## ⚙️ Stack technique

### Backend
- Spring Boot (Java)
- API REST
- Maven

### Frontend
- Nuxt 3 (Vue.js)

### Base de données
- MySQL / PostgreSQL

### Outils
- Git / GitHub
- Jira

---

## 🚀 Objectifs

- Encourager le partage d’objets entre particuliers
- Réduire l’achat inutile et l’impact écologique
- Développer une application fullstack complète
- Travailler en équipe avec des outils professionnels

---

## 📦 Installation du projet

### Prérequis
- Git
- Docker + Docker Compose
- Java 17
- Node.js

### Backend

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

---

## 🐳 Lancement avec Docker (recommandé)

Vous pouvez lancer l'ensemble du projet avec Docker Compose.

### Mode production / proche prod

La configuration par défaut utilise `compose.yml` et lance :
- PostgreSQL,
- le backend Spring Boot packagé en jar,
- le frontend Nuxt en mode production.

Construire et lancer (affiche les logs) :
```bash
cd /home/remcor/Documents/work/ProjetPretObjets
docker compose up --build
```

Démarrer en arrière-plan :
```bash
docker compose up -d --build
```

### Mode développement

Pour le dev avec hot-reload, utilisez `compose.dev.yml` :
```bash
cd /home/remcor/Documents/work/ProjetPretObjets
docker compose -f compose.dev.yml up --build
```

Ce mode lance :
- PostgreSQL avec un volume séparé `postgres_data_dev`,
- le backend avec `mvn spring-boot:run`,
- le frontend avec `npm run dev`.

Arrêter et supprimer les conteneurs :
```bash
docker compose down
```

Réinitialiser la base de données (supprime aussi les volumes) :
```bash
docker compose down -v
```

Pour le mode dev, vous pouvez aussi nettoyer la stack dédiée :
```bash
docker compose -f compose.dev.yml down -v
```

Accès
- Frontend : http://localhost:3000
- Backend : http://localhost:8080
- PostgreSQL : localhost:5432 (user/password/db: admin/admin/projetPret)


