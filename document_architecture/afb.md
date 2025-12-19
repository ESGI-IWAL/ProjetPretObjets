# Analyse Fonctionnelle du Besoin (AFB)

## 1. Contexte et présentation du projet
- **Description générale :** Plateforme de gestion de pret d'objet entre particulier.
- **Problématique initiale :** Lorsqu’un particulier prête un bien (livre, outil, équipement…), il est fréquent d’oublier à qui l’objet a été confié et depuis combien de temps. Cette situation peut entraîner des pertes ou des difficultés à récupérer le bien.
- **Objectifs globaux :**
  - Offrir la possibilité d’enregistrer facilement les prêts effectués, en précisant l’objet concerné et l’identité de l’emprunteur.
  - Mettre en place un système de rappel automatique afin d’informer l’emprunteur de la date prévue de restitution et d’aider le prêteur à suivre ses biens.
  - Réduire la consommation d'objet peu utilisé.
  - Favoriser le partage.
---

## 2. Expression du besoin
- **A qui le produit rend-il service ?** Un particulier qui prête ses biens.
- **Sur quoi agit le produit ?** Sur la gestion, le suivi et la traçabilité des objets prêtés à d’autres personnes.
- **Dans quel but ?** Pour permettre au prêteur de savoir à tout moment à qui il a prêté un objet, depuis quand, et d’être alerté automatiquement lorsque la date de retour prévue approche, afin d’éviter les oublis et les pertes.
---

## 3. Analyse du besoin

### 3.1 Must have
- **M1 :** Permettre à un utilisateur de créer un compte et gérer son profil (modification, suppression)
- **M2 :** Enregistrer un pret (objet, emprunteur, date de début et de fin prévue), le modifier et l'annuler.
- **M3 :** Informer lorsqu'un emprunt arrive à échéance (notif, mail, SMS ou autre).
- **M4 :** Visualiser l'ensemble de ses prêts (en cours, prévus, arrivant à échéance, retour en retard)

### 3.2 Should have
- **S1 :** Avoir accès à une liste de personnes de confiance validé par l'utilisateur.
- **S2 :** Avoir accès à la liste des objets pouvant être emprunté de sa liste de personnes de confiance (avec recherche et filtre).
  
### 3.3 Can have
- **C1 :** Avoir un moyen de conversation.
- **C2 :** Pouvoir préter à des personnes qui ne sont pas sur l'application.

### 3.4 Won't have
- **W1 :** Pouvoir noter les autres utilisateurs.
---

## 4. Contraintes et limites
- **Techniques :** Base de données sécurisée.
- **Organisationnelles :** Budget inexistant, délais de mise en production fixés à 6 mois.
- **Réglementaires :** Respect du RGPD, conditions générales d’utilisation claires.
---

## 5. Objectifs 
- 95 % des actions principales (publication, demande, validation) réalisées en moins de 3 secondes.
- Interface validée par un panel de 5 utilisateurs tests.
---


## 6. Glossaire / définitions
- **Prêteur :** Utilisateur mettant un objet à disposition.
- **Emprunteur :** Utilisateur demandant un objet.
- **Objet :** Bien matériel proposé au prêt.
- **Demande de prêt :** Action par laquelle un emprunteur sollicite un objet.
