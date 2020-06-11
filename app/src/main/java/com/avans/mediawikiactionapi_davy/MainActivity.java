package com.avans.mediawikiactionapi_davy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public Button enterMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        enterMenuButton = findViewById(R.id.enterAppButton);

        enterMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleEnterMenuButton();
             
            }
        });

    }

    private void handleEnterMenuButton() {
        Intent intent = new Intent(this, RecyclerView.class);
        startActivity(intent);
    }
}
