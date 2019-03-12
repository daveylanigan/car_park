package ie.cp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ie.cp.R;
import ie.cp.models.User;

public class UserListAdapter extends ArrayAdapter<User> {

    private Context context;
    private View.OnClickListener deleteListener;
    public List<User> userList;

    public UserListAdapter(Context context, View.OnClickListener deleteListener, List<User> userList)
    {
        super(context, R.layout.userrow, userList);

        this.context = context;
        this.deleteListener = deleteListener;
        this.userList = userList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserItem item = new UserItem(context, parent,
                deleteListener, userList.get(position));
        return item.view;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public User getItem(int position) {
        return userList.get(position);
    }

}
