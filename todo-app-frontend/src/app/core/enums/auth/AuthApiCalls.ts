export const enum AuthApiCalls {
    REGISTER_URL = "/api/auth/register",
    LOGIN_URL = "/api/auth/login",
    REFRESH_URL = "/api/auth/refreshToken",

    ERROR_FOUND = '{"authToken": "", "refreshToken": ""}',
    LOGOUT_URL = "/api/auth/logout"
}
