package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_tienda;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Adapter.rt_adaptiendaspersona;
import mx.com.encargalo.tendero.Entidad.rt_EntidadTiendasPersona;
import mx.com.encargalo.tendero.Util.Util;


public class rt_frginicio extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    Button rt_ie1btnpuntoventa; //+@OHC04.11.2022
    RecyclerView rt_ierclvtiendas; //+@OHC04.11.2022

    ArrayList<rt_EntidadTiendasPersona> rt_arltiendaspersona; //+@OHC04.11.2022
    ProgressDialog rt_pdtiendaspersona; //+@OHC04.11.2022
    RequestQueue rt_rqtiendaspersona; //+@OHC04.11.2022
    JsonObjectRequest rt_jortiendaspersona; //+@OHC04.11.2022

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_rt_frginicio, container, false);

        rt_arltiendaspersona = new ArrayList<>(); //+@OHC04.11.2022
        rt_ierclvtiendas = vista.findViewById(R.id.rt_ierclvtiendas); //+@OHC04.11.2022
        rt_ierclvtiendas.setLayoutManager(new LinearLayoutManager(this.getContext())); //+@OHC04.11.2022
        rt_ierclvtiendas.setHasFixedSize(true); //+@OHC04.11.2022

        rt_rqtiendaspersona = Volley.newRequestQueue(requireContext()); //+@OHC04.11.2022
        rt_fctWebServiceTiendasPersona(); //+@OHC04.11.2022

        rt_ie1btnpuntoventa=vista.findViewById(R.id.rt_ie1btnpuntoventa); //+@OHC04.11.2022

        rt_ie1btnpuntoventa.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_nav_mi_tienda_to_rt_frgcategoriatienda)); //+}@OHC04.11.2022

        return vista;
    }

    private void rt_fctWebServiceTiendasPersona() {

        rt_pdtiendaspersona = new ProgressDialog(getContext());
        rt_pdtiendaspersona.setMessage("Consultando Tiendas");
        rt_pdtiendaspersona.show();

        String url= Util.RUTA + "c_tiendas_por_persona_registrotienda.php?p_idDocumentoPersona=11111111";
        url=url.replace(" ","%20");
        rt_jortiendaspersona= new JsonObjectRequest(Request.Method.GET, url,null,this, this);
        rt_rqtiendaspersona.add(rt_jortiendaspersona);

    } //+}@OHC04.11.2022

    @Override
    public void onErrorResponse(VolleyError error) {
        rt_pdtiendaspersona.hide();
        Toast.makeText(getContext(), "Error al consultar " + error.toString(), Toast.LENGTH_SHORT).show();
    } //+}@OHC04.11.2022

    @Override
    public void onResponse(JSONObject response) {
        rt_pdtiendaspersona.hide();
        rt_EntidadTiendasPersona rt_etmistiendas;
        JSONArray json=response.optJSONArray("tiendas_personas");
        try {
            for (int i = 0; i < Objects.requireNonNull(json).length(); i++) {
                rt_etmistiendas = new rt_EntidadTiendasPersona();

                JSONObject rt_jojsonObject;
                rt_jojsonObject = json.getJSONObject(i);
                rt_etmistiendas.setIdTienda(rt_jojsonObject.optInt("idTienda"));
                rt_etmistiendas.setTieNombre(rt_jojsonObject.optString("tieNombre"));

                rt_arltiendaspersona.add(rt_etmistiendas);
            }
            rt_pdtiendaspersona.hide();

            rt_adaptiendaspersona adapter = new rt_adaptiendaspersona(getContext(), rt_arltiendaspersona,
                    (item, vista) -> {
                        //CONTENIDO
                    });
            rt_ierclvtiendas.setAdapter(adapter);
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "No hay conexión con el servidor", Toast.LENGTH_SHORT).show();
            rt_pdtiendaspersona.hide();
        }
    } //+}@OHC04.11.2022
}