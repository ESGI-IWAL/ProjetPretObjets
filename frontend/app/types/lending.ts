import type { ELendingStatus } from "~/enums/lending/status.enum";

export interface ILending {
  id: number;
  objectId: number;
  borrowerId: number;
  lenderId: number;
  startDate: Date;
  endDate: Date | null;
  status: ELendingStatus;
}