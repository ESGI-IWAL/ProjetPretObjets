import type { ELendingStatus } from "~/enums/lending/status.enum";

export interface ISearchLendingDto {
    objectName?: string;
    borrowerName?: string;
    startAt?: Date;
    endAt?: Date;  
    status?: ELendingStatus;
}