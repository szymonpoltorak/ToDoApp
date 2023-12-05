package razepl.dev.todoapp.api.auth.devices.interfaces;

import razepl.dev.todoapp.api.auth.devices.data.LoggedDeviceResponse;
import razepl.dev.todoapp.entities.user.User;

import java.util.List;

@FunctionalInterface
public interface DeviceService {
    List<LoggedDeviceResponse> getLoggedDevicesOnPage(int page, User user);
}
