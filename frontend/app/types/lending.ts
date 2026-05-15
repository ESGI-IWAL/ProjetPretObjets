import type { ELendingStatus } from "~/enums/lending/status.enum";
import type { IUser } from "./user";

export interface ILending {
  id: string;
  objectId: string;
  borrower: IUser;
  startDate: Date;
  endDate: Date | null;
  status: ELendingStatus;
}