export interface ICreateUserDto {
    userInfo: {
        userName: string,
        firstName: string,
        lastName: string,
    };
    email: string;
    password: string;
}