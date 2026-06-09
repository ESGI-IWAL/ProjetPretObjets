export interface ICreateLendingDto {
    objectId: number;
    borrowerId: number;
    startAt: string;
    endAt: string|null;  
}