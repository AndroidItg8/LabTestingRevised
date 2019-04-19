package itg8.com.labtestingapp.lab;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import itg8.com.labtestingapp.MainActivity;
import itg8.com.labtestingapp.R;
import itg8.com.labtestingapp.common.BaseFragment;
import itg8.com.labtestingapp.databinding.FragmentLabBinding;
import itg8.com.labtestingapp.db.tables.MainCategory;
import itg8.com.labtestingapp.home.mvvm.HomeViewModel;
import itg8.com.labtestingapp.lab.model.LabModel;
import itg8.com.labtestingapp.lab.mvvm.LabViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LabFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<LabModel> labList;
    private FragmentLabBinding binding;
    private LabViewModel model;
    private LabSelectionListener listner;
    private static final String TAG = "LabFragment";


    public LabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment LabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LabFragment newInstance(List<LabModel> labModelList) {
        LabFragment fragment = new LabFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, (ArrayList<? extends Parcelable>) labModelList);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            labList = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_lab, container, false);
        View view=binding.getRoot();

        model=new LabViewModel( getActivity(),labList);

        binding.setLabViewModel(model);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
