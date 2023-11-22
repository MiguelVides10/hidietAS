package sv.edu.catolica.hidiet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText tiUser, tiPass;
    private String resultado;
    private SharedPreferences preferences;
    private SharedPreferences.Editor  editor;
    private CheckBox chSesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tiUser = findViewById(R.id.tiUser);
        tiPass = findViewById(R.id.tiPasw);
        chSesi = findViewById(R.id.chkSesion);
        preferences = this.getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();
        if(checkSesion() && getId()>0){
            Intent intent = new Intent(getApplicationContext(), PrincipalMenu.class);
            intent.putExtra("ID",getId());
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }else {
            Toast.makeText(MainActivity.this, "No hay sesión guardada", Toast.LENGTH_SHORT).show();
        }

    }
    public boolean checkSesion(){
        return this.preferences.getBoolean("KEY_SESSION",false);
    }

    public int getId(){
        return  this.preferences.getInt("ID_USER",0);
    }
    public void VerificarDatos(View view) {
        String correo=tiUser.getText().toString();
        String contra = tiPass.getText().toString();
        String url = "http://18.222.197.239:8000/api/usuarios/"+correo+"/"+contra+"/";
        AsyncHttpClient cliente = new AsyncHttpClient();

        cliente.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String respuesta = new String(responseBody);
                    JSONObject MiJson = new JSONObject(respuesta);

                    if (MiJson.getString("message").equals("success")){
                        JSONObject usuarioObj = MiJson.getJSONObject("usuarios");
                        int id_usuario =usuarioObj.getInt("id_usuario");
                        resultado= usuarioObj.getString("correo")+ "\n"+
                                "Bienvenido "+usuarioObj.getString("contrasenia");
                        guardarSesion(chSesi.isChecked(),id_usuario);
                        Intent intent = new Intent(MainActivity.this, PrincipalMenu.class);
                        intent.putExtra("ID",id_usuario);
                        startActivity(intent);
                        finish();
                    }else {
                        resultado = MiJson.getString("error");
                    }
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this,"Error Json", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(MainActivity.this,"No encontrado", Toast.LENGTH_SHORT ).show();
            }
        });
    }

    public void guardarSesion(boolean check,int id_perfil){
        editor.putBoolean("KEY_SESSION",check);
        editor.putInt("ID_USER",id_perfil);
        editor.apply();

    }

    public void CrearCuenta(View view) {

        startActivity(new Intent(getApplicationContext(),Perfil.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
//        String url = "http://18.222.197.239:8000/api/usuariopost/";
//        JSONObject data = new JSONObject();
//        String correo = tiUser.getText().toString();
//        String contra = tiPass.getText().toString();
//
//        if(!correo.isEmpty() && !contra.isEmpty()){
//            try {
//                data.put("correo", tiUser);
//                data.put("contrasenia", tiPass);
//                // Agrega más claves y valores según sea necesario
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            // Crear un objeto RequestParams y agregar el JSON como parámetro
//            RequestParams params = new RequestParams();
//            params.put("data", data);
//            AsyncHttpClient client = new AsyncHttpClient();
//
//            String json = data.toString();
//            client.post( url, params, new JsonHttpResponseHandler() {
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                    try {
//                            if(response.getString("message").equals("usuario creado")){
//                                Toast.makeText(MainActivity.this, "Usuario creado con éxito",Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(MainActivity.this, "El usuario no se pudo crear",Toast.LENGTH_SHORT).show();
//                            }
//                    } catch (JSONException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                    // Manejar la respuesta fallida aquí
//                    Toast.makeText(MainActivity.this, "Error al conectar con el server",Toast.LENGTH_SHORT).show();
//                }
//            });
        }

    }