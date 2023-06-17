package com.example.gongguhaejo.googlemap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.example.gongguhaejo.R;
import com.example.gongguhaejo.SignupActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class googlemap extends AppCompatActivity
        implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {

    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;
    private GoogleMap mMap;
    private Marker marker;
    private double mlatitude,mlongitude;
    public double mylatitude,mylongitude;
    public String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.googlemap);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    // NULL이 아닌 GoogleMap 객체를 파라미터로 제공해 줄 수 있을 때 호출
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(final GoogleMap googleMap) {
// GPS로 캐싱된 위치가 없다면 Network로 가져옴

        mMap = googleMap;
        marker = null;
        final Geocoder geocoder = new Geocoder(this, Locale.KOREA);

        mMap.setMinZoomPreference(5.0f);
        mMap.setMaxZoomPreference(18.0f);
        // Check if location permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Request location permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Location permission is already granted
            mMap.setMyLocationEnabled(true);
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

        // 맵 클릭 이벤트
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onMapClick(LatLng point) {
                MarkerOptions mOptions = new MarkerOptions();
                // 마커 타이틀
                mOptions.title("마커 좌표");
                Double latitude = point.latitude; // 위도
                Double longitude = point.longitude; // 경도

                LatLng pointby = new LatLng(latitude, longitude);
                mOptions.position(pointby);

                //위도 경도로 주소 구하는 Reverse-GeoCoding
                List<Address> list = null;
                try {
                    list = geocoder.getFromLocation(
                            latitude, // 위도
                            longitude, // 경도
                            10); // 얻어올 값의 개수
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("test", "입출력 오류");
                }
                if (list != null) {
                    if (list.size()==0) {
                        mOptions.snippet("해당되는 주소 정보는 없습니다");
                    } else {
                        address = list.get(0).getAddressLine(0);
                        mOptions.snippet(address);
                        //Toast.makeText(getApplicationContext(), list.get(0).getCountryName(), Toast.LENGTH_LONG).show();
                    }
                }

                // 마커의 스니펫(텍스트) 설정
                //mOptions.snippet(latitude.toString() + ", " + longitude.toString());

                float zoom = mMap.getCameraPosition().zoom; //'현재' 화면 확대 정도
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pointby,zoom)); // zoom자리에 int값으로 조절

                if(marker != null) //이전 마커 삭제
                marker.remove();

                marker = mMap.addMarker(mOptions); //마커 생성
                marker.showInfoWindow();
                showAlertDialog();
            }
        });

        // 마커 클릭 이벤트
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            public boolean onMarkerClick(Marker marker) {
//                 mlatitude = marker.getPosition().latitude;
//                 mlongitude = marker.getPosition().longitude;
//
//                String text = "[마커 클릭 이벤트] latitude ="
//                        + mlatitude + ", longitude ="
//                        + mlongitude;
//                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG)
//                        .show();
                showAlertDialog();
                return false;
            }
        });
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            mylatitude = location.getLatitude();
                            mylongitude = location.getLongitude();
                            LatLng myGPS = new LatLng(mylatitude, mylongitude);      //시작화면 설정, 현재위치 gps로 시작하게 수정해야 함
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myGPS, 17));

                            //LatLng testzoom = new LatLng(37.56,127.00); //데스크탑 테스트용
                            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(testzoom,17));
                        }
                    }
                });
        //Toast.makeText(this, "mygps"+myGPS, Toast.LENGTH_SHORT).show();
    }
    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            return;
        }
        // 2. Otherwise, request location permissions from the user.
        PermissionUtils.requestLocationPermissions(this, LOCATION_PERMISSION_REQUEST_CODE, true);
    }

    //내 위치 바로가기 버튼
    @Override
    public boolean onMyLocationButtonClick() {
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
      }
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION) || PermissionUtils
                .isPermissionGranted(permissions, grantResults,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
            // 퍼미션 허가
            enableMyLocation();
        } else {
            // 퍼미션 허가 실패, 에러메시지 송출
            permissionDenied = true;
        }
    }
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    public void showAlertDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("이 주소를 내 위치로 설정할까요?").setMessage(address);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                Intent outIntent = new Intent(getApplicationContext(),
                        SignupActivity.class);
                outIntent.putExtra("loca",address);
                setResult(RESULT_OK,outIntent);
                finish();
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
