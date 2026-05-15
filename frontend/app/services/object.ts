import type { ICreateObjectDto } from "~/dto/object/create.dto";
import type { ISearchObjectDto } from "~/dto/object/search.dto";
import type { IUpdateObjectDto } from "~/dto/object/update.dto";

  const api = useApi()

export const createObject = async (dto: ICreateObjectDto) => {
  return await api("/objects", {
    method: "POST",
    body: dto
  })
}

export const getObjects = async () => {
  return await api("/objects")
}

export const getObjectById = async (id: string) => {
  return await api(`/objects/${id}`)
}

export const searchObjects = async (searchParams: ISearchObjectDto) => {
  return await api("/objects/search", {
    method: "POST",
    body: searchParams
  })
}

export const updateObject = async (id: string, dto: IUpdateObjectDto) => {
  return await api(`/objects/${id}`, {
    method: "PUT",
    body: dto
  })
}

export const deleteObject = async (id: string) => {
  return await api(`/objects/${id}`, {
    method: "DELETE"
  })
}

export const historyObject = async (id: string) => {
  return await api(`/objects/${id}/history`)
}

// GET all
// GET by ID
// POST RECHERCHE { via categorie, via pret en cours ou non, via date de creation}
// POST CREATION { image, nom, description, categorie, disponibilite, poids, dimensions, usure, matiere}
// PUT by ID {  image, nom, description, categorie, disponibilite, poids, dimensions, usure, matiere}
// DELETE by Id  