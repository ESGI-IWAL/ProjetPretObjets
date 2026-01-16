# Prête‑moi ça

## 🧰 Description

**Prête‑moi ça** est un site web de **prêt d’objets entre particuliers**, pensé pour encourager le partage, réduire la surconsommation et favoriser l’entraide locale.

Le principe est simple :

* proposer des objets que l’on utilise peu,
* emprunter près de chez soi,
* créer du lien plutôt que d’acheter.

Le projet met l’accent sur la simplicité d’usage, la confiance entre utilisateurs et une approche responsable de la consommation.

---

## 👥 L’équipe

Nous sommes une équipe de **4 personnes** ayant collaboré sur la conception, le développement et la réflexion fonctionnelle du projet.

| Membre  | Rôle                           | Photo                                 |
|---------|--------------------------------|---------------------------------------|
| Gweltaz | Développement / Backend        | ![Photo membre 1](images/gweltaz.jpeg) |
| Katel   | Gestion de projet / Full stack | ![Photo membre 2](images/katel.jpeg)  |
| Noémie  | Développement / Frontend       | ![Photo membre 3](images/noemie.jpeg) |
| Rémi    | Développement / Backend        | ![Photo membre 4](images/remi.jpeg)   |


---

## ⚙️ Technologies utilisées

### Backend

* **SprintBoot** (Java)
* **API REST**

### Frontend

* **VueJS**
* **HTML / CSS / JavaScript**

### Base de données

* **MySQL** / **PostgreSQL**

### Outils

* **Git / GitHub**
* **Jira**

---

## 🚀 Objectifs du projet

* Encourager le **partage d’objets** entre particuliers
* Réduire l’achat inutile et l’impact écologique
* Mettre en pratique un projet web complet (conception → développement → déploiement)
* Travailler en **équipe** avec des outils et méthodes proches du monde professionnel

---

## 📌 État du projet

🛠️ Projet en cours / terminé (à préciser)

---

## Installation du projet

**Prérequis :**
- Git
- Docker et Docker Compose
- Java 17
- Maven

~~~sh
git clone git@github.com:ESGI-IWAL/ProjetPretObjets.git
cd ProjetPretObjets

docker-compose up -d

mvn clean install

mvn spring-boot:run
~~~

L'application sera disponible sur 'http://localhost:8080'.

En l'occurence vu que y'a rien encore pour vérifier que l'appli est bien installée
Se rendre sur 'http://localhost:8080/swagger-ui/index.html' pour voir la doc de l'API

## 📄 Licence

Projet réalisé dans un cadre pédagogique / personnel.
Licence à définir.
