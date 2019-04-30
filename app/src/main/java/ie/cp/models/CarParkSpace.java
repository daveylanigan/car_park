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
    public String carParkSpaceDescription;
    public String carParkId;
    public boolean booked;

    public CarParkSpace() {}

    public CarParkSpace(String carParkSpaceId, String carParkSpaceName, String carParkSpaceDescription, String carParkId, boolean booked)
    {
 //       this.carParkSpaceId = UUID.randomUUID().toString();
        this.carParkSpaceId = carParkSpaceId;
        this.carParkSpaceName = carParkSpaceName;
        this.carParkSpaceDescription = carParkSpaceDescription;
        this.carParkId = carParkId;
        this.booked = booked;
    }

    @Override
    public String toString() {
        return carParkSpaceName;
    }
}
