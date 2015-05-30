package ru.firsto.yac;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ru.firsto.yac.Util.Saver;


public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button newGameButton = (Button) findViewById(R.id.button_new_game);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.putExtra("NEW GAME", true);
                startActivity(intent);
            }
        });

        Button continueButton = (Button) findViewById(R.id.button_continue);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Saver.load(getApplicationContext(), Game.get());
                startActivity(new Intent(getApplicationContext(), GameActivity.class));
            }
        });

        Button saveButton = (Button) findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Saver.save(getApplicationContext(), Game.get().getResources(), Game.get().getStartgame(), ItemPool.get().getLevels(), Statistics.get().statistics());
            }
        });
    }
}
