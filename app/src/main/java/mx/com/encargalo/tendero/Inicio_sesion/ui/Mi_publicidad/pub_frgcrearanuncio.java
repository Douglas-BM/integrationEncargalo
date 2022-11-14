package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_publicidad;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Util.Util;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class pub_frgcrearanuncio extends Fragment {
    Button pub_cabtncontinuar, pub_cabtnseleccionarimg;
    ImageView pub_caimgvwpubcargada;
    EditText pub_caedttituloanuncio, pub_caedttextoanuncio, pub_caedtlinkanuncio;
    TextView pub_caedtfechainitanuncio, pub_caedtfechafinanuncio;
    CheckBox pub_cachkbaceptarterminos;
    DatePickerDialog.OnDateSetListener setListener1, setListener2;
    Calendar calendar;
    Dialog dialog;

    int TOMAR_FOTO = 100;
    int SELEC_IMAGEN = 200;
    boolean cimg=false;
    private Uri imageUri;
    Bitmap bitmapimgselecionada;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_pub_frgcrearanuncio, container, false);

        pub_cabtncontinuar=vista.findViewById(R.id.pub_cabtncontinuar);
        pub_cabtnseleccionarimg=vista.findViewById(R.id.pub_cabtnseleccionarimg);
        pub_caimgvwpubcargada=vista.findViewById(R.id.pub_caimgvwpubcargada);
        pub_caedttituloanuncio=vista.findViewById(R.id.pub_caedttituloanuncio);
        pub_caedttextoanuncio=vista.findViewById(R.id.pub_caedttextoanuncio);
        pub_caedtlinkanuncio=vista.findViewById(R.id.pub_caedtlinkanuncio);
        pub_caedtfechainitanuncio=vista.findViewById(R.id.pub_caedtfechainitanuncio);
        pub_caedtfechafinanuncio=vista.findViewById(R.id.pub_caedtfechafinanuncio);
        pub_cachkbaceptarterminos=vista.findViewById(R.id.pub_cachkbaceptarterminos);
        calendar = Calendar.getInstance();

        final int año = calendar.get(Calendar.YEAR);
        final int mes = calendar.get(Calendar.MONTH);
        final int dia = calendar.get(Calendar.DAY_OF_MONTH);

        pub_caedtfechainitanuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        android.R.style.Theme_Holo_Dialog_MinWidth, setListener1, año, mes, dia
                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                pub_caedtfechainitanuncio.setText(year+"/"+month+"/"+dayOfMonth);
            }
        };
        pub_caedtfechafinanuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        android.R.style.Theme_Holo_Dialog_MinWidth, setListener2, año, mes, dia
                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                pub_caedtfechafinanuncio.setText(year+"/"+month+"/"+dayOfMonth);
            }
        };
        pub_cabtnseleccionarimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirAccion();
            }
        });
        pub_cabtncontinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences =
                        getContext().getSharedPreferences(Util.SHAREDPREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("pathImg", getStringImagen(bitmapimgselecionada));
                editor.putString("titulo", pub_caedttituloanuncio.getText().toString());
                editor.putString("descripcion", pub_caedttextoanuncio.getText().toString());
                editor.putString("linkRed", pub_caedtlinkanuncio.getText().toString());
                editor.putString("fechaInicio", pub_caedtfechainitanuncio.getText().toString());
                editor.putString("fechaFin", pub_caedtfechafinanuncio.getText().toString());
                editor.apply();
                Navigation.findNavController(view).navigate(R.id.nav_pagaranuncios);
            }
        });
        return vista;
    }
    public void elegirAccion(){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.pub_dialog_camara_galeria);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        Button camara = dialog.findViewById(R.id.btn_Camara);
        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                tomarFoto();
            }
        });
        Button galeria = dialog.findViewById(R.id.btn_Galeria);
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                seleccionarImagen();
            }
        });
        dialog.show();
    }
    public void tomarFoto() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Titulo de la Imagen");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripción de la imagen");
        imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, TOMAR_FOTO);
    }
    public void seleccionarImagen() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, SELEC_IMAGEN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK){
                if(requestCode == SELEC_IMAGEN){
                    CropImage.activity(data.getData())
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(2, 1)
                            .setBorderCornerColor(Color.BLACK)
                            .start(getContext(), this);
                }
                else if(requestCode == TOMAR_FOTO){
                    CropImage.activity(imageUri)
                            .setAspectRatio(2, 1)
                            .setBorderCornerColor(Color.BLACK)
                            .start(getContext(), this);
                }
                else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                    //Croped image received
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (resultCode == RESULT_OK){
                        Uri resultUri = result.getUri();
                        imageUri = resultUri;
                        pub_caimgvwpubcargada.setImageURI(resultUri);
                        Bitmap bm = ((BitmapDrawable)pub_caimgvwpubcargada.getDrawable()).getBitmap();
                        bitmapimgselecionada=Bitmap.createScaledBitmap(bm, 100, 100, true);
                        cimg=true;
                    }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                        Exception error = result.getError();
                        Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }catch (Exception e){
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public String getStringImagen(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}