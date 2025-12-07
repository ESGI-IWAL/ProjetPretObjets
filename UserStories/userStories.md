# User Stories – Site de prêt d’objets

| ID  | User Story | Critères d'acceptation |
|-----|------------|-------------------------|
| **US1** | En tant qu'utilisateur, je veux **ajouter un objet à prêter**, afin de gérer mon inventaire. | **Étant donné** que je suis connecté<br>**Quand** j’accède à "Ajouter un objet" et renseigne nom + description + état<br>**Alors** l’objet est enregistré.<br><br>**Étant donné** que je ne renseigne pas de nom<br>**Alors** une erreur apparaît. |
| **US2** | En tant qu'utilisateur, je veux **enregistrer un prêt**, afin de savoir à qui et quand j’ai prêté un objet. | **Étant donné** que j’ai des objets et des contacts<br>**Quand** je crée un prêt valide<br>**Alors** l’objet devient indisponible.<br><br>**Étant donné** que l’objet est déjà prêté<br>**Alors** la création est refusée. |
| **US3** | En tant qu’utilisateur, je veux **être notifié quand la date de retour approche**, afin de penser à récupérer mon objet. | **Étant donné** qu’un prêt a une date de retour prévue<br>**Quand** il reste moins de 48h<br>**Alors** une notification est envoyée.<br><br>Une seule notification par prêt. |
| **US4** | En tant qu’utilisateur, je veux **voir la liste des objets prêtés**, afin d’avoir une vue rapide de leur disponibilité. | **Étant donné** qu’il y a des objets prêtés<br>**Quand** j’ouvre la page des objets prêtés<br>**Alors** la liste apparaît.<br><br>Sinon : “Aucun objet prêté actuellement”. |
| **US5** | En tant qu’utilisateur, je veux **clôturer un prêt**, afin d’indiquer que l’objet m’a été rendu. | **Étant donné** qu’un prêt est en cours<br>**Quand** je clique “Objet rendu”<br>**Alors** l’objet redevient disponible.<br><br>Un prêt déjà clôturé ne peut pas l’être à nouveau. |

## FS1 — Création de compte & profil

| ID    | User Story                                                                 | Critères d’acceptation |
|-------|-----------------------------------------------------------------------------|-------------------------|
| US1.1 | En tant que nouvel utilisateur, je veux créer un compte afin d’accéder au service. | - L’utilisateur peut saisir email + mot de passe.<br>- Un email déjà utilisé est refusé.<br>- Un message confirme la création du compte. |
| US1.2 | En tant qu’utilisateur connecté, je veux modifier mes informations personnelles. | - L’utilisateur peut modifier nom, email, adresse.<br>- Une confirmation apparaît après modification.<br>- Les champs invalides sont refusés. |
| US1.3 | En tant qu’utilisateur, je veux ajouter une photo de profil. | - Upload d’une image valide (png, jpg).<br>- Prévisualisation visible.<br>- L’image est affichée sur le profil. |

## FS2 — Publication d’objet

| ID    | User Story                                                                 | Critères d’acceptation |
|-------|-----------------------------------------------------------------------------|-------------------------|
| US2.1 | En tant qu’utilisateur, je veux publier un objet avec description et état. | - Les champs obligatoires sont vérifiés.<br>- L’objet apparaît dans la liste publique.<br>- Un message confirme la publication. |
| US2.2 | En tant qu’utilisateur, je veux ajouter une photo à mon objet. | - Upload d’image valide.<br>- L’image s’affiche dans la fiche objet. |
| US2.3 | En tant qu’utilisateur, je veux modifier ou supprimer mes objets. | - La modification met à jour la fiche.<br>- La suppression retire l’objet de la liste. |

## FS3 — Recherche

| ID    | User Story                                                                 | Critères d’acceptation |
|-------|-----------------------------------------------------------------------------|-------------------------|
| US3.1 | En tant qu’utilisateur, je veux rechercher un objet par mot-clé. | - Les résultats correspondent au mot-clé.<br>- Message si aucun résultat. |
| US3.2 | En tant qu’utilisateur, je veux filtrer les objets par catégorie. | - Les filtres sont cumulables.<br>- La liste se met à jour instantanément. |
| US3.3 | En tant qu’utilisateur, je veux filtrer les objets par localisation. | - Recherche par code postal ou rayon km.<br>- Les résultats sont triés par proximité. |

## FS4 — Demande de prêt

| ID    | User Story                                                                 | Critères d’acceptation |
|-------|-----------------------------------------------------------------------------|-------------------------|
| US4.1 | En tant qu’utilisateur, je veux envoyer une demande de prêt. | - Formulaire avec dates + message.<br>- Le propriétaire reçoit la demande.<br>- La demande apparaît dans le tableau de bord. |
| US4.2 | En tant que propriétaire, je veux être notifié lorsqu’une demande arrive. | - Notification visible.<br>- Email ou push selon configuration. |

## FS5 — Gestion des demandes

| ID    | User Story                                                                 | Critères d’acceptation |
|-------|-----------------------------------------------------------------------------|-------------------------|
| US5.1 | En tant que propriétaire, je veux accepter ou refuser une demande. | - Boutons Accepter/Refuser fonctionnels.<br>- Le demandeur est notifié. |
| US5.2 | En tant que demandeur, je veux être notifié d’une réponse. | - Notification affichée.<br>- Statut mis à jour. |

## FS6 — Suivi des prêts

| ID    | User Story                                                                 | Critères d’acceptation |
|-------|-----------------------------------------------------------------------------|-------------------------|
| US6.1 | En tant qu’utilisateur, je veux voir l’état de mes prêts. | - Statuts visibles.<br>- Mise à jour automatique. |
| US6.2 | En tant que propriétaire, je veux indiquer quand l’objet est récupéré/rendu. | - Boutons Remis/Rendu.<br>- Statut mis à jour. |

## FS7 — Avis

| ID    | User Story                                                                 | Critères d’acceptation |
|-------|-----------------------------------------------------------------------------|-------------------------|
| US7.1 | En tant qu’utilisateur, je veux noter un autre utilisateur après un prêt. | - Note 1–5.<br>- Commentaire optionnel.<br>- 1 avis par prêt. |
| US7.2 | En tant qu’utilisateur, je veux voir les avis dans un profil. | - Liste visible.<br>- Moyenne affichée. |


## Diagramme de cas d’usage (ASCII)

```
                 +-----------------------+
                 |   Système de prêt     |
                 +-----------------------+
                     /            \
                    /              \
               (Gérer objets)          (Gérer prêts)
                  /   |\                    /   |   \
                 /    | \                  /    |    \
                /     |  \                /     |     \
               /      |   \              /      |      \
              /       |    \            /       |       \
             v        v     v          v        v        v

         +--------+  +----------+  +----------+
         |Ajouter |  |Modifier  |  |Supprimer |
         |un objet|  |un objet  |  |un objet  |
         +--------+  +----------+  +----------+

                                  +-------------+
                                  |Créer un prêt|
                                  +-------------+
                                  +------------------+
                                  |Clôturer un prêt  |
                                  +------------------+
                                  +------------------+
                                  |Recevoir notif    |
                                  | retour imminent  |
                                  +------------------+

               ^                                        ^
               |                                        |
          +----------+                           +-------------+
          | Utilisateur |----------------------->|   Contact   |
          +----------+                           +-------------+

```
