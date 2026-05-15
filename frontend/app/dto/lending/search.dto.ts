import type { ELendingStatus } from "~/enums/lending/status.enum";

export interface ISearchLendingDto {
    objectName?: string;
    borrowerName?: string;
    startDate?: Date;
    endDate?: Date;  
    status?: ELendingStatus;
}