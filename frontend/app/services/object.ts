

import type { ICreateObjectDto } from "~/dto/object/create.dto";
import type { ISearchObjectDto } from "~/dto/object/search.dto";
import type { IUpdateObjectDto } from "~/dto/object/update.dto";
import type { IObject } from "~/types/object";

const api = () => useNuxtApp().$api

export const createObject = async (dto: ICreateObjectDto) => {
  return await api()("/objects", {
    method: "POST",
    body: dto
  })
}

export const getObjects = async () => {
  return await api()<IObject[]>("/objects")
}

export const getObjectById = async (id: number) => {
  return await api()<IObject>(`/objects/${id}`)
}

export const searchObjects = async (searchParams: ISearchObjectDto) => {
  return await api()<IObject[]>("/objects/search", {
    method: "POST",
    body: searchParams
  })
}

export const updateObject = async (dto: IUpdateObjectDto) => {
  return await api()<IObject>(`/objects/${dto.id}`, {
    method: "PUT",
    body: dto
  })
}

export const deleteObject = async (id: number) => {
  return await api()(`/objects/${id}`, {
    method: "DELETE"
  })
}

export const historyObjectById = async (id: number) => {
  return await api()<IObject[]>(`/objects/${id}/history`)
}

// GET all
// GET by ID
// POST RECHERCHE { via categorie, via pret en cours ou non, via date de creation}
// POST CREATION { image, nom, description, categorie, disponibilite, poids, dimensions, usure, matiere}
// PUT by ID {  image, nom, description, categorie, disponibilite, poids, dimensions, usure, matiere}
// DELETE by Id  
