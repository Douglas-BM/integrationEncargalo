package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_tienda;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Util.Util;

import static android.content.Context.MODE_PRIVATE;

public class rt_frgubicacion extends Fragment {

    private GoogleMap rt_ubcmapubicacion; //+@OHC08.11.2022
    Button rt_ubcbtncancelar, rt_ubcbtnagregarubicacion; //+@OHC08.11.2022
    String rt_vargoblatitudmarcador, rt_vargoblongitudmarcador; //+@OHC08.11.2022
    boolean rt_vargobmarcador= false; //+@OHC08.11.2022

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            SharedPreferences preferencias = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferencias.edit();
            rt_ubcmapubicacion = googleMap;
            rt_ubcmapubicacion.getUiSettings().setZoomControlsEnabled(true);
            rt_ubcmapubicacion.getUiSettings().setMapToolbarEnabled(true);
            rt_ubcmapubicacion.getUiSettings().setMyLocationButtonEnabled(true);
            LatLng Ubi = new LatLng(Double.parseDouble(preferencias.getString("latTemp", "")), Double.parseDouble(preferencias.getString("longTemp", "")));
            rt_ubcmapubicacion.moveCamera(CameraUpdateFactory.newLatLng(Ubi));

            CameraPosition cameraPosition = CameraPosition.builder().target(Ubi).zoom(16).build();
            rt_ubcmapubicacion.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            rt_ubcmapubicacion.setOnMapClickListener(latLng -> {
                rt_vargobmarcador = true;
                rt_ubcmapubicacion.clear();
                rt_ubcmapubicacion.addMarker(new MarkerOptions()
                        .title("Punto")
                        .snippet("descripci??n ......")
                        .position(latLng)
                        .anchor(0.5f, 0.5f)
                ); //+}@OHC08.11.2022
                rt_vargoblatitudmarcador= String.valueOf(latLng.latitude);
                rt_vargoblongitudmarcador= String.valueOf(latLng.longitude);

                try {
                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                    List<Address> list = geocoder.getFromLocation(
                            latLng.latitude, latLng.longitude, 1);
                    if (!list.isEmpty()) {
                        Address DirCalle = list.get(0);
                        editor.putString("Direccion", String.valueOf(DirCalle.getAddressLine(0)));
                        editor.apply();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }); //+}@OHC08.11.2022
        }
    }; //+}@OHC08.11.2022

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rt_frgubicacion, container, false);

        rt_ubcbtnagregarubicacion = view.findViewById(R.id.rt_ubcbtnagregarubicacion);
        rt_ubcbtncancelar = view.findViewById(R.id.rt_ubcbtncancelar);
        rt_ubcbtnagregarubicacion.setOnClickListener(view1 -> {
            if(rt_vargobmarcador){
                SharedPreferences preferencias = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("tieLatitud", rt_vargoblatitudmarcador);
                editor.putString("tieLongitud", rt_vargoblongitudmarcador);
                editor.apply();

                //Refrescar Pantalla Crear Punto de venta
                Navigation.findNavController(view1).navigateUp();
                Fragment fragment = new rt_frgcrearpuntoventa();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.commit();
                //

            }
            else{
                Toast.makeText(getActivity(), "Inidique una ubicaci??n en el mapa.", Toast.LENGTH_SHORT).show();
            }

        }); //+}@OHC08.11.2022

        rt_ubcbtncancelar.setOnClickListener(view12 -> {
            //Borrando Shared Preferences
            SharedPreferences.Editor borrandosharedpreferences = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS, MODE_PRIVATE).edit();
            borrandosharedpreferences.remove("tieLatitud");
            borrandosharedpreferences.remove("tieLongitud");
            borrandosharedpreferences.remove("Direccion");
            borrandosharedpreferences.apply();

            Navigation.findNavController(view12).navigateUp();
        }); //+}@OHC08.11.2022

        return view;
    } //+}@OHC08.11.2022

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.rt_frgmapa);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    } //+}@OHC08.11.2022
}