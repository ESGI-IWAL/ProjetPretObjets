# User Stories – Site de prêt d’objets

| ID  | User Story | Critères d'acceptation |
|-----|------------|-------------------------|
| **US1** | En tant qu'utilisateur, je veux **ajouter un objet à prêter**, afin de gérer mon inventaire. | **Étant donné** que je suis connecté<br>**Quand** j’accède à "Ajouter un objet" et renseigne nom + description + état<br>**Alors** l’objet est enregistré.<br><br>**Étant donné** que je ne renseigne pas de nom<br>**Alors** une erreur apparaît. |
| **US2** | En tant qu'utilisateur, je veux **enregistrer un prêt**, afin de savoir à qui et quand j’ai prêté un objet. | **Étant donné** que j’ai des objets et des contacts<br>**Quand** je crée un prêt valide<br>**Alors** l’objet devient indisponible.<br><br>**Étant donné** que l’objet est déjà prêté<br>**Alors** la création est refusée. |
| **US3** | En tant qu’utilisateur, je veux **être notifié quand la date de retour approche**, afin de penser à récupérer mon objet. | **Étant donné** qu’un prêt a une date de retour prévue<br>**Quand** il reste moins de 48h<br>**Alors** une notification est envoyée.<br><br>Une seule notification par prêt. |
| **US4** | En tant qu’utilisateur, je veux **voir la liste des objets prêtés**, afin d’avoir une vue rapide de leur disponibilité. | **Étant donné** qu’il y a des objets prêtés<br>**Quand** j’ouvre la page des objets prêtés<br>**Alors** la liste apparaît.<br><br>Sinon : “Aucun objet prêté actuellement”. |
| **US5** | En tant qu’utilisateur, je veux **clôturer un prêt**, afin d’indiquer que l’objet m’a été rendu. | **Étant donné** qu’un prêt est en cours<br>**Quand** je clique “Objet rendu”<br>**Alors** l’objet redevient disponible.<br><br>Un prêt déjà clôturé ne peut pas l’être à nouveau. |

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
