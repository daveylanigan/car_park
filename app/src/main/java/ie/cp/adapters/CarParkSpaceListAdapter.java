package ie.cp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ie.cp.R;
import ie.cp.models.CarParkSpace;

public class CarParkSpaceListAdapter extends ArrayAdapter<CarParkSpace> {

    private Context context;
    private View.OnClickListener deleteListener;
    public List<CarParkSpace> carParkSpaceList;

    public CarParkSpaceListAdapter(Context context, View.OnClickListener deleteListener, List<CarParkSpace> carParkSpaceList)
    {
        super(context, R.layout.carparkspacerow, carParkSpaceList);

        this.context = context;
        this.deleteListener = deleteListener;
        this.carParkSpaceList = carParkSpaceList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CarParkSpaceItem item = new CarParkSpaceItem(context, parent,
                deleteListener, carParkSpaceList.get(position));
        return item.view;
    }

    @Override
    public int getCount() {
        return carParkSpaceList.size();
    }

    @Override
    public CarParkSpace getItem(int position) {
        return carParkSpaceList.get(position);
    }

}
