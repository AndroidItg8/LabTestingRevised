package itg8.com.labtestingapp.common;

import android.support.v7.widget.RecyclerView;

import java.util.List;

public abstract class  CustomRVAdapter<V,E> extends RecyclerView.Adapter {

    public  abstract void updateData(List<E> data);

}
