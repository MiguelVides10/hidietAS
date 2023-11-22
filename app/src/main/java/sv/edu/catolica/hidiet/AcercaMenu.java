package sv.edu.catolica.hidiet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.content.Intent;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AcercaMenu extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_menu);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.bottom_home) {
                startActivity(new Intent(getApplicationContext(), AcercaMenu.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.bottom_progreso) {
                // Lógica para el elemento "bottom_progreso"
                return true;
            } else if (itemId == R.id.bottom_dieta) {
                // Lógica para el elemento "bottom_progreso"
                return true;
            } else if (itemId == R.id.bottom_perfil) {
                startActivity(new Intent(getApplicationContext(), PerfilMenu.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.bottom_acerca) {
                return true;
            }

            return false;
        });
    }
}