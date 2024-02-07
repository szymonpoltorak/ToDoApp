import { SocialPlatform } from "@enums/home/SocialPlatform";

export interface SocialAccountRequest {
    socialName: string;
    socialLink: string;
    socialPlatform: SocialPlatform;
}
