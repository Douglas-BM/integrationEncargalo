package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_tienda;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import mx.com.encargalo.R;

public class rt_frgtiendaelegida extends Fragment {

    TextView rt_tietxtnombretienda; //+@OHC08.11.2022
    ImageView rt_imgtienda; //+@OHC08.11.2022

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rt_frgtiendaelegida, container, false);

        rt_tietxtnombretienda = view.findViewById(R.id.rt_tietxtnombretienda);
        rt_imgtienda = view.findViewById(R.id.rt_imgtienda);

//        Navigation.findNavController(view).getGraph().clear();

        return view;
    } //+}@OHC08.11.2022
}