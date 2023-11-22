package sv.edu.catolica.hidiet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void VerificarDatos(View view) {
        Intent intent = new Intent(getApplicationContext(), PrincipalMenu.class);
        startActivity(intent);

        // Opcional: Puedes agregar transiciones de animación entre Activities
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        // Opcional: Puedes finalizar la Activity actual si ya no la necesitas
        finish();
    }
}