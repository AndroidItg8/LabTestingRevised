package itg8.com.labtestingapp.common.genericRv;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseViewHolder(ViewDataBinding itemView) {
        super(itemView.getRoot());
        setBindable(itemView);
    }

    public abstract void setBindable(ViewDataBinding bindable);

    public abstract void bind(T object);
}
