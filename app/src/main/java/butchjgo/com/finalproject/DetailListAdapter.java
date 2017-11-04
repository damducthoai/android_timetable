package butchjgo.com.finalproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by root on 11/4/2017.
 */

public class DetailListAdapter extends ArrayAdapter<DetailModel> {
    Context mContext;
    List<DetailModel> data;
    int mResource;

    public DetailListAdapter(@NonNull Context context, int resource, @NonNull List<DetailModel> objects) {
        super(context, resource, objects);
        mContext = context;
        data = objects;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DetailModel detail = data.get(position);
        int slotNum = detail.getSlotNum();
        String subject = detail.getSubject();
        String location = detail.getLocation();
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        TextView txtSlotNum = ((TextView) convertView.findViewById(R.id.txtSlot));
        TextView txtSubject = ((TextView) convertView.findViewById(R.id.txtSubject));
        TextView txtLocation = ((TextView) convertView.findViewById(R.id.txtLocation));

        txtSlotNum.setText(String.format("Slot: %d", slotNum));
        txtSubject.setText(String.format("Subject: %s", subject));
        txtLocation.setText(String.format("Location: %s", location));

        return convertView;

    }
}
