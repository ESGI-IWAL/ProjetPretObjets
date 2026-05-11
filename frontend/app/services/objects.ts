export default defineEventHandler(async (event) => {
  return 'Hello Nitro'
})


// GET all
// GET by ID
// POST RECHERCHE { via categorie, via pret en cours ou non, via date de creation}
// POST CREATION { image, nom, description, categorie, disponibilite, poids, dimensions, usure, matiere}
// PUT by ID {  image, nom, description, categorie, disponibilite, poids, dimensions, usure, matiere}
// PUT by ID { etat de pret, date de debut, date de fin, emprunteur}
// DELETE by Id  