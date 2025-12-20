# Document d'Architecture Logicielle

## 1. Contexte
Ce document présente les décisions architecturales du projet. Il décrit les choix de conception, les composants principaux, et leur interaction.
Il sert donc à :
- Fournir une vue d'ensemble claire du système.
- Définir les composants logiciels et leurs interactions.
- Supporter l’alignement des parties prenantes.

### 1.1 Terminologie
**Preteur :** Utilisateur qui prete des objets.
**Emprunteur :** Utilisateur qui emprunte un objet à un autre utilisateur.
**Date prévue de retour :** Le preteur indique la date à laquelle il souhaite recupérer son bien.
**Date effective de retour :** Le preteur indique la date à laquelle l'emprunteur à réellement rendu son bien.

## 2. Architecture applicative 

- Nous avons donc 3 composants : une interface web, un backend et une base de donnees. 
- L'interface web est realisee en vue.js, et est connectee au backend (via quelle API)
- Le backend est realise en Java SpringBoot et est en lien avec la base de donnes et le frontend. C'est cette partie qui contiendra toutes nos fonctions applicatives.
- La base de donnee est une base PostgreSQL contenant 4 tables : une table User, une table Lending, une table Object et une table LendingHistory.

## 3. Architecture physique

## 4. Deploiement et integration

## 5. Schéma relationnel de la base de données

![image](../document_architecture/schema_bdd.png)

La table **User** enregistre les informations d'un utilisateur, qu'il soit preteur ou emprunteur :
- id : son identifiant unique.
- first_name et last_name : prénom et nom de l'utilisateur.
- email : adresse mail du l'utilisateur .
- password : mot de passe de l'utilisateur sous sa forme chiffrée.
- properties : l'ensemble des objets appartenant à l'utilisateur qu'il a enregistré sur l'application. L'utilisateur peut ne pas avoir d'objets.

La table **Object** enregistre les informations d'un objet qu'un utilisateur prete :
- id : son identifiant unique.
- name : nom de l'objet.
- descrition : description libre de l'objet donné par l'utilisateur, il peut préciser son état, son utilité.

La table **Lending** enregistre l'ensemble des prets prévus ou en cours :
- id : son identifiant unique. Ce champs est utilisé afin de simplifié la clef primaire, qui aurait été sinon l'ensemble des autres champs.
- id_lender : identifiant de l'utilisateur preteur. (User.id)
- id_borrower : identifiant de l'utilisateur emprunteur.(User.id)
- id_object : identifiant de l'objet prété. (Object.id)
- date_begin : date de début du pret.
- date_end : date prévue de retour.

La table **LendingHistory** enregistre les prets ayant déjà eu lieu :
- id : son identifiant unique.
- id_lender : identifiant de l'utilisateur preteur. (User.id)
- id_borrower : identifiant de l'utilisateur emprunteur.(User.id)
- id_object : identifiant de l'objet prété. (Object.id)
- date_begin : date de début du pret.
- date_end : date effective de retour.
  
- Les logs quant a eux ne seront pas acccessibles a l'utilisateur / seront limites dans la quantite d'information fournie.

## 6. Securite et gestion des acces

- Tous nos utilisateurs peuvent creer un compte puis se connecter. Ces derniers ont la possibilite de creer des objets qu'ils pourront preter par la suite et voir l'historique des prets de leurs objets. 
- L'authentification se fait grace a ...

## 7. Exploitation

