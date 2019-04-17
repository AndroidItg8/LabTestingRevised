package itg8.com.labtestingapp.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;




import java.util.List;

import itg8.com.labtestingapp.R;

public class GenericSpinnerAdapter  extends BaseAdapter{


    private final Context applicationContext;
    private final List<SpinnerGenericModel> users;
//    private final onItemCLickedListener listener;

    public GenericSpinnerAdapter(Context context, List<SpinnerGenericModel> users) {

        this.applicationContext = context;
        this.users = users;
//        this.listener = listener;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public SpinnerGenericModel getItem(int position) {
        return (SpinnerGenericModel) users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_spinner_row, null);
        }

        convertView.setTag(users.get(position));
        TextView mTxtItem = (TextView )convertView.findViewById(R.id.txt_item);
        mTxtItem.setText(users.get(position).getValue());


        return convertView;
    }

    public int findItemPosition(String id){
        int i=0;
        if(users.size()>0) {
            for (SpinnerGenericModel m :
                    users) {
                if (m.getId().equalsIgnoreCase(id)) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }


    public int getItemPosition(String productType, Spinner searchableSpinner) {

        for (int i=0; i< users.size(); i++)
            if (users.get(i).getValue().equalsIgnoreCase(productType)){
                return i;

            }


        return -1;
    }

    public void updateList(List<SpinnerGenericModel> listFamily) {
        users.clear();
        users.addAll(listFamily);
        notifyDataSetChanged();
    }



}
