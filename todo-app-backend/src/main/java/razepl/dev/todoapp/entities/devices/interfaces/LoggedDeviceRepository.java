package razepl.dev.todoapp.entities.devices.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import razepl.dev.todoapp.entities.devices.LoggedDevice;

@Repository
public interface LoggedDeviceRepository extends JpaRepository<LoggedDevice, Long> {
}
