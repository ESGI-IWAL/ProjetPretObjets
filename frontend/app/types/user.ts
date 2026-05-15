export interface IUser {
    id:number,                                         
    pseudo: string,
    firstName: string,
    lastName: string,
    image: string,
    description:string
}

export interface IUserAuth extends IUser {
    token: string
}