package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_tienda;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mx.com.encargalo.R;

public class rt_frgpuntoventacreado extends Fragment {

    Button rt_pvcbtnvolverinicio; //+@OHC08.11.2022

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_rt_frgpuntoventacreado, container, false);
        rt_pvcbtnvolverinicio=vista.findViewById(R.id.rt_pvcbtnvolverinicio);
        rt_pvcbtnvolverinicio.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.action_rt_frgpuntoventacreado_to_rt_frgtiendaelegida));
        return vista;
    } //+}@OHC08.11.2022
}