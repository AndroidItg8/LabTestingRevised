package itg8.com.labtestingapp.geotechnical;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import itg8.com.labtestingapp.databinding.FragmentGeoTechnicalBinding;

import itg8.com.labtestingapp.R;
import itg8.com.labtestingapp.geotechnical.mvvm.GeoTechViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GeoTechnicalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GeoTechnicalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public GeoTechnicalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GeoTechnicalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GeoTechnicalFragment newInstance(String param1, String param2) {
        GeoTechnicalFragment fragment = new GeoTechnicalFragment();
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

    private FragmentGeoTechnicalBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_geo_technical, container, false);
        setModel();
        return binding.getRoot();
    }

    private void setModel() {
        GeoTechViewModel model=new GeoTechViewModel(getActivity());
        binding.setGeomodel(model);
    }


}
