
export interface ISearchLendingDto {
    objectName: string;
    borrowerName: string;
    date: string | null;
}

export interface ISearchLendingPeriodDto {
        startedAt: string;
        endedAt?: string
}