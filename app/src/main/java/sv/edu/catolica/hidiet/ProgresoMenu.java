package sv.edu.catolica.hidiet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProgresoMenu extends AppCompatActivity {
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progreso_menu);
        Intent intent = getIntent();
        if (intent.hasExtra("ID")){
            this.id = intent.getIntExtra("ID",0);
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
                Intent intent1 = new Intent(ProgresoMenu.this, DietaMenu.class);
                intent1.putExtra("ID",this.id);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.bottom_perfil) {
                Intent intent1 = new Intent(ProgresoMenu.this, PerfilMenu.class);
                intent1.putExtra("ID",this.id);
                startActivity(intent1);
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
}