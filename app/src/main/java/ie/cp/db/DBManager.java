package ie.cp.db;

import android.content.Context;
import android.database.SQLException;

import ie.cp.models.CarPark;
import ie.cp.models.CarParkSpace;
import ie.cp.models.Reservation;
import ie.cp.models.User;
import io.realm.Realm;
import io.realm.RealmConfiguration;
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

    public void updateCarPark(CarPark c, String name ,String address, String location, int spacesAvailable, int totalSpaces)
    {
        realmDatabase.beginTransaction();
        c.name = name;
        c.address = address;
        c.location = location;
        c.spacesAvailable = spacesAvailable;
        c.totalSpaces = totalSpaces;
        realmDatabase.commitTransaction();
    }

    public void updateCarParkSpace(CarParkSpace c, String name ,String description, String carParkId)
    {
        realmDatabase.beginTransaction();
        c.name = name;
        c.description = description;
        c.carParkId = carParkId;
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
    public CarParkSpace getCarParkSpace(String carParkpaceId) {
        return realmDatabase.where(CarParkSpace.class)
                .equalTo("carParkpaceId",carParkpaceId)
                .findAll()
                .first();
    }
    public Reservation getReservation(String reservationId) {
        return realmDatabase.where(Reservation.class)
                .equalTo("rservationId",reservationId)
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

    public void deleteCarPark(String carParkId) {
        realmDatabase.beginTransaction();
        realmDatabase.where(CarPark.class)
                .equalTo("carParkId",carParkId)
                .findAll()
                .deleteAllFromRealm();
        realmDatabase.commitTransaction();
    }

    public void deleteCarParkSpace(String carParkSpaceId) {
        realmDatabase.beginTransaction();
        realmDatabase.where(CarParkSpace.class)
                .equalTo("carParkSpaceId",carParkSpaceId)
                .findAll()
                .deleteAllFromRealm();
        realmDatabase.commitTransaction();
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
