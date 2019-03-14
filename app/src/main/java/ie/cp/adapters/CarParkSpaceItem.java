package ie.cp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ie.cp.R;
import ie.cp.models.CarParkSpace;

public class CarParkSpaceItem {
    public View view;

    public CarParkSpaceItem(Context context, ViewGroup parent,
                            View.OnClickListener deleteListener, CarParkSpace carparkSpace)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.carparkspacecard, parent, false);
        view.setTag(carparkSpace.carParkSpaceId);

        updateControls(carparkSpace);

        ImageView imgDelete = view.findViewById(R.id.rowDeleteImg);
        imgDelete.setTag(carparkSpace);
        imgDelete.setOnClickListener(deleteListener);
    }

    private void updateControls(CarParkSpace carparkSpace) {
        ((TextView) view.findViewById(R.id.rowCarParkSpaceName)).setText(carparkSpace.carParkSpaceName);
        ((TextView) view.findViewById(R.id.rowCarParkSpaceDescription)).setText(carparkSpace.carParkSpaceDescription);
  //      ((TextView) view.findViewById(R.id.rowCarPark)).setText(carparkSpace.carParkId);

    }
}
