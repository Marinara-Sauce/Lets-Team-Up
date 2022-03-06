export interface User {
    id: number;
    name: string;
    passwordHash: string;
    twitter: string;
    linkedin: string;
    github: string;
    email: string;
    exposeEmail: boolean;
    skills: string;
}