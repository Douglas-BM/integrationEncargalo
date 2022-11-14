package mx.com.encargalo.tendero.Inicio_sesion.ui.Mis_productos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;

import mx.com.encargalo.R;

public class mip_frgregistrarproducto extends Fragment {

    Spinner mit_rpedtcategoria;
    TextInputEditText mit_rpedtdescpricion;
    Button mit_rpbtnagregarproducto, mit_rpbtnproductopropio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_mip_frgregistrarproducto, container, false);

        mit_rpbtnagregarproducto = view.findViewById(R.id.mip_rpbtnagregarproducto);
        mit_rpbtnproductopropio = view.findViewById(R.id.mip_rpbtnproductopropio);
        mit_rpedtdescpricion = view.findViewById(R.id.mip_rpedtdescpricion);
        mit_rpedtcategoria = view.findViewById(R.id.mip_rpedtcategoria);

        mit_rpbtnagregarproducto.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_mip_frgregistrarproducto_to_mip_frgproductoagregado));
        mit_rpbtnproductopropio.setOnClickListener(view12 -> Navigation.findNavController(view12).navigate(R.id.action_mip_frgregistrarproducto_to_mip_frgproductopropio));


        mit_rpedtdescpricion.setEnabled(false);
        mit_rpedtdescpricion.setKeyListener(null);

        mit_rpedtcategoria.setEnabled(false);

        return view;
    }
}