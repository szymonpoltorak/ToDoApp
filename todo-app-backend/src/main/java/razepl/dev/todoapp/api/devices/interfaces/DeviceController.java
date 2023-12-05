package razepl.dev.todoapp.api.devices.interfaces;

import razepl.dev.todoapp.api.devices.data.LoggedDeviceResponse;
import razepl.dev.todoapp.entities.user.User;

import java.util.List;

@FunctionalInterface
public interface DeviceController {
    List<LoggedDeviceResponse> getLoggedDevicesOnPage(int page, User user);
}
