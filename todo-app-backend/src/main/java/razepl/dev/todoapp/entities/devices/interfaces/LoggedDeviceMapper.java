package razepl.dev.todoapp.entities.devices.interfaces;

import org.mapstruct.Mapper;
import razepl.dev.todoapp.api.devices.data.LoggedDeviceResponse;
import razepl.dev.todoapp.entities.devices.LoggedDevice;

@Mapper(componentModel = "spring")
public interface LoggedDeviceMapper {
    LoggedDeviceResponse toLoggedDeviceResponse(LoggedDevice loggedDevice);
}
