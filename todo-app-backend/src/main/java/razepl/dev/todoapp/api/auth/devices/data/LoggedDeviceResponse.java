package razepl.dev.todoapp.api.auth.devices.data;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record LoggedDeviceResponse(LocalDate dateOfLogin, String ipAddress, String deviceType,
                                   String timeOfLogin) {
}
