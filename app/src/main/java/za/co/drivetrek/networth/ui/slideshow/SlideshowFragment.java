package za.co.drivetrek.networth.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import za.co.drivetrek.networth.R;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SlideshowFragment extends Fragment implements OnMapReadyCallback {

    private SlideshowViewModel slideshowViewModel;
    private GoogleMap mMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng Randburg = new LatLng(-26.1438, 27.9952);
        LatLng HarareMarker = new LatLng(-17.8252, 31.0335);
        mMap.addMarker(new
                MarkerOptions().position(Randburg).title("Randburg"));
        mMap.addMarker(new
                MarkerOptions().position(HarareMarker).title("Harare"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Randburg));
    }
}