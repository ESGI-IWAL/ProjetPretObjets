import type { ELendingStatus } from "~/enums/lending/status.enum";

export interface ISearchLendingDto {
    objectName: string;
    borrowerName: string;
    startAt: Date | null;
    endAt: Date| null;  
    status: ELendingStatus | null;
}