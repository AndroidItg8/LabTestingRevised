package itg8.com.labtestingapp.request.mvvm;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.List;

import itg8.com.labtestingapp.common.LatLng;
import itg8.com.labtestingapp.db.repository.SubAdminTestRepository;
import itg8.com.labtestingapp.db.repository.TestRepository;
import itg8.com.labtestingapp.db.tables.SubAdmin;
import itg8.com.labtestingapp.db.tables.SubAdminTest;

class NearestSubAdminController {
    private LatLng latLng;
    SubAdminTestRepository repository;
    Fragment fragment;

    public   NearestSubAdminController(LatLng latLng, Application context, Fragment fragment) {
        this.latLng = latLng;
        this.fragment=fragment;
        repository=ViewModelProviders.of(fragment).get(SubAdminTestRepository.class);
    }


    public void getSubAdmin(LatLng latLng, List<Integer> allSelectedTestList, OnIDAvailListener onIDAvailListener) {

        repository.getAllSubAdmin(allSelectedTestList).observe(fragment, new Observer<List<SubAdmin>>() {
            @Override
            public void onChanged(@Nullable List<SubAdmin> subAdmins) {
                double d1,d2;
                double shortedDistance=-1;
                int subAdminID=0;
                for (SubAdmin admin :
                        subAdmins) {
                    d1= Double.parseDouble(admin.getLatitude());
                    d2=Double.parseDouble(admin.getLongitude());
                    if(distance(latLng.lat,d1,latLng.lng,d2)<shortedDistance || shortedDistance==-1){
                        shortedDistance=distance(latLng.lat,d1,latLng.lng,d2);
                        subAdminID=admin.getId();
                    }
                }
                onIDAvailListener.onIDAvail(subAdminID);
            }
        });

    }


    //returns distance in meters
    public static double distance(double lat1, double lng1,
                                  double lat2, double lng2){
        double a = (lat1-lat2)*distPerLat(lat1);
        double b = (lng1-lng2)*distPerLng(lat1);
        return Math.sqrt(a*a+b*b);
    }

    private static double distPerLng(double lat){
        return 0.0003121092*Math.pow(lat, 4)
                +0.0101182384*Math.pow(lat, 3)
                -17.2385140059*lat*lat
                +5.5485277537*lat+111301.967182595;
    }

    private static double distPerLat(double lat){
        return -0.000000487305676*Math.pow(lat, 4)
                -0.0033668574*Math.pow(lat, 3)
                +0.4601181791*lat*lat
                -1.4558127346*lat+110579.25662316;
    }

}
