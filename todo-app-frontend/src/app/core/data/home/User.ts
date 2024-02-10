import { SocialAccount } from "@core/data/home/SocialAccount";

export interface User {
    name: string;
    surname: string;
    username: string;
    socialAccounts: SocialAccount[];
}
