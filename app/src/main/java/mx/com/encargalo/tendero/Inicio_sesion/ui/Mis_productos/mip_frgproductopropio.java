package mx.com.encargalo.tendero.Inicio_sesion.ui.Mis_productos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mx.com.encargalo.R;

public class mip_frgproductopropio extends Fragment {

    Button mip_ppbtncancelar, mip_ppbtnagregarproducto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mip_frgproductopropio, container, false);

        mip_ppbtncancelar = view.findViewById(R.id.mip_ppbtncancelar);
        mip_ppbtnagregarproducto = view.findViewById(R.id.mip_ppbtnagregarproducto);

        mip_ppbtncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigateUp();
            }
        });

        mip_ppbtnagregarproducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_mip_frgproductopropio_to_mip_frgproductoagregado);

            }
        });

        return view;
    }
}