package ie.cp.db;

import android.content.Context;
import android.database.SQLException;

import ie.cp.models.CarPark;
import ie.cp.models.CarParkSpace;
import ie.cp.models.Reservation;
import ie.cp.models.User;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class DBManager {

    public Realm realmDatabase;

    public DBManager(Context context) {
        Realm.init(context);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("carpark.realm")
                .schemaVersion(1)
                .build();

        Realm.setDefaultConfiguration(config);
    }

    public void open() throws SQLException {
        realmDatabase = Realm.getDefaultInstance();
    }

    public void close() {
        realmDatabase.close();
    }

    public void addUser(User c) {
        realmDatabase.beginTransaction();
        realmDatabase.copyToRealm(c);
        realmDatabase.commitTransaction();
    }

    public void addCarPark(CarPark c) {
        realmDatabase.beginTransaction();
        realmDatabase.copyToRealm(c);
        realmDatabase.commitTransaction();
    }

    public void addCarParkSpace(CarParkSpace c) {
        realmDatabase.beginTransaction();
        realmDatabase.copyToRealm(c);
        realmDatabase.commitTransaction();
    }

    public void addReservation(Reservation c) {
        realmDatabase.beginTransaction();
        realmDatabase.copyToRealm(c);
        realmDatabase.commitTransaction();
    }

    public void updateUser(User c, String userName ,String emailAddress, String password)
    {
        realmDatabase.beginTransaction();
        c.userName = userName;
        c.emailAddress = emailAddress;
        c.password = password;
        realmDatabase.commitTransaction();
    }

    public void updateCarPark(CarPark c, String name ,String address, String location, String spacesAvailable, String totalSpaces)
    {
        realmDatabase.beginTransaction();
        c.carParkName = name;
        c.address = address;
        c.location = location;
        c.spacesAvailable = spacesAvailable;
        c.totalSpaces = totalSpaces;
        realmDatabase.commitTransaction();
    }

    public void updateCarParkTotals(String carParkName ,boolean increment, boolean decrement)
    {

        CarPark foundCarPark =  realmDatabase.where(CarPark.class)
                .equalTo("carParkName",carParkName)
                .findAll()
                .first();

        if (foundCarPark.isValid()) {
            realmDatabase.beginTransaction();
            if (increment) {
                foundCarPark.spacesAvailable = Integer.toString(Integer.parseInt(foundCarPark.spacesAvailable) + 1);
                foundCarPark.totalSpaces = Integer.toString(Integer.parseInt(foundCarPark.totalSpaces) + 1);
            }
            else {

                if (decrement && Integer.parseInt(foundCarPark.spacesAvailable) > 0) {
                    foundCarPark.spacesAvailable = Integer.toString(Integer.parseInt(foundCarPark.spacesAvailable) - 1);
                }
                if (decrement && Integer.parseInt(foundCarPark.totalSpaces) > 0) {
                    foundCarPark.totalSpaces = Integer.toString(Integer.parseInt(foundCarPark.totalSpaces) - 1);
                }
            }

            realmDatabase.commitTransaction();
        }

    }

    public void updateCarParkBookedSpace(String carParkName ,boolean increment, boolean decrement)
    {

        CarPark foundCarPark =  realmDatabase.where(CarPark.class)
                .equalTo("carParkName",carParkName)
                .findAll()
                .first();

        if (foundCarPark.isValid()) {
            realmDatabase.beginTransaction();
            if (increment) {
                foundCarPark.spacesAvailable = Integer.toString(Integer.parseInt(foundCarPark.spacesAvailable) + 1);
            }
            else {

                if (decrement && Integer.parseInt(foundCarPark.spacesAvailable) > 0) {
                    foundCarPark.spacesAvailable = Integer.toString(Integer.parseInt(foundCarPark.spacesAvailable) - 1);
                }
            }

            realmDatabase.commitTransaction();
        }

    }

    public void updateCarParkSpace(CarParkSpace c, String name ,String carParkSpaceDescription, String carParkId, boolean booked)
    {
        realmDatabase.beginTransaction();
        c.carParkSpaceName = name;
        c.carParkSpaceDescription = carParkSpaceDescription;
        c.carParkId = carParkId;
        c.booked = booked;
        realmDatabase.commitTransaction();
    }

    public void updateReservation(Reservation c, String userId ,String carParkId, String carParkSpaceId)
    {
        realmDatabase.beginTransaction();
        c.userId = userId;
        c.carParkId = carParkId;
        c.carParkSpaceId = carParkSpaceId;
        realmDatabase.commitTransaction();
    }

    public void resetReservation() {
        realmDatabase.beginTransaction();
        realmDatabase.where(Reservation.class)
                .findAll()
                .deleteAllFromRealm();
        realmDatabase.commitTransaction();
    }

    public void resetUser() {
        realmDatabase.beginTransaction();
        realmDatabase.where(User.class)
                .findAll()
                .deleteAllFromRealm();
        realmDatabase.commitTransaction();
    }

    public void resetCarParkSpace() {
        realmDatabase.beginTransaction();
        realmDatabase.where(CarParkSpace.class)
                .findAll()
                .deleteAllFromRealm();
        realmDatabase.commitTransaction();
        // ensure referential Integrity by deleting reservations
        resetReservation();
    }

    public void resetCarPark() {
        realmDatabase.beginTransaction();
        realmDatabase.where(User.class)
                .findAll()
                .deleteAllFromRealm();
        realmDatabase.commitTransaction();
        // ensure referential Integrity by deleting reservations
        resetReservation();
        // ensure referential Integrity by deleting car park spaces
        resetCarParkSpace();
    }

    public RealmResults<User> getAllUsers() {
        RealmResults<User> result = realmDatabase.where(User.class)
                .findAll();
        return result;
    }

    public RealmResults<CarPark> getAllCarParks() {
        RealmResults<CarPark> result = realmDatabase.where(CarPark.class)
                .findAll();
        return result;
    }

    public RealmResults<CarParkSpace> getAllCarParkSpaces() {
        RealmResults<CarParkSpace> result = realmDatabase.where(CarParkSpace.class)
                .findAll();
        return result;
    }

    public RealmResults<CarParkSpace> getCarParkSpaces(String carParkId) {
        // get available spaces for a particular car park
        RealmResults<CarParkSpace> result = realmDatabase.where(CarParkSpace.class)
                .equalTo("carParkId",carParkId)
                .equalTo("booked",false)
                .findAll();
        return result;
    }

    public RealmResults<Reservation> getAllReservations() {
        RealmResults<Reservation> result = realmDatabase.where(Reservation.class)
                .findAll();
        return result;
    }

    public User getUser(String userId) {
        return realmDatabase.where(User.class)
                .equalTo("userId",userId)
                .findAll()
                .first();
    }

    public CarPark getCarPark(String carParkId) {
        return realmDatabase.where(CarPark.class)
                .equalTo("carParkId",carParkId)
                .findAll()
                .first();
    }
    public CarParkSpace getCarParkSpace(String carParkSpaceId) {
        return realmDatabase.where(CarParkSpace.class)
                .equalTo("carParkSpaceId",carParkSpaceId)
                .findAll()
                .first();
    }
    public CarParkSpace getCarParkSpaceByName(String carParkSpaceName) {

        RealmQuery<CarParkSpace> query = realmDatabase.where(CarParkSpace.class).equalTo("carParkSpaceName", "MultiStory 1");
        RealmResults<CarParkSpace> result = query.findAll();

        if (result.size()>0) {
            return result.get(0);
        }else{
            return null;
        }

    }
    public Reservation getReservation(String reservationId) {
        return realmDatabase.where(Reservation.class)
                .equalTo("reservationId",reservationId)
                .findAll()
                .first();
    }

    public void deleteUser(String userId) {
        realmDatabase.beginTransaction();
        realmDatabase.where(User.class)
                .equalTo("userId",userId)
                .findAll()
                .deleteAllFromRealm();
        realmDatabase.commitTransaction();
    }

    public void ReserveSpace(CarParkSpace c) {
        realmDatabase.beginTransaction();
        realmDatabase.where(CarParkSpace.class)
                .equalTo("carParkSpaceId",c.carParkSpaceId)
                .findAll()
                .setBoolean("booked", true);
        realmDatabase.commitTransaction();

        // now update the car park with the number of spaces available
        updateCarParkBookedSpace(c.carParkId ,false, true);
    }
    public void deleteCarPark(String carParkId) {
        realmDatabase.beginTransaction();
        realmDatabase.where(CarPark.class)
                .equalTo("carParkId",carParkId)
                .findAll()
                .deleteAllFromRealm();
        realmDatabase.commitTransaction();
    }

    public void deleteCarParkSpace(String carParkSpaceId) {
        // use the carpark space to find the car park
        CarParkSpace c = realmDatabase.where(CarParkSpace.class)
                .equalTo("carParkSpaceId",carParkSpaceId)
                .findAll()
                .first();
        // find the car park
        CarPark cp = realmDatabase.where(CarPark.class)
                .equalTo("carParkName",c.carParkId)
                .findAll()
                .first();
        realmDatabase.beginTransaction();

            realmDatabase.where(CarParkSpace.class)
                    .equalTo("carParkSpaceId",carParkSpaceId)
                    .findAll()
                    .deleteAllFromRealm();

        realmDatabase.commitTransaction();
        // update the car park space (by name because of the dropdown having name only) available totals
        updateCarParkTotals(cp.carParkName,false,true);
    }

    public void deleteReservation(String reservationId) {
        realmDatabase.beginTransaction();
        realmDatabase.where(Reservation.class)
                .equalTo("reservationId",reservationId)
                .findAll()
                .deleteAllFromRealm();
        realmDatabase.commitTransaction();
    }


}
