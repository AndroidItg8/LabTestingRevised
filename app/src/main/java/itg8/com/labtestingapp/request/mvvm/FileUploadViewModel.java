package itg8.com.labtestingapp.request.mvvm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import itg8.com.labtestingapp.MainActivity;
import itg8.com.labtestingapp.R;
import itg8.com.labtestingapp.common.NetworkCall;
import itg8.com.labtestingapp.common.UtilSnackbar;
import itg8.com.labtestingapp.common.fileupload.ProgressRequestBody;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.EasyImageConfig;
import retrofit2.Call;

public class FileUploadViewModel extends BaseObservable implements ProgressRequestBody.UploadCallbacks {

    private static final String TAG = "FileUploadViewModel";
    private static File f;
    private static File compressedImageFile;
    private Context context;
    private Fragment fragment;
    public ObservableField<String> imgPath;
    public ObservableBoolean fileSelected;
    public ObservableInt imgUploadProgress;
    public String uploadedFile;
    private boolean isPermission;

    public FileUploadViewModel(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        fileSelected = new ObservableBoolean(false);
        imgPath = new ObservableField<>();
        imgUploadProgress = new ObservableInt();
        askPermission();
//        imgPath.set(R.drawable.ic_image);
    }


    @BindingAdapter({"img_path"})
    public static void setImageFile(ImageView imageView, ObservableField<String> imgPath) {
        if(imgPath!=null && !TextUtils.isEmpty(imgPath.get())){
             f = new File(imgPath.get());
            try {
                compressedImageFile = new Compressor(imageView.getContext()).compressToFile(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Picasso.get().load(f).fit().into(imageView);
        }
    }




    @BindingAdapter({"img_src_data"})
    public static void setImageRes(ImageView imageView, ObservableBoolean imgPath) {
      if(!imgPath.get()){
          imageView.setImageResource(R.drawable.ic_image);
      }
    }

    private void  askPermission(){
        ((MainActivity)context).setPermissionCallback(new MainActivity.PermissionStorageCallbackListener() {
            @Override
            public void onPermissionGranted() {
                isPermission=true;
            }

            @Override
            public void onPermissionDenied() {
                isPermission=false;
            }
        });
    }



    public void onSelectFileClick(View view){
        if(!isPermission)
        {
            askPermission();
            return;
        }
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        builderSingle.setTitle("Select image to from:-");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("From Camera");
        arrayAdapter.add("From Gallery");

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    EasyImage.openCamera(fragment, EasyImage.REQ_TAKE_PICTURE);
                }else if(which==1){
                    EasyImage.openGallery(fragment,EasyImageConfig.REQ_PICK_PICTURE_FROM_GALLERY);
                }
            }
        });
        builderSingle.show();
    }

    public void uploadFileToServer(View view){
        File file=compressedImageFile;
        if(file==null){
            UtilSnackbar.showSnakbarOkBtn(view, "Please select work order first", new UtilSnackbar.OnSnackbarActionClickListener() {
                @Override
                public void onRetryClicked() {

                }
            });
            return;
        }
        ProgressRequestBody fileBody = new ProgressRequestBody(file, "image",this);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("uploadfile", file.getName(), fileBody);

        Observable<ResponseBody> request = NetworkCall.getController().upload(filePart);
        Disposable d=request.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String response=responseBody.string();
                        if(!TextUtils.isEmpty(response)){
                            uploadedFile=removeInverted(response);
                            forwardToMainActivity();
                        }
                    }
                },Throwable::printStackTrace);
    }

    private void forwardToMainActivity() {
        Log.d(TAG, "forwardToMainActivity: "+uploadedFile);
        ((MainActivity)context).typeFragment(uploadedFile);
    }

    public String removeInverted(String str) {
        if (str != null && str.length() > 3) {
            str = str.substring(1, str.length() - 1);
        }
        return str;
    }


    public void setImagePath(File imageFile) {
        fileSelected.set(true);
    imgPath.set(imageFile.getAbsolutePath());
    }

    @Override
    public void onProgressUpdate(int percentage) {
        imgUploadProgress.set(percentage);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {

    }

    public void setPermission(boolean b) {
        isPermission=b;
    }
}
