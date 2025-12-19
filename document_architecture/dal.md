# Dal

## 1. Besoin fonctionnel

Inserer le schema d'architecture
Inserer le diagramme d'architecture

## 2. Architecture applicative 

- Nous avons donc 3 composants : une interface web, un backend et une base de donnees. 
- L'interface web est realisee en vue.js, et est connectee au backend (via quelle API)
- Le backend est realise en Java SpringBoot et est en lien avec la base de donnes et le frontend. C'est cette partie qui contiendra toutes nos fonctions applicatives.
- La base de donnee est une base postgreSql contenant 4 tables : une table User, une table Lending, une table Object et une table LendingHistory.

## 3. Architecture physique

## 4. Deploiement et integration

## 5. Gestion de donnees

- Les tables, comme precise dans la partie 2, sont au nombre de 4. On considere qu'un utilisateur peut avoir aucun ou plusieurs objets, et aucun ou plusieurs prets en attente. Un objet a forcement un utilisateur / possesseur, mais n'a pas forcement de pret en cours le concernant, bien qu'il puisse en avoir eu dans le passe. Un pret est forcement lie a deux utilisateurs et a un seul objet. L'historique des prets doit contenir un objet s'il est present.
- Pour ce qui est des donnes sensibles, nous utilisons une cle de cryptage directement avec la table pour qu'aucune de ces donnees ne soient accessibles en clair dans les tables.
- Les logs quant a eux ne seront pas acccessibles a l'utilisateur / seront limites dans la quantite d'information fournie.

## 6. Securite et gestion des acces

- Tous nos utilisateurs peuvent creer un compte puis se connecter. Ces derniers ont la possibilite de creer des objets qu'ils pourront preter par la suite et voir l'historique des prets de leurs objets. 
- L'authentification se fait grace a ...

## 7. Exploitation

