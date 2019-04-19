package itg8.com.labtestingapp.type;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import io.reactivex.annotations.NonNull;
import itg8.com.labtestingapp.MainActivity;
import itg8.com.labtestingapp.R;
import itg8.com.labtestingapp.RequestFinalListener;
import itg8.com.labtestingapp.common.BaseFragment;
import itg8.com.labtestingapp.common.UtilSnackbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TypeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TypeFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean isViewDestroyed;
    private ProgressBar progressbar;
    private Button buttonNext;
    private TextView txtStatus;


    public TypeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TypeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TypeFragment newInstance(String param1, String param2) {
        TypeFragment fragment = new TypeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    boolean isPick;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_type, container, false);
        isViewDestroyed=false;
        view.findViewById(R.id.rdoPick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPick=true;
            }
        });
        view.findViewById(R.id.rdoDrop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPick=false;
            }
        });
        buttonNext=(Button)view.findViewById(R.id.btnNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentGateway();
            }
        });

        progressbar=view.findViewById(R.id.progressbar);
        txtStatus=view.findViewById(R.id.txtAStatus);
        return view;
    }

   private void showProgress(){
        progressbar.setVisibility(View.VISIBLE);
        buttonNext.setVisibility(View.GONE);
        txtStatus.setText("");
   }

   private void showStatus(String text){
       progressbar.setVisibility(View.GONE);
       buttonNext.setVisibility(View.GONE);
       txtStatus.setText(text);
   }

   private void showButton(){
       progressbar.setVisibility(View.GONE);
       buttonNext.setVisibility(View.VISIBLE);
       txtStatus.setText("");
   }

    private void showPaymentGateway() {
        showProgress();
        ((MainActivity)getActivity()).paymentFragment(isPick,new RequestFinalListener(){

            @Override
            public void onFinalFinished() {
                if(!isViewDestroyed){
                    showStatus("Request submitted successfully");
                    showSnackbarSuccess("Request submitted successfully");
                }
            }

            @Override
            public void onFinalSaveFailed(String t) {
                if(!isViewDestroyed){
                    showButton();
                    showError(t);
                }
            }
        });
    }

    private void showSnackbarSuccess(String msg) {
        UtilSnackbar.showSnakbarOkBtn(progressbar, msg, new UtilSnackbar.OnSnackbarActionClickListener() {
            @Override
            public void onRetryClicked() {
                ((MainActivity)getActivity()).backToStackHome();
            }
        });
    }

    private void showError(String message) {
        UtilSnackbar.showSnakbarTypeWithMessage(progressbar, message, new UtilSnackbar.OnSnackbarActionClickListener() {
            @Override
            public void onRetryClicked() {
                showPaymentGateway();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewDestroyed=true;
    }
}
