import { SocialPlatform } from "@enums/home/SocialPlatform";

export interface SocialAccount {
    socialAccountId: number;
    socialName: string;
    socialLink: string;
    socialPlatform: SocialPlatform;
}
