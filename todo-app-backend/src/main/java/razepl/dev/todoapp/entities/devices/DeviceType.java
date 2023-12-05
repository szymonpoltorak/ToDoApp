package razepl.dev.todoapp.entities.devices;

public enum DeviceType {
    DESKTOP,
    MOBILE;

    public static DeviceType getDeviceType(String userAgent) {
        if (userAgent.contains("Android") || userAgent.contains("iPhone")) {
            return MOBILE;
        }
        return DESKTOP;
    }
}
