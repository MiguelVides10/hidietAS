package sv.edu.catolica.hidiet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class PerfilMenu extends AppCompatActivity {
    private TextInputEditText ticorreo, tiSexo, tiPeso, tiAlt;
    public int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_menu);
        ticorreo = findViewById(R.id.tiCorreo);
        tiSexo = findViewById(R.id.tiSexo);
        tiPeso = findViewById(R.id.tiPeso);
        tiAlt = findViewById(R.id.tiAltura);

        Intent intent1 = getIntent();
        if (intent1.hasExtra("ID")){
            this.id = intent1.getIntExtra("ID",0);
            cargarDatos(id);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.bottom_home) {
                startActivity(new Intent(getApplicationContext(), PrincipalMenu.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.bottom_progreso) {
                // Lógica para el elemento "bottom_progreso"
                startActivity(new Intent(getApplicationContext(), ProgresoMenu.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.bottom_dieta) {
                Intent intent = new Intent(PerfilMenu.this, DietaMenu.class);
                intent.putExtra("ID",this.id);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                finish();
                return true;
            } else if (itemId == R.id.bottom_perfil) {
                Intent intent = new Intent(PerfilMenu.this, PerfilMenu.class);
                intent.putExtra("ID",this.id);
                startActivity(intent);
                finish();
                return true;
            } else if (itemId == R.id.bottom_acerca) {
                startActivity(new Intent(getApplicationContext(), AcercaMenu.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }

            return false;
        });
    }

    public void EditarPerfil (View view){
        startActivity(new Intent(getApplicationContext(), EditarPerfil.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void cargarDatos(int id){
        String url = "http://18.222.197.239:8000/api/perfil/"+id;
        JSONObject data = new JSONObject();

        AsyncHttpClient cliente = new AsyncHttpClient();

        cliente.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String respuesta = new String(responseBody);
                    JSONObject MiJson = new JSONObject(respuesta);

                    if (MiJson.getString("message").equals("success")){
                        JSONArray perfilArray = MiJson.getJSONArray("perfil");
                        JSONObject jsPerfilObj = perfilArray.getJSONObject(0);
                        ticorreo.setText(jsPerfilObj.getString("correo"));
                        tiSexo.setText(jsPerfilObj.getString("sexo"));
                        tiPeso.setText(jsPerfilObj.getString("peso")+" Kg");
                        tiAlt.setText(jsPerfilObj.getString("estatura")+ " M");
                    }else {
                        Toast.makeText(PerfilMenu.this, "No se puedieron cargar los datos", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(PerfilMenu.this,"Error Json", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(PerfilMenu.this,"No encontrado", Toast.LENGTH_SHORT ).show();
            }
        });
    }

}