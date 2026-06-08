export interface ICreateLendingDto {
    objectId: number;
    borrowerId: number;
    startAt: string|null;
    endAt: string|null;  
}