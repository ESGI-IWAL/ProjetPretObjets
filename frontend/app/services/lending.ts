import type { ILending } from "~/types/lending"
import type { ICreateLendingDto } from "../dto/lending/create.dto"
import type { ISearchLendingDto } from "../dto/lending/search.dto"
import type { IUpdateLendingDto } from "~/dto/lending/update.dto"

const api = () => useNuxtApp().$api

export const createLending = async (
  dto: ICreateLendingDto
) => {
  return await api()("/lendings", {
    method: "POST",
    body: dto
  })
}

export const getLendings = async () => {
  return await api()<ILending[]>("/lendings")
}

export const getLendingById = async (id: number) => {
  return await api()<ILending>(`/lendings/${id}`)
}

export const searchLending = async (searchParams: ISearchLendingDto) => {
  return await api()<ILending[]>("/lendings/search", {
    method: "POST",
    body: searchParams
  })
}

export const updateLending = async ( dto: IUpdateLendingDto) => {
  const startAt = dto.startAt instanceof Date ? dto.startAt.toISOString() : dto.startAt
  const endAt = dto.endAt instanceof Date ? dto.endAt.toISOString() : dto.endAt

  return await api()(`/lendings/${dto.id}`, {
    method: "PUT",
    body: {
      startAt,
      endAt
    }
  })
}

export const deleteLending = async (id: number) => {
  return await api()(`/lendings/${id}`, {
    method: "DELETE"
  })
}

// GET all
// GET by ID
// POST RECHERCHE { via objet, via utilisateur, via date debut, via date fin}
// POST CREATION { objet, utilisateur, date debut, date fin}
// PUT by ID { date fin }
// DELETE by Id  