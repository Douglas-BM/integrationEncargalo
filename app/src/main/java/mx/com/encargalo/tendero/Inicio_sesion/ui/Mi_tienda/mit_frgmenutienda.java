package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_tienda;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mx.com.encargalo.R;

public class mit_frgmenutienda extends Fragment {

    Button mit_mtbtneditardatos, mit_mtbtncambiartienda, mit_mtbtnagregartienda;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mit_frgmenutienda, container, false);

        mit_mtbtneditardatos = view.findViewById(R.id.mit_mtbtneditardatos);
        mit_mtbtncambiartienda = view.findViewById(R.id.mit_mtbtncambiartienda);
        mit_mtbtnagregartienda = view.findViewById(R.id.mit_mtbtnagregartienda);

        mit_mtbtneditardatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_mit_frgmenutienda_to_mit_frgdatostienda);
            }
        });

        mit_mtbtncambiartienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_mit_frgmenutienda_to_mit_frgcambiartienda);
            }
        });

        mit_mtbtnagregartienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_mit_frgmenutienda_to_rt_frgcategoriatienda);
            }
        });

        return view;
    }
}