package razepl.dev.todoapp.config.constants;

import java.util.List;

public final class CorsConfig {
    public static final List<String> ALLOWED_REQUESTS = List.of("GET", "POST", "DELETE", "PATCH");

    public static final List<String> CORS_ADDRESSES = List.of(
            "http://localhost:4200", "http://todo-app-frontend:4200", "http://todo-app-frontend:80"
    );

    public static final String CONTENT_TYPE_HEADER = "Content-Type";

    public static final String API_PATTERN = "/api/**";

    private CorsConfig() {
    }
}
