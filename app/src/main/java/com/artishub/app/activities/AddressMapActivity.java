package com.artishub.app.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.artishub.app.R;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.constant.MsgConstant;
import com.artishub.app.constant.ServerConstants;
import com.artishub.app.databinding.ActivityAddressMapBinding;
import com.artishub.app.helpers.AlertUtil;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.helpers.AppValidator;
import com.artishub.app.helpers.LocationActivity;
import com.artishub.app.helpers.PermissionsUtil;
import com.artishub.app.helpers.RestClient;
import com.artishub.app.helpers.SharedPrefrencesManager;
import com.artishub.app.model.CurrentUser;
import com.artishub.app.model.MyAddressModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.artishub.app.helpers.RestClient.ApiRequest.METHOD_POST;


public class AddressMapActivity extends LocationActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ActivityAddressMapBinding binding;
    private double lat;
    private RestClient.ApiRequest apiAddAddressRequest;
    private RestClient.ApiRequest apiUpdateAddressRequest;
    public static double latitude;
    public static double longitude;
    View mapView;
    private CurrentUser currentUser;
    private MyAddressModel.ResultBean myAddress;
    private String mobileNo, name, address, houseNo, landmark,countryCode="+234";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(getDrawable(R.drawable.bg_statusbarone));
        }
        binding = DataBindingUtil.setContentView(AddressMapActivity.this, R.layout.activity_address_map);
        currentUser = new Gson().fromJson(SharedPrefrencesManager.getInstance(AddressMapActivity.this).getString(AppConstant.KEY_CURRENT_USER, ""), CurrentUser.class);
        myAddress = (MyAddressModel.ResultBean) getIntent().getSerializableExtra("address");
        if (myAddress != null) {
            setData();
        }


        PermissionsUtil.askPermission(this, PermissionsUtil.LOCATION, (boolean isGranted) -> {
            if (isGranted) {
                //[Get current Location]
                requestLocation(location -> {
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        if (latitude != 0.0d && longitude != 0.0d) {
                            currentAddress(latitude, longitude);
                        }
                        //[map initialize and setup]
                        MapsInitializer.initialize(this);
                        setupGoogleMap();
                        //[end]
                    }
                });
            }
        });

        binding.imgLeftSide.setOnClickListener(view -> {

            finish();
        });

        binding.btnAddAddress.setOnClickListener(view -> {
            if (myAddress != null) {
                if (checkData()) {
                    name = binding.edtAddressName.getText().toString();
                    mobileNo = binding.edtAddressPhone.getText().toString();
                    address = binding.edtAddress.getText().toString();
                    houseNo = binding.edtAddressLine.getText().toString();
                    landmark = binding.edtAddressLandMark.getText().toString();
                    callUpdateAddressApi();
                }
            } else {

                if (checkData()) {
                    name = binding.edtAddressName.getText().toString();
                    mobileNo = binding.edtAddressPhone.getText().toString();
                    address = binding.edtAddress.getText().toString();
                    houseNo = binding.edtAddressLine.getText().toString();
                    landmark = binding.edtAddressLandMark.getText().toString();
                    callAddAddressApi();
                }
            }
        });


    }

    private void setData() {

        binding.edtAddressName.setText(myAddress.getName());
        binding.edtAddressPhone.setText(myAddress.getMobile_number());
        binding.edtAddress.setText(myAddress.getAddress());
        binding.edtAddressLine.setText(myAddress.getHouse_no());
        binding.edtAddressLandMark.setText(myAddress.getLandmark());
    }

    //[Map set up]
    private void setupGoogleMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);
    }


    protected void setMarker(Double latitude, Double longitude) {
        mMap.getUiSettings().setZoomControlsEnabled(false);
        // googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        LatLng latLng1 = new LatLng(latitude, longitude);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng1)
                .zoom(16)
//                .bearing(90)                // Sets the orientation of the camera to east
//                .tilt(90)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //[for location button below]
        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 30);
            //[End]
        }
        //[Get address when moving map]
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                if (mMap.getCameraPosition().target.latitude != 0.0d && mMap.getCameraPosition().target.longitude != 0.0d) {
                    currentAddress(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);
                }
            }
        });
        //[End]
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(AddressMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddressMapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddressMapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
            return;
        }

        googleMap.setMyLocationEnabled(true);
        if (latitude != 0.0d && longitude != 0.0d) {
            setMarker(latitude, longitude);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    public String currentAddress(final double currentLat, final double currentLong) {
        StringBuilder sb = null;
        String address = null;
        try {
            Geocoder geocoder = null;
            StringBuilder addressBuilderString = new StringBuilder();
            List<Address> addresses;
            geocoder = new Geocoder(AddressMapActivity.this, Locale.getDefault());
            addresses = geocoder.getFromLocation(currentLat, currentLong, 1);
            if (addresses != null && !addresses.isEmpty()) {
                // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                Address addresssss = (Address) addresses.get(0);
                address = addresssss.getAddressLine(0);
                binding.edtAddress.setText(address);
                binding.edtAddress.setSelection(binding.edtAddress.getText().length());
                countryCode=addresssss.getCountryCode();

            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return address;
    }

    private void callAddAddressApi() {
        AppUtilis.hideSoftKeyboard(this);
        //AlertUtil.showProgressDialog(this);
        binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("country_code", countryCode);
        params.put("mobile_number", mobileNo);
        params.put("address", address);
        params.put("house_no", houseNo);
        params.put("landmark", landmark);
        params.put("method", "add_address");
        params.put("user_id", currentUser.getResult().getUser_id());


        apiAddAddressRequest = new RestClient.ApiRequest(AddressMapActivity.this);
        apiAddAddressRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("Add address")
                .setResponseListener((tag, response) -> {
                    // AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    if (response != null) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String msg;
                            if (jsonObject.getInt("error_code") == 0) {
                                msg = jsonObject.getString("error_string");
                                AlertUtil.showToast(AddressMapActivity.this, msg);
                                finish();

                            } else {
                                msg = jsonObject.getString("error_string");
                                AlertUtil.showToast(AddressMapActivity.this, msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else
                        AlertUtil.showSnackBarLong(AddressMapActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                callAddAddressApi();
                            }
                        });

                })
                .setErrorListener((tag, errorMsg) -> {
                    //AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    AlertUtil.showSnackBarLong(AddressMapActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            callAddAddressApi();
                        }
                    });
                })
                .execute();
    }


    private void callUpdateAddressApi() {
        AppUtilis.hideSoftKeyboard(this);
        //AlertUtil.showProgressDialog(this);
        binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("country_code", "+91");
        params.put("mobile_number", mobileNo);
        params.put("address", address);
        params.put("house_no", houseNo);
        params.put("landmark", landmark);
        params.put("method", "add_address");
        params.put("address_id",myAddress.getAddress_id());



        apiUpdateAddressRequest = new RestClient.ApiRequest(AddressMapActivity.this);
        apiUpdateAddressRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("Add address")
                .setResponseListener((tag, response) -> {
                    // AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    if (response != null) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String msg;
                            if (jsonObject.getInt("error_code") == 0) {
                                msg = jsonObject.getString("error_string");
                                AlertUtil.showToast(AddressMapActivity.this, msg);

                            } else {
                                msg = jsonObject.getString("error_string");
                                AlertUtil.showToast(AddressMapActivity.this, msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else
                        AlertUtil.showSnackBarLong(AddressMapActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", view -> callUpdateAddressApi());

                })
                .setErrorListener((tag, errorMsg) -> {
                    //AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    AlertUtil.showSnackBarLong(AddressMapActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", view -> callUpdateAddressApi());
                })
                .execute();
    }


    //***************check validation of data**************//
    private boolean checkData() {
        if (!AppValidator.isValidName(AddressMapActivity.this, binding.edtAddressName, MsgConstant.FUll_NAME_ERROR_VOID)) {
            return false;
        } else if (!AppValidator.isValidMobile(AddressMapActivity.this, binding.edtAddressPhone, MsgConstant.PHONE_ERROR_VOID)) {
            return false;
        } else if (!validData(binding.edtAddress.getText().toString(), MsgConstant.ADDRESS_ERROR_VOID)) {
            return false;
        } else if (!validData(binding.edtAddressLine.getText().toString(), MsgConstant.ADDRESS_ERROR_VOID)) {
            return false;
        }


        return true;
    }

    private boolean validData(String s, String msg) {
        if (s != null && !s.equals("")) {
            return true;
        } else {
            AlertUtil.showToast(AddressMapActivity.this, msg);
            return false;

        }


    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
