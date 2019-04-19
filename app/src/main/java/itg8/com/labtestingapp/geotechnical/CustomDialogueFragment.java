package itg8.com.labtestingapp.geotechnical;


import android.databinding.ObservableList;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import itg8.com.labtestingapp.R;
import itg8.com.labtestingapp.common.SpinnerGenericModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomDialogueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomDialogueFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ObservableList<SpinnerGenericModel> states;


    public CustomDialogueFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomDialogueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomDialogueFragment newInstance(String param1, String param2) {
        CustomDialogueFragment fragment = new CustomDialogueFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static CustomDialogueFragment newInstance(ObservableList<SpinnerGenericModel> states) {
        CustomDialogueFragment fragment = new CustomDialogueFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, (ArrayList<? extends Parcelable>) states);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
          //  states = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  =inflater.inflate(R.layout.fragment_custom_dialogue, container, false);
        return view;
    }

}
