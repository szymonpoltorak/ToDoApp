package razepl.dev.todoapp.config.oauth.constants;

public final class RedirectUrls {
    public static final String FAILURE_URL = "/auth/login";

    public static final String SUCCESS_URL = "/oauth";

    public static final String FRONTEND_URL_VALUE = "${frontend.url}";

    private RedirectUrls() {
    }
}
