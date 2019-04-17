package itg8.com.labtestingapp.common.genericRv;

public interface ViewModel<T> {
    int layoutId();
    void setModel(T t);
}
