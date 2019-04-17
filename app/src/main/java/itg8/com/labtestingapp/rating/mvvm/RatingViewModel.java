package itg8.com.labtestingapp.rating.mvvm;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Spinner;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import itg8.com.labtestingapp.MainActivity;
import itg8.com.labtestingapp.common.CommonMethod;
import itg8.com.labtestingapp.common.GenericSpinnerAdapter;
import itg8.com.labtestingapp.common.NetworkCall;
import itg8.com.labtestingapp.common.Prefs;
import itg8.com.labtestingapp.common.SpinnerGenericModel;
import itg8.com.labtestingapp.common.SpinnerItemSelect;
import itg8.com.labtestingapp.common.UtilSnackbar;
import itg8.com.labtestingapp.rating.model.QuestionController;
import itg8.com.labtestingapp.rating.model.RatingModel;
import itg8.com.labtestingapp.request.model.RequestModel;
import okhttp3.ResponseBody;

public class RatingViewModel extends BaseObservable {

    private Context context;
    public RatingModel model;

    public ObservableList<SpinnerGenericModel> allQue;

    public RatingViewModel(Context context) {
        this.context = context;
        model=new RatingModel();
        allQue=new ObservableArrayList<>();
        downloadQuestions();
        model.setUserID(Prefs.getString(CommonMethod.USERID));
    }

    private void downloadQuestions() {
         Observable<ResponseBody> observable=NetworkCall.getController().downloadQuestions();
        Disposable disposable = observable.flatMap(new Function<ResponseBody, Observable<List<SpinnerGenericModel>>>() {
            @Override
            public Observable<List<SpinnerGenericModel>> apply(ResponseBody responseBody) throws Exception {
                String res=responseBody.string();
                QuestionController controller=new QuestionController(res);
                return Observable.just(controller.getModels());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SpinnerGenericModel>>() {
                    @Override
                    public void accept(List<SpinnerGenericModel> spinnerGenericModels) throws Exception {
                        if(spinnerGenericModels.size()>0){
                            allQue.addAll(spinnerGenericModels);
                        }
                    }
                }, Throwable::printStackTrace);
    }

    public void  submitClick(View view){
        if(model.isValidate(view)) {
            Observable<ResponseBody> d = NetworkCall.getController().storeRating(model.getUserID(), model.getDescription(), (int) model.getRating(), model.getCategoryID());
            Disposable disposable = d.flatMap(new Function<ResponseBody, ObservableSource<?>>() {
                @Override
                public ObservableSource<?> apply(ResponseBody responseBody) throws Exception {
                    String res = responseBody.string();
                    JSONObject jsonObject = new JSONObject(res);
                    if (jsonObject.has("flag")) {
                        String flag = jsonObject.getString("flag");
                        if (flag != null && flag.equalsIgnoreCase("1")) {
                            return Observable.just("1");
                        }
                    }
                    return Observable.just("-1");
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            if (o instanceof String) {
                                String res = (String) o;
                                if (res.equalsIgnoreCase("1")) {
                                    showSuccess(view);
                                } else {
                                    showFail(view);
                                }
                            }
                        }
                    }, Throwable::printStackTrace);
        }
    }


    @BindingAdapter(value = {"customEntriesQue", "customEntriesQueModel"}, requireAll = false)
    public static void bindQuestionAdapter(Spinner spinner, ObservableList<SpinnerGenericModel> allQue, RatingModel model) {
        spinner.setAdapter(new GenericSpinnerAdapter(spinner.getContext(), allQue));
        SpinnerItemSelect itemDate = new SpinnerItemSelect(model, "categoryID");
        itemDate.setOnItemAvailListener(id -> model.setCategoryID(Integer.parseInt(id)));
        spinner.setOnItemSelectedListener(itemDate);
    }

    @BindingAdapter({"error"})
    public static void setError(TextInputLayout layout,String err){
        layout.setError(err);
    }

    private void showSuccess(View view) {
        UtilSnackbar.showSnakbarOkBtn(view, "Thanks for your feedback", new UtilSnackbar.OnSnackbarActionClickListener() {
            @Override
            public void onRetryClicked() {
                ((MainActivity)context).backToStackHome();
            }
        });
    }

    private void showFail(View view) {
        UtilSnackbar.showSnakbarTypeWithMessage(view, "Fail to send request", new UtilSnackbar.OnSnackbarActionClickListener() {
            @Override
            public void onRetryClicked() {
                submitClick(view);
            }
        });
    }


}
