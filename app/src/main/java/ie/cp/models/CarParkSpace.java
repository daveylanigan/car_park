package ie.cp.models;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


// this class holds details of all the spaces contained within the various car parks

public class CarParkSpace extends RealmObject
{
    @PrimaryKey
    public String carParkSpaceId;
    public String carParkSpaceName;
    public String description;
    public String carParkId;

    public CarParkSpace() {}

    public CarParkSpace(String carParkSpaceName, String description, String carParkId)
    {
        this.carParkSpaceId = UUID.randomUUID().toString();
        this.carParkSpaceName = carParkSpaceName;
        this.description = description;
        this.carParkId = carParkId;
    }

    @Override
    public String toString() {
        return "CarParkSpace [carParkSpaceName=" + carParkSpaceName
                + ", description =" + description + ", carParkId=" + carParkId + "]";
    }
}
