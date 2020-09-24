package com.example.yasatravels;



        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.app.ActivityOptionsCompat;
        import androidx.core.view.ViewCompat;

        import android.content.Intent;
        import android.os.Bundle;
        import android.os.Handler;
        import android.transition.Fade;
        import android.view.View;
        import android.view.WindowManager;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.ImageView;
        import android.widget.TextView;

        import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    //declaring variables
    Animation topAnim, botAnim;
    ImageView startupImg;
    TextView appName;
    //5 secs
    private static int STARTUP_SCREEN = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // To hide status bar from the starting activity window
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.from_top);
        botAnim = AnimationUtils.loadAnimation(this, R.anim.from_bottom);

        //Hooks
        startupImg = findViewById(R.id.startupImg);
        appName = findViewById(R.id.appName);

        //Assign Animations
        startupImg.setAnimation(topAnim);
        appName.setAnimation(botAnim);


        //To handle delay process and start another activity window
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                //To remove this activity from activity list we use finish() - now users cannot open startup screen by pressing bak button
                finish();
            }
        }, STARTUP_SCREEN);
    }
}