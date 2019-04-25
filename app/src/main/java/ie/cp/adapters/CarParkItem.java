package ie.cp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ie.cp.R;
import ie.cp.models.CarPark;

public class CarParkItem  {
    public View view;

    public CarParkItem(Context context, ViewGroup parent,
                      View.OnClickListener deleteListener, CarPark carpark)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.carparkcard, parent, false);
        view.setTag(carpark.carParkId);

        updateControls(carpark);

        ImageView imgDelete = view.findViewById(R.id.rowDeleteImg);
        imgDelete.setTag(carpark);
        imgDelete.setOnClickListener(deleteListener);

        ImageView imgSpaces = view.findViewById(R.id.rowSpaceImg);
        imgSpaces.setTag(carpark);
        imgSpaces.setOnClickListener(deleteListener);
    }

    private void updateControls(CarPark carpark) {
        ((TextView) view.findViewById(R.id.rowCarParkName)).setText(carpark.carParkName);
        ((TextView) view.findViewById(R.id.rowCarParkAddress)).setText(carpark.address);
        // get the number of car park spaces
        ((TextView) view.findViewById(R.id.rowTotalSpaces)).setText(carpark.totalSpaces);
        ((TextView) view.findViewById(R.id.rowSpacesAvailable)).setText(carpark.spacesAvailable);

    }
}
