package sv.edu.catolica.hidiet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

        ImageView splashImageView = findViewById(R.id.splashImageView);

        // Configura la animación en tu ImageView
        Animation splashAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
        splashImageView.startAnimation(splashAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Inicia la siguiente actividad
                Intent intent = new Intent(Home.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000); // 3000 milisegundos (3 segundos) de retraso antes de pasar a la siguiente actividad

    }
}