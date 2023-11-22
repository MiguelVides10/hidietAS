package sv.edu.catolica.hidiet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class DietaMenu extends AppCompatActivity {
    private TextView tvDesayuno, tvAlmuerzo, tvCena;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dieta_menu);

        tvDesayuno = findViewById(R.id.tvdesayuno1);
        tvAlmuerzo = findViewById(R.id.txtAlmue);
        tvCena = findViewById(R.id.tvCena1);

        Intent intent1 = getIntent();
        if (intent1.hasExtra("ID")){
            this.id = intent1.getIntExtra("ID",0);
            cargarDietas(id);
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
                Intent intent = new Intent(DietaMenu.this, DietaMenu.class);
                intent.putExtra("ID",this.id);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.bottom_perfil) {
                Intent intent = new Intent(DietaMenu.this, PerfilMenu.class);
                intent.putExtra("ID",this.id);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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

    public void cargarDietas(int id){
        String url = "http://18.222.197.239:8000/api/menus/"+id;
        JSONObject data = new JSONObject();

        AsyncHttpClient cliente = new AsyncHttpClient();

        cliente.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String respuesta = new String(responseBody);
                    JSONObject MiJson = new JSONObject(respuesta);

                    if (MiJson.getString("message").equals("success")){
                        JSONArray menuArray = MiJson.getJSONArray("menu");
                        JSONObject jsMenuObj = menuArray.getJSONObject(0);
                        tvDesayuno.setText(jsMenuObj.getString("desayuno"));
                        tvAlmuerzo.setText(jsMenuObj.getString("almuerzo"));
                        tvCena.setText(jsMenuObj.getString("cena"));
                    }else {
                        Toast.makeText(DietaMenu.this, "No se puedieron cargar los datos", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(DietaMenu.this,"Error Json", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(DietaMenu.this,"No encontrado", Toast.LENGTH_SHORT ).show();
            }
        });
    }
}