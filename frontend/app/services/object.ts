import type { ICreateObjectDto } from "~/dto/object/create.dto";

export const createObject = async (dto: ICreateObjectDto) => {
  return await $fetch("/objects", {
    method: "POST",
    body: dto
  })
}

export const getObjects = async () => {
  return await $fetch("/objects")
}

export const getObjectById = async (id: number) => {
  return await $fetch(`/objects/${id}`)
}

export const searchObjects = async (searchParams: any) => {
  return await $fetch("/objects/search", {
    method: "POST",
    body: searchParams
  })
}

// GET all
// GET by ID
// POST RECHERCHE { via categorie, via pret en cours ou non, via date de creation}
// POST CREATION { image, nom, description, categorie, disponibilite, poids, dimensions, usure, matiere}
// PUT by ID {  image, nom, description, categorie, disponibilite, poids, dimensions, usure, matiere}
// PUT by ID { etat de pret, date de debut, date de fin, emprunteur}
// DELETE by Id  