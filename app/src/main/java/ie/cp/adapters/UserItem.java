package ie.cp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ie.cp.R;
import ie.cp.models.User;

public class UserItem {
    public View view;

    public UserItem(Context context, ViewGroup parent,
                    View.OnClickListener deleteListener, User user)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.carparkcard, parent, false);
        view.setTag(user.userId);

        updateControls(user);

        ImageView imgDelete = view.findViewById(R.id.rowDeleteImg);
        imgDelete.setTag(user);
        imgDelete.setOnClickListener(deleteListener);
    }

    private void updateControls(User user) {
        ((TextView) view.findViewById(R.id.rowUserName)).setText(user.userName);

        ((TextView) view.findViewById(R.id.rowUserEmailAddress)).setText(user.emailAddress);
        ((TextView) view.findViewById(R.id.rowUserSpacesBooked)).setText(user.password);

        ImageView imgIcon = view.findViewById(R.id.rowFavouriteImg);


    }
}
