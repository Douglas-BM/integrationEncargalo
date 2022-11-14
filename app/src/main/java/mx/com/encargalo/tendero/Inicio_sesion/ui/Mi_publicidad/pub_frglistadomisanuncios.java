package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_publicidad;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_publicidad.Adapters.pub_adplistadofrglistado;
import mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_publicidad.Entidades.Publicidad;

public class pub_frglistadomisanuncios extends Fragment {

    pub_adplistadofrglistado pub_adplista;
    RecyclerView pub_rclvpublicidad;
    ArrayList<Publicidad> pub_listapublicidad;

    Publicidad publicidad;
    RequestQueue requestQueue;
    JsonObjectRequest request;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_pub_frglistadomisanuncios, container, false);
        pub_rclvpublicidad = vista.findViewById(R.id.pub_lmarclvmisanuncios);
        pub_rclvpublicidad.setLayoutManager(new LinearLayoutManager(this.getContext()));
        pub_rclvpublicidad.setHasFixedSize(true);
        pub_listapublicidad = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(getContext());

        parseJSON();

        return vista;
    }

    private void parseJSON() {
        String URL = "http://129.151.103.228/Encargalo/APIS/TenderoApp/c_mostrar_detalle_publicidad.php?id_Tienda=1";

        request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        publicidad = null;
                        JSONArray json=response.optJSONArray("D_publicidad"); ///--------------------///--------------------///--------------------
                        try {
                            pub_listapublicidad.clear();
                            for (int i = 0; i<json.length();i++){
                                publicidad=new Publicidad();
                                JSONObject jsonObject=null;
                                jsonObject=json.getJSONObject(i);
                                publicidad.setPub_strtitulo(jsonObject.optString("pubTitulo"));
                                publicidad.setPub_strfecha(jsonObject.optString("pubFechaInicio"));
                                publicidad.setPub_strduracion(jsonObject.optString("fecha_Dias"));
                                publicidad.setPub_strvistas(jsonObject.optString("pubCantVistas"));
                                publicidad.setPub_intimagenid(jsonObject.optString("pubImagen"));
                                pub_listapublicidad.add(publicidad);

                            }
                            pub_adplista = new pub_adplistadofrglistado(getContext(), pub_listapublicidad);
                            pub_rclvpublicidad.setAdapter(pub_adplista);
                            pub_adplista.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String pub_strtitulo = pub_listapublicidad.get(pub_rclvpublicidad.getChildAdapterPosition(view)).getPub_strtitulo();
                                    Toast.makeText(getContext(), "Selecciono: " + pub_strtitulo, Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(view).navigate(R.id.nav_detalleanuncio);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error al consultar"+ error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(request);

    }
}