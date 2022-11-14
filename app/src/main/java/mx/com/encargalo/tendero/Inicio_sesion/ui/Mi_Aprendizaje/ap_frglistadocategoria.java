package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_Aprendizaje;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
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
import mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_Aprendizaje.Adapters.ap_adplistadofrgcategoria;
import mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_Aprendizaje.Entidades.Curso;
import mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_publicidad.Adapters.pub_adplistadofrglistado;
import mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_publicidad.Entidades.Publicidad;
import mx.com.encargalo.tendero.Util.Util;

public class ap_frglistadocategoria extends Fragment {

    ap_adplistadofrgcategoria ap_adpcategoria;
    RecyclerView ap_rclvcategoria;
    ArrayList<Curso> ap_listacategoria;

    Curso curso;
    RequestQueue requestQueue;
    JsonObjectRequest request;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_ap_frglistadocategoria, container, false);
        ap_rclvcategoria = vista.findViewById(R.id.ap_ccrclvlistadocurso);
        ap_rclvcategoria.setLayoutManager(new LinearLayoutManager(this.getContext()));
        ap_rclvcategoria.setHasFixedSize(true);
        ap_listacategoria = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(getContext());

        parseJSON();

        return vista;
    }

    private void parseJSON() {

        String URL = Util.RUTA+"c_consultar_materiales_por_categoria_aprendizaje.php";

        request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        curso = null;
                        JSONArray json=response.optJSONArray("matCategoria"); ///--------------------///--------------------///--------------------
                        try {
                            ap_listacategoria.clear();
                            for (int i = 0; i<json.length();i++){
                                curso=new Curso();
                                JSONObject jsonObject=null;
                                jsonObject=json.getJSONObject(i);
                                curso.setAp_varstrnombrecurso(jsonObject.optString("apreTituloRecurso"));
                                ap_listacategoria.add(curso);

                            }
                            ap_adpcategoria = new ap_adplistadofrgcategoria(getContext(), ap_listacategoria);
                            ap_rclvcategoria.setAdapter(ap_adpcategoria);
                            ap_adpcategoria.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String ap_varlocnombrecurso = ap_listacategoria.get(ap_rclvcategoria.getChildAdapterPosition(view)).getAp_varstrnombrecurso();
                                    Toast.makeText(getContext(), "Seleccionó: " + ap_varlocnombrecurso, Toast.LENGTH_SHORT).show();
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