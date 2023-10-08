package razepl.dev.todoapp.config.constants;

import java.util.List;

public final class CorsConfig {
    public static final List<String> ALLOWED_REQUESTS = List.of("GET", "POST", "DELETE", "PATCH");

    public static final List<String> CORS_ADDRESSES = List.of(
            "http://localhost:80", "http://frontend:80", "http://localhost:82", "http://backend:82"
    );

    public static final String CONTENT_TYPE_HEADER = "Content-Type";

    public static final String API_PATTERN = "/api/**";

    private CorsConfig() {
    }
}