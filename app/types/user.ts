export interface IUser {
    id:number,
    pseudo: string,
    avatar: string,
    email:string,
    description:string
}

export interface IUserAuth extends IUser {
    token: string
}