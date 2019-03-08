package ie.cp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ie.cp.R;
import ie.cp.models.CarPark;

public class CarParkListAdapter extends ArrayAdapter<CarPark> {

    private Context context;
    private View.OnClickListener deleteListener;
    public List<CarPark> carParkList;

    public CarParkListAdapter(Context context, View.OnClickListener deleteListener, List<CarPark> carParkList)
    {
        super(context, R.layout.carparkrow, carParkList);

        this.context = context;
        this.deleteListener = deleteListener;
        this.carParkList = carParkList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CarParkItem item = new CarParkItem(context, parent,
                deleteListener, carParkList.get(position));
        return item.view;
    }

    @Override
    public int getCount() {
        return carParkList.size();
    }

    @Override
    public CarPark getItem(int position) {
        return carParkList.get(position);
    }

}
