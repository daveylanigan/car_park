package ie.cp.models;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.util.UUID;

// this class holds car park details
public class CarPark extends RealmObject
{
    @PrimaryKey
    public String carParkId;
    public String name;
    public String address;
    public String location;
    public String spacesAvailable;
    public String totalSpaces;

    public CarPark() {}

    public CarPark(String name, String address, String location, String spacesAvailable,String totalSpaces)
    {
        this.carParkId = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
        this.location = location;
        this.spacesAvailable = spacesAvailable;
        this.totalSpaces = totalSpaces;
    }

    @Override
    public String toString() {
        return "CarPark [name=" + name
                + ", address =" + address + ", location=" + location + ", spacesAvailable=" + spacesAvailable
                + ", totalSpaces =" + totalSpaces + "]";
    }
}