package itg8.com.labtestingapp.test;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import itg8.com.labtestingapp.MainActivity;
import itg8.com.labtestingapp.R;
import itg8.com.labtestingapp.common.BaseFragment;
import itg8.com.labtestingapp.common.MyApplication;
import itg8.com.labtestingapp.databinding.FragmentTestListBinding;
import itg8.com.labtestingapp.test.mvvm.TestListViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestListFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;
    private TestListViewModel viewModel;


    public TestListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TestListFragment newInstance(int param1, String param2) {
        TestListFragment fragment = new TestListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentTestListBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_test_list, container, false);
        View view=binding.getRoot();
        setViewModel();
        return view;
    }

    private void setViewModel() {
        viewModel=new TestListViewModel(MyApplication.getInstance(),this,mParam1);
        binding.setTestViewModel(viewModel);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getContext()).invalidMenu();
    }
}
