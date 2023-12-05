package razepl.dev.todoapp.api.devices;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.todoapp.api.devices.data.LoggedDeviceResponse;
import razepl.dev.todoapp.api.devices.interfaces.DeviceController;
import razepl.dev.todoapp.api.devices.interfaces.DeviceService;
import razepl.dev.todoapp.entities.user.User;

import java.util.List;

import static razepl.dev.todoapp.api.devices.constants.DeviceMappings.DEVICE_MAPPING;
import static razepl.dev.todoapp.api.devices.constants.DeviceMappings.GET_LOGGED_DEVICES;

@RestController
@RequestMapping(value = DEVICE_MAPPING)
@RequiredArgsConstructor
public class DeviceControllerImpl implements DeviceController {
    private final DeviceService deviceService;

    @Override
    @GetMapping(value = GET_LOGGED_DEVICES)
    public final List<LoggedDeviceResponse> getLoggedDevicesOnPage(@RequestParam int page,
                                                                   @AuthenticationPrincipal User user) {
        return deviceService.getLoggedDevicesOnPage(page, user);
    }
}
