package mx.com.encargalo.tendero.Inicio_sesion.ui.Mis_productos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mx.com.encargalo.R;


public class mip_frgmenuproductos extends Fragment {

    Button mip_mpbtnregistrarproducto, mip_mpbtnreporteproductos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mip_frgmenuproductos, container, false);

        mip_mpbtnregistrarproducto = view.findViewById(R.id.mip_mpbtnregistrarproducto);
        mip_mpbtnreporteproductos = view.findViewById(R.id.mip_mpbtnreporteproductos);

        mip_mpbtnregistrarproducto.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_nav_mis_productos_to_mip_frgregistrarproducto));

        mip_mpbtnreporteproductos.setOnClickListener(view12 -> Navigation.findNavController(view12).navigate(R.id.action_nav_mis_productos_to_mip_frgreporteproductos));

        return view;
    }
}