package com.example.androidstudy.any.addressphoto.activity;


import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.ExifInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.example.androidstudy.any.addressphoto.adapter.AddressPhotoAdapter;
import com.example.androidstudy.any.addressphoto.bean.AlbumBean;
import com.example.androidstudy.databinding.ActivityAddressPhotoBinding;
import com.example.baselibrary.activity.BaseActivity;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.recyclerview.widget.GridLayoutManager;

public class AddressPhotoActivity extends BaseActivity {

    private AddressPhotoAdapter addressPhotoAdapter;
    private ActivityAddressPhotoBinding binding;
    private List<AlbumBean> albumBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddressPhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addressPhotoAdapter = new AddressPhotoAdapter();
        addressPhotoAdapter.setOnItemClickListener(position -> {
            AlbumBean albumBean = albumBeanList.get(position);
            Intent intent = new Intent(AddressPhotoActivity.this, PhotoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("lists", (Serializable) albumBean.urls);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        binding.rvAddressPhoto.setLayoutManager(new GridLayoutManager(this, 4));
        binding.rvAddressPhoto.setAdapter(addressPhotoAdapter);
        scanPhoto();
    }

    private void scanPhoto() {
        try {
            Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, null, null, null);
            albumBeanList = new ArrayList<>();
            while (cursor.moveToNext()) {
                //获取图片的名称 cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME 常量 查下
                // 可以 查询图片的名称 的列名
                String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                String desc = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION));
                String size = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                String longitude = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.LONGITUDE));
                String latitude = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.LATITUDE));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                Location loc = exif2Loc(data);
                if (loc != null) {
                    Address address = getAddress(loc.getLatitude(), loc.getLongitude());
                    if (address == null) {
                        continue;
                    }
                    Log.i("zjy", "name: " + name + "longitude: " + longitude + " latitude: " + latitude + " address: " + address);
                    boolean isHas = false;
                    for (AlbumBean albumBean : albumBeanList) {
                        if (albumBean.albumName.equals(address.getLocality())) {
                            isHas = true;
                            if (albumBean.urls == null) {
                                albumBean.urls = new ArrayList<>();
                            }
                            albumBean.urls.add(data);
                            break;
                        }
                    }
                    if (!isHas) {
                        AlbumBean albumBean = new AlbumBean();
                        albumBean.albumName = address.getLocality();
                        if (albumBean.urls == null) {
                            albumBean.urls = new ArrayList<>();
                        }
                        albumBean.urls.add(data);
                        albumBeanList.add(albumBean);
                    }
                } else {
                    boolean isHas = false;
                    for (AlbumBean albumBean : albumBeanList) {
                        if (albumBean.albumName.equals("未知位置")) {
                            isHas = true;
                            if (albumBean.urls == null) {
                                albumBean.urls = new ArrayList<>();
                            }
                            albumBean.urls.add(data);
                            break;
                        }
                    }
                    if (!isHas) {
                        AlbumBean albumBean = new AlbumBean();
                        albumBean.albumName = "未知位置";
                        if (albumBean.urls == null) {
                            albumBean.urls = new ArrayList<>();
                        }
                        albumBean.urls.add(data);
                        albumBeanList.add(albumBean);
                    }
                }

            }

            cursor.close();
            addressPhotoAdapter.setAlbums(albumBeanList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Location exif2Loc(String flNm) {
        String sLat = "", sLatR = "", sLon = "", sLonR = "";
        try {
            ExifInterface ef = new ExifInterface(flNm);
            sLat = ef.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            sLon = ef.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            sLatR = ef.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
            sLonR = ef.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
        } catch (IOException e) {
            return null;
        }

        double lat = dms2Dbl(sLat);
        if (lat > 180.0) return null;
        double lon = dms2Dbl(sLon);
        if (lon > 180.0) return null;

        if (sLatR != null) {
            lat = sLatR.contains("S") ? -lat : lat;
        }
        if (sLonR != null) {
            lon = sLonR.contains("W") ? -lon : lon;
        }

        Location loc = new Location("exif");
        loc.setLatitude(lat);
        loc.setLongitude(lon);
        return loc;
    }

    double dms2Dbl(String sDMS) {
        double dRV = 999.0;
        try {
            String[] DMSs = sDMS.split(",", 3);
            String[] s = DMSs[0].split("/", 2);
            dRV = (Double.parseDouble(s[0]) / Double.parseDouble(s[1]));
            s = DMSs[1].split("/", 2);
            dRV += ((Double.parseDouble(s[0]) / Double.parseDouble(s[1])) / 60);
            s = DMSs[2].split("/", 2);
            dRV += ((Double.parseDouble(s[0]) / Double.parseDouble(s[1])) / 3600);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dRV;
    }

    public final Address getAddress(double latitude, double longitude) {
        Geocoder gc = new Geocoder(this, Locale.getDefault());
        StringBuilder sb = new StringBuilder();
        try {
            List<Address> add = gc.getFromLocation(latitude, longitude, 1);
            if (add.size() > 0) {
                Address ad = add.get(0);
                sb.append(ad.getAddressLine(0));
                sb.append(ad.getAddressLine(1));
                sb.append(ad.getAddressLine(2));
                return ad;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}