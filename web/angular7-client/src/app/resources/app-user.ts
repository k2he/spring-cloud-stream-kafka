export interface AppUser {
    username: string;
    name: string;
    imageUrl: string;
    authorities: Authority[]
}


export interface Authority {
    authority: string;
}