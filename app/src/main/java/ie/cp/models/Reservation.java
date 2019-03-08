package ie.cp.models;

import java.io.Serializable;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

// this class holds details of the car park space bookings that users have made

public class Reservation extends RealmObject
{
    @PrimaryKey
    public String reservationId;
    public String userId;
    public String carParkId;
    public String carParkSpaceId;

    public Reservation() {}

    public Reservation(String userId, String carParkId, String carParkSpaceId)
    {
        this.reservationId = UUID.randomUUID().toString();
        this.userId = userId;
        this.carParkId = carParkId;
        this.carParkSpaceId = carParkSpaceId;
    }

    @Override
    public String toString() {
        return "Reservation [userId=" + userId
                + ", carParkId =" + carParkId + ", carParkSpaceId=" + carParkSpaceId + "]";
     }
}
