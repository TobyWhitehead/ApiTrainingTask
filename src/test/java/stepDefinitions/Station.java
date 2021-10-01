package stepDefinitions;

public class Station {

    private String external_id;
    private String name;
    private double latitude;
    private double longitude;
    private int altitude;

    public String getExternal_id() {
        return external_id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getAltitude() {
        return altitude;
    }

    public void createStation(String external_id, String name, double latitude, double longitude, int altitude) {
        this.external_id = external_id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }
}
