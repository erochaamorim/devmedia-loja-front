package app.devmedia.com.br.appdevmedia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import app.devmedia.com.br.appdevmedia.service.LoginService;
import app.devmedia.com.br.appdevmedia.validation.LoginValidation;

public class LoginActivity extends AppCompatActivity {

    protected EditText edtLogin, edtSenha;
    protected Button btnLogar;
    protected SharedPreferences preferences;
    protected LoginService loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginService = new LoginService();
        preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        String login = preferences.getString("login", null);
        String senha = preferences.getString("senha", null);
        if(login != null && senha != null) {

            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();

        }
        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnLogar = (Button) findViewById(R.id.btnLogar);
        btnLogar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String login = edtLogin.getText().toString();
                String senha = edtSenha.getText().toString();
                LoginValidation validation = new LoginValidation();
                validation.setActivity(LoginActivity.this);
                validation.setEdtLogin(edtLogin);
                validation.setEdtSenha(edtSenha);
                validation.setLogin(login);
                validation.setSenha(senha);
                loginService.validarCamposLogin(validation);

            }

        });

    }
}
