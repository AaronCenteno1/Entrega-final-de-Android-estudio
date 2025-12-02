package com.example.entregafinalaaron;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.entregafinalaaron.db.DBHelper;

public class RegistroActiviti extends AppCompatActivity {

    EditText etNuevoUsuario, etNuevoPassword;
    Button btnRegistrar;
    DBHelper db;
 // Inicializa la actividad y la interfaz de usuario Vincula las vistas del layout con las variables del código.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        db = new DBHelper(this);

        etNuevoUsuario = findViewById(R.id.etNuevoUsuario);
        etNuevoPassword = findViewById(R.id.etNuevoPassword);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = etNuevoUsuario.getText().toString();
                String password = etNuevoPassword.getText().toString();

                if (usuario.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegistroActiviti.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Registrar usuario normal
                boolean ok = db.registrarUsuario(usuario, password);

                if (ok) {
                    Toast.makeText(RegistroActiviti.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                    finish(); // vuelve al login
                } else {
                    Toast.makeText(RegistroActiviti.this, "Error: usuario ya existe", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
