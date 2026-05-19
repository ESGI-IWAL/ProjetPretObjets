export interface IUser {
    id:string,                                         
    pseudo: string,
    firstName: string,
    lastName: string,
    avatar: string,
    description:string
}

export interface IUserAuth extends IUser {
    token: string
}