import type { ILending } from "~/types/lending"
import type { ICreateLendingDto } from "../dto/lending/create.dto"
import type { ISearchLendingDto, ISearchLendingPeriodDto } from "../dto/lending/search.dto"
import type { IUpdateLendingDto } from "~/dto/lending/update.dto"
import type { IObjectInfoDisponibilityDto, ISearchLendingWithIdsObjectsDto } from "~/dto/object/search.dto"

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

export const getLendingsOfConnectedUser = async () => {
  return await api()<ILending[]>("/lendings/lent")
}

export const getLendingById = async (id: number) => {
  return await api()<ILending>(`/lendings/${id}`)
}

export const searchLending = async (searchParams: Omit<ISearchLendingDto, "date">) => {
  return await api()<ILending[]>("/lendings/search", {
    method: "POST",
    body: searchParams
  })
}

export const searchLendingWithObjectsIds= async(searchParams : ISearchLendingWithIdsObjectsDto) => {
  return await api()<IObjectInfoDisponibilityDto[]>("/lendings/objects/disponibility", {
    method: "POST", 
    body: searchParams
  })
}
export const updateLending = async ( dto: IUpdateLendingDto) => {
  const startAt = dto.startedAt ?? null
  const endAt = dto.endedAt ?? null

  return await api()(`/lendings/${dto.id}`, {
    method: "PUT",
    body: {
      startAt,
      endAt,
      ...(dto.status ? { status: dto.status } : {})
    }
  })
}

export const deleteLending = async (id: number) => {
  return await api()(`/lendings/${id}`, {
    method: "DELETE"
  })
}

export const searchLendingsOnDateByIdObject = async (objectId: number) => {
  return await api()<ISearchLendingPeriodDto[]>(`/lendings/objects/${objectId}/periods`)
}
// GET all
// GET by ID
// POST RECHERCHE { via objet, via utilisateur, via date debut, via date fin}
// POST CREATION { objet, utilisateur, date debut, date fin}
// PUT by ID { date fin }
// DELETE by Id  