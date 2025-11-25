# Analyse Fonctionnelle du Besoin (AFB)

## 1. Contexte et présentation du projet
- **Description générale :** Plateforme de gestion de pret d'objet entre particulier.
- **Problématique initiale :** De nombreux objets sont peu utilisés et restent inutilisés chez les particuliers. L’application vise à optimiser leur usage en favorisant le partage.
- **Objectifs globaux :**
  - Faciliter la mise en relation entre prêteurs et emprunteurs.
  - Promouvoir l’économie collaborative et réduire la consommation inutile.
---

## 2. Expression du besoin
- **Qui exprime le besoin :** Utilisateurs particuliers souhaitant prêter ou emprunter des objets.
- **Nature du besoin :** Fonctionnel (gestion des prêts), organisationnel (mise en relation)
- **Finalité recherchée :** Permettre aux utilisateurs de publier des objets disponibles et de gérer les demandes de prêt.
---

## 3. Analyse du besoin

### 3.1 Fonctions de service (FS)
- **FS1 :** Permettre à un utilisateur de créer un compte et gérer son profil.
- **FS2 :** Publier un objet disponible au prêt (photo, description, état).
- **FS3 :** Rechercher des objets disponibles selon critères (catégorie, localisation).
- **FS4 :** Envoyer une demande de prêt à un autre utilisateur.
- **FS5 :** Gérer les demandes reçues (accepter, refuser).
- **FS6 :** Suivre l’état des prêts (en attente, en cours, terminé).
- **FS7 :** Permettre l’évaluation et les avis entre utilisateurs.

### 3.2 Fonctions contraintes (FC)
- **FC1 :** Respecter les réglementations en matière de protection des données (RGPD).
- **FC2 :** Interface responsive.
- **FC3 :** Authentification sécurisée des utilisateurs.
- **FC4 :** Temps de réponse inférieur à 2 secondes pour les principales actions.
- **FC5 :** Système de notifications pour informer des demandes et statuts.
---

## 4. Diagramme des interactions
// TO DO : Insérer un diagramme d'interactions avec :
// Acteurs : Utilisateur prêteur, Utilisateur emprunteur, Application.  
// Flux : Publication d’objet → Consultation → Demande de prêt → Validation → Suivi du prêt.
---

## 5. Critères d’évaluation
- 95 % des actions principales (publication, demande, validation) réalisées en moins de 3 secondes.
// Interface validée par un panel de 5 utilisateurs tests.
// Taux de satisfaction utilisateur supérieur à 80 % lors des enquêtes internes.
---

## 6. Priorisation des besoins
- **Indispensables :** Création de compte, publication d’objet, demande de prêt, gestion des demandes.
- **Souhaitables :** Système de notation/avis, notifications push.
- **Optionnels :** Intégration d’un calendrier de disponibilité, assurance intégrée.
---

## 7. Contraintes et limites
- **Techniques :** Base de données sécurisée.
- **Organisationnelles :** Budget inexistant, délais de mise en production fixés à 6 mois.
- **Réglementaires :** Respect du RGPD, conditions générales d’utilisation claires.
---

## 8. Glossaire / définitions
- **Prêteur :** Utilisateur mettant un objet à disposition.
- **Emprunteur :** Utilisateur demandant un objet.
- **Objet :** Bien matériel proposé au prêt.
- **Demande de prêt :** Action par laquelle un emprunteur sollicite un objet.
