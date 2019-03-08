package ie.cp.fragments;

import android.widget.Filter;

import ie.cp.adapters.CarParkListAdapter;
import ie.cp.db.DBManager;
import ie.cp.models.CarPark;
import io.realm.Case;
import io.realm.OrderedRealmCollection;
import io.realm.RealmResults;


public class CarParkFilter  extends Filter {
    private OrderedRealmCollection<CarPark> originalCarParkList;
    private RealmResults<CarPark> realmCarParkResults;
    private CarParkListAdapter adapter;
    private boolean favourites = false;
    private DBManager dbManager;

    public CarParkFilter(DBManager dbManager, CarParkListAdapter adapter) {
        super();
        this.dbManager = dbManager;
        this.originalCarParkList = dbManager.getAllCarParks();
        this.adapter = adapter;
    }

    public void setFilter(String filterText) {
        //favourites = filterText.equals("all") ? false : true;
        favourites = !filterText.equals("all");
    }

    @Override
    protected FilterResults performFiltering(CharSequence prefix) {
        return new FilterResults();
    }

    @Override
    protected void publishResults(CharSequence prefix, FilterResults results) {

        if ((prefix == null || prefix.length() == 0))
    //        if(!favourites)
                realmCarParkResults = dbManager.getAllCarParks();
     //       else
     //           realmCarParkResults = dbManager.getFavourites();
        else {
            realmCarParkResults = dbManager.realmDatabase
                    .where(CarPark.class)
            //        .equalTo("favourite", favourites)
                    .contains("name", prefix.toString(), Case.INSENSITIVE)
         //           .or()
          //          .contains("shop", prefix.toString(), Case.INSENSITIVE)
                    .findAll();
        }

        adapter.carParkList = realmCarParkResults;

        if (adapter.carParkList.size() > 0)
            adapter.notifyDataSetChanged();
        else {
            adapter.notifyDataSetInvalidated();
            adapter.carParkList = originalCarParkList;
        }
    }
}
