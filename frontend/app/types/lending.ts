import type { ELendingStatus } from "~/enums/lending/status.enum";
import type { IUser } from "./user";
import type { IObject } from "./object";

export interface ILending {
  id: string;
  object: IObject;
  borrower: IUser;
  startDate: Date;
  endDate: Date | null;
  status: ELendingStatus;
}