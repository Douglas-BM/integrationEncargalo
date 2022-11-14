package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_tienda;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.Objects;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Util.Util;

public class rt_frgcrearpuntoventa extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    Spinner rt_cpvspnrciudad; //+@OHC08.11.2022
    TextInputEditText rt_cpvedtnombremarca, rt_cpvedtcorreo, rt_cpvedttelefono, rt_cpvedtventames, rt_cpvedtinventario, rt_cpvedtubicacion; //+@OHC08.11.2022
    Button rt_cpvbtncontinuar, rt_cpvbtnubicacion; //+@OHC08.11.2022

    ProgressDialog rt_pdcrearpuntoventa; //+@OHC08.11.2022
    RequestQueue rt_rqcrearpuntoventa; //+@OHC08.11.2022
    JsonObjectRequest rt_jorcrearpuntoventa; //+@OHC08.11.2022
    boolean rt_vargobaceptacion = false; //+@OHC08.11.2022

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences preferencias = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        View vista = inflater.inflate(R.layout.fragment_rt_frgcrearpuntoventa, container, false);

        rt_cpvedtnombremarca=vista.findViewById(R.id.rt_cpvedtnombremarca);
        rt_cpvedtcorreo=vista.findViewById(R.id.rt_cpvedtcorreo);
        rt_cpvedttelefono=vista.findViewById(R.id.rt_cpvedttelefono);
        rt_cpvedtventames=vista.findViewById(R.id.rt_cpvedtventames);
        rt_cpvedtinventario=vista.findViewById(R.id.rt_cpvedtinventario);

        rt_cpvspnrciudad=vista.findViewById(R.id.rt_cpvspnrciudad);
        rt_cpvedtubicacion=vista.findViewById(R.id.rt_cpvedtubicacion);

        rt_cpvbtnubicacion=vista.findViewById(R.id.rt_cpvbtnubicacion);
        rt_cpvbtncontinuar=vista.findViewById(R.id.rt_cpvbtncontinuar);

        rt_cpvedtubicacion.setEnabled(false);

        if (preferencias.contains("Direccion")) rt_cpvedtubicacion.setVisibility(View.VISIBLE);
        else rt_cpvedtubicacion.setVisibility(View.INVISIBLE);

        rt_fctautocompletado.rt_fctautorrellenadoporcampo("tieNombre", rt_cpvedtnombremarca, requireContext());
        rt_fctautocompletado.rt_fctautorrellenadoporcampo("tieCorreo", rt_cpvedtcorreo, requireContext());
        rt_fctautocompletado.rt_fctautorrellenadoporcampo("tieTelefono", rt_cpvedttelefono, requireContext());
        rt_fctautocompletado.rt_fctautorrellenadoporcampo("tieVentasMensuales", rt_cpvedtventames, requireContext());
        rt_fctautocompletado.rt_fctautorrellenadoporcampo("tieInventarioEstimado", rt_cpvedtinventario, requireContext());
        rt_fctautocompletado.rt_fctautorrellenadoporcampo("Direccion", rt_cpvedtubicacion, requireContext());

        if (preferencias.contains("tieCiudad")){
            rt_cpvspnrciudad.setSelection(preferencias.getInt("tieCiudad", 0));
        } //+}@OHC08.11.2022

        editor.putString("latTemp", "19.356498");
        editor.putString("longTemp", "-99.236183");
        editor.apply();

        rt_cpvbtnubicacion.setOnClickListener(view -> {
            editor.putString("tieNombre", String.valueOf(rt_cpvedtnombremarca.getText()));
            editor.putString("tieCorreo", String.valueOf(rt_cpvedtcorreo.getText()));
            editor.putString("tieTelefono", String.valueOf(rt_cpvedttelefono.getText()));
            editor.putString("tieVentasMensuales", String.valueOf(rt_cpvedtventames.getText()));
            editor.putString("tieInventarioEstimado", String.valueOf(rt_cpvedtinventario.getText()));
            editor.commit();
            Navigation.findNavController(view).navigate(R.id.action_rt_frgcrearpuntoventa_to_rt_frgubicacion);
        }); //+}@OHC08.11.2022

        rt_cpvspnrciudad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                editor.putInt("tieCiudad", i);
                editor.commit();
                rt_fctautocompletado.rt_fctseleccionspinner(i, requireContext());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }); //+}@OHC08.11.2022

        rt_cpvbtncontinuar.setOnClickListener(view -> {
            if((String.valueOf(rt_cpvedtnombremarca.getText()).equals("")) && (String.valueOf(rt_cpvedttelefono.getText()).equals(""))){
                Toast.makeText(getActivity(), "Rellenar los campos necesarios!", Toast.LENGTH_SHORT).show();
            }
            else{
                if (preferencias.contains("Direccion")){
                    RegistroTiendaWebService(preferencias);
                    if(rt_vargobaceptacion) Navigation.findNavController(view).navigate(R.id.action_rt_frgcrearpuntoventa_to_rt_frgpuntoventacreado);

                }
                else Toast.makeText(getActivity(), "Elija una Ubicación", Toast.LENGTH_SHORT).show();
            }
        }); //+}@OHC08.11.2022

        return vista;
    } //+}@OHC08.11.2022

    private void RegistroTiendaWebService(SharedPreferences preferencias) {
        rt_pdcrearpuntoventa = new ProgressDialog(getContext());
        rt_pdcrearpuntoventa.setMessage("Cargando datos");
        rt_pdcrearpuntoventa.show();
        String url = Util.RUTA + "a_datos_tienda_registrotienda.php?"+
                "p_tieNombre="+ Objects.requireNonNull(rt_cpvedtnombremarca.getText()).toString()+
//                 "&p_tieImagen="+
                "&p_tieURLWeb=#"+
                "&p_tieDescripcion="+ ""+
                "&p_tieCorreo="+ Objects.requireNonNull(rt_cpvedtcorreo.getText()).toString()+
                "&p_tieTelefono="+ Objects.requireNonNull(rt_cpvedttelefono.getText()).toString()+
                "&p_tieDireccion="+ Objects.requireNonNull(rt_cpvedtubicacion.getText()).toString()+
                "&p_tieCiudad="+ rt_cpvspnrciudad.getSelectedItem().toString()+
                "&p_tieEstado=Abierto"+
                "&p_tieVentasMensuales="+ Objects.requireNonNull(rt_cpvedtventames.getText()).toString()+
                "&p_tieInventarioEstimado="+ Objects.requireNonNull(rt_cpvedtinventario.getText()).toString()+
                "&p_idDocumentoPersona="+ "11111111"+//camProf.getText().toString()+
                "&p_idRubroTienda="+ preferencias.getString("idRubroTienda", "")+
                "&p_tieLatitud="+ preferencias.getString("tieLatitud", "")+
                "&p_tieLongitud="+ preferencias.getString("tieLongitud", "");

        url = url.replace(" ", "%20");
        rt_jorcrearpuntoventa = new JsonObjectRequest(Request.Method.POST, url, null, this, this);
        rt_rqcrearpuntoventa.add(rt_jorcrearpuntoventa);
    } //+}@OHC08.11.2022

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();

        rt_cpvedtnombremarca.setText("");
        rt_cpvedtcorreo.setText("");
        rt_cpvedttelefono.setText("");
        rt_cpvedtventames.setText("");
        rt_cpvedtinventario.setText("");
        rt_cpvedtubicacion.setText("");
        rt_cpvspnrciudad.setSelection(0);
        rt_vargobaceptacion = true;
        rt_pdcrearpuntoventa.hide();
    } //+}@OHC08.11.2022

    @Override
    public void onErrorResponse(VolleyError error) {
        rt_pdcrearpuntoventa.hide();
        Toast.makeText(getContext(), "No se pudo registrar." + error, Toast.LENGTH_SHORT).show();
    } //+}@OHC08.11.2022
}