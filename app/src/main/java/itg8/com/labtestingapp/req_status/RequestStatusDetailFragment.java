package itg8.com.labtestingapp.req_status;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import itg8.com.labtestingapp.R;
import itg8.com.labtestingapp.common.BaseFragment;
import itg8.com.labtestingapp.databinding.FragmentRequestStatusDetailBinding;
import itg8.com.labtestingapp.req_status.model.RequestStatusModel;
import itg8.com.labtestingapp.req_status.mvvm.ReqStatusDetailViewModel;
import itg8.com.labtestingapp.req_status.mvvm.RequestStatusViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RequestStatusDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestStatusDetailFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private  RequestStatusModel mParam1;
    private String mParam2;


    public RequestStatusDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RequestStatusDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RequestStatusDetailFragment newInstance(RequestStatusModel param1, String param2) {
        RequestStatusDetailFragment fragment = new RequestStatusDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    FragmentRequestStatusDetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_request_status_detail, container, false);
        View view=binding.getRoot();
        setViewModel();
        return view;
    }

    private void setViewModel() {
        ReqStatusDetailViewModel model =new ReqStatusDetailViewModel(getContext(),this);
        model.setModel(mParam1);
        binding.setReqStatusModel(model);
    }

}
