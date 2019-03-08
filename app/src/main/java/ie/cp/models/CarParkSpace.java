package ie.cp.models;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


// this class holds details of all the spaces contained within the various car parks

public class CarParkSpace extends RealmObject
{
    @PrimaryKey
    public String carParkSpaceId;
    public String name;
    public String description;
    public String carParkId;

    public CarParkSpace() {}

    public CarParkSpace(String name, String description, String carParkId)
    {
        this.carParkSpaceId = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.carParkId = carParkId;
    }

    @Override
    public String toString() {
        return "CarParkSpace [name=" + name
                + ", description =" + description + ", carParkId=" + carParkId + "]";
    }
}
