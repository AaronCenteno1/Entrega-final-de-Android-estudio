package com.example.entregafinalaaron;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

public class UsuarioActivity extends AppCompatActivity {

    EditText etFirstName, etLastName, etAge;
    TextView tvBirthday;
    Button btnSelectBirthday, btnTakePhoto, btnSave, btnPokemon;
    ImageView ivPhoto;

    private static final int REQUEST_CAMERA = 100;
    private String birthday = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        // Referencias a vistas
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etAge = findViewById(R.id.etAge);
        tvBirthday = findViewById(R.id.tvBirthday);
        btnSelectBirthday = findViewById(R.id.btnSelectBirthday);
        btnTakePhoto = findViewById(R.id.btnTakePhoto);
        btnSave = findViewById(R.id.btnSave);
        ivPhoto = findViewById(R.id.ivPhoto);
        btnPokemon = findViewById(R.id.btnPokemon);

        // Seleccionar cumpleaños del usaurio
        btnSelectBirthday.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(this, (view, y, m, d) -> {
                birthday = d + "/" + (m + 1) + "/" + y;
                tvBirthday.setText("Cumpleaños: " + birthday);
            }, year, month, day);
            dpd.show();
        });

        // Tomar foto del usuario
        btnTakePhoto.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA);
        });

        // Guardar datos usuario
        btnSave.setOnClickListener(v -> {
            String firstName = etFirstName.getText().toString().trim();
            String lastName = etLastName.getText().toString().trim();
            String age = etAge.getText().toString().trim();

            if (firstName.isEmpty() || lastName.isEmpty() || age.isEmpty() || birthday.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Perfil guardado:\n" +
                            firstName + " " + lastName + ", Edad: " + age + ", Cumpleaños: " + birthday,
                    Toast.LENGTH_LONG).show();
        });

        //  BOTÓN NUEVo IR A POKÉMON
        btnPokemon.setOnClickListener(v -> {
            Intent intent = new Intent(UsuarioActivity.this, PokemonActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ivPhoto.setImageBitmap(photo);
            Toast.makeText(this, "Foto tomada", Toast.LENGTH_SHORT).show();
        }
    }
}