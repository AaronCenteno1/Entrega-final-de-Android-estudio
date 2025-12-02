package com.example.entregafinalaaron;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.entregafinalaaron.db.DBHelper;

public class MainActivity extends AppCompatActivity {

    EditText etUsuario, etPassword;
    Button btnLogin, btnCrear;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar DBHelper
        db = new DBHelper(this);

        // Referencias a vistas
        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCrear = findViewById(R.id.btnCrear);

        // boton LOGIN
        btnLogin.setOnClickListener(v -> {
            String usuario = etUsuario.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (usuario.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar login y obtener tipo
            String tipo = db.validarLogin(usuario, password);

            if (tipo == null) {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            } else if (tipo.equals("admin")) {
                Toast.makeText(this, "Bienvenido Admin", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, AdminActivity.class));
            } else { // usuario normal
                Toast.makeText(this, "Bienvenido Usuario", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, UsuarioActivity.class));
            }
        });

        // IR A REGISTRO
        btnCrear.setOnClickListener(v ->
                startActivity(new Intent(this, RegistroActiviti.class))
        );
    }
}