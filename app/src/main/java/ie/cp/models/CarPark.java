package ie.cp.models;

import java.io.Serializable;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.util.UUID;

// this class holds car park details
public class CarPark  implements Serializable
{
    @PrimaryKey
    public String carParkId;
    public String carParkName;
    public String address;
    public String location;
    public String spacesAvailable;
    public String totalSpaces;
    public double latitude;
    public double longitude;

    public CarPark() {}

    public CarPark(String carParkId, String carParkName, String address, String location, String spacesAvailable,String totalSpaces, double lat, double lng)
    {
    //    this.carParkId = UUID.randomUUID().toString();
        this.carParkId = carParkId;
        this.carParkName = carParkName;
        this.address = address;
        this.location = location;
        this.spacesAvailable = spacesAvailable;
        this.totalSpaces = totalSpaces;
        this.latitude = lat;
        this.longitude = lng;
    }

    @Override
    public String toString() {
        return carParkName;
    }
}