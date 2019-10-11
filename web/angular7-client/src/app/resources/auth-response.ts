import { AppUser } from './app-user';

export interface AuthResponse {
    token: string;
    user: AppUser;
}