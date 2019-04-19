package itg8.com.labtestingapp.request;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import itg8.com.labtestingapp.MainActivity;
import itg8.com.labtestingapp.R;
import itg8.com.labtestingapp.common.BaseFragment;
import itg8.com.labtestingapp.common.GlobalViewModel;
import itg8.com.labtestingapp.common.MyApplication;
import itg8.com.labtestingapp.databinding.FragmentRequestBinding;
import itg8.com.labtestingapp.lab.LabSelectionListener;
import itg8.com.labtestingapp.lab.model.LabModel;
import itg8.com.labtestingapp.lab.mvvm.LabItemViewModel;
import itg8.com.labtestingapp.lab.mvvm.LabViewModel;
import itg8.com.labtestingapp.request.mvvm.RequestViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestFragment extends BaseFragment implements LabSelectionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final String TAG = "MainRequestFragment";
    private RequestViewModel requestViewModel;
    private GlobalViewModel viewModel;
    private LabItemViewModel labItemViewModel;


    public RequestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RequestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RequestFragment newInstance(String param1, String param2) {
        RequestFragment fragment = new RequestFragment();
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
        Log.d(TAG, "onCreate: ");
    }

    FragmentRequestBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_request, container, false);
        View view = binding.getRoot();
//        return inflater.inflate(R.layout.fragment_request,container,false);
        if(requestViewModel==null) {
            requestViewModel = new RequestViewModel(MyApplication.getInstance(), this);
            labItemViewModel = new LabItemViewModel();
        }
        binding.setRequestModel(requestViewModel);
        binding.setModel(labItemViewModel);

        Log.d(TAG, "onCreateView: ");
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null)
            ((MainActivity) getActivity()).setLabSelectedListner(this);

        Log.d(TAG, "onAttach: ");

    }

    @Override
    public void onLabSelected(LabModel model) {
        if (model != null) {
            Log.d(TAG, "onLabSelected: " + new Gson().toJson(model));

          



            //            viewModel.setLabModel(model)
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel = ViewModelProviders.of((MainActivity) getActivity()).get(GlobalViewModel.class);
        viewModel.getModel().observe(this, new Observer<LabModel>() {
            @Override
            public void onChanged(@Nullable LabModel model) {
                Log.d(TAG, "onChanged: model"+new Gson().toJson(model));
                requestViewModel.setLabModel(model);




            }
        });
      
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "monDestroyView: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "monDetach: ");
    }
}
