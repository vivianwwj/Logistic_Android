package com.wwj.firstpage;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.wdy.login.LoginActivity;

public class FirstPage extends AppCompatActivity {

    private Button myLoginCangchu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        myLoginCangchu = findViewById(R.id.btn_entercangchu);
        //点击实现跳转
        myLoginCangchu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(FirstPage.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


}