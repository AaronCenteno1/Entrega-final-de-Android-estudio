package com.example.entregafinalaaron;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PokemonActivity extends AppCompatActivity {

    EditText etBuscar;
    Button btnBuscar;
    ImageView ivPokemon;

    TextView tvBienvenida, tvNombre, tvId, tvTipo, tvAltura, tvPeso, tvHabilidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        // Referencias a mi xml
        etBuscar = findViewById(R.id.etBuscar);
        btnBuscar = findViewById(R.id.btnBuscar);
        ivPokemon = findViewById(R.id.ivPokemon);

        tvBienvenida = findViewById(R.id.tvBienvenida);
        tvNombre = findViewById(R.id.tvNombre);
        tvId = findViewById(R.id.tvId);
        tvTipo = findViewById(R.id.tvTipo);
        tvAltura = findViewById(R.id.tvAltura);
        tvPeso = findViewById(R.id.tvPeso);
        tvHabilidad = findViewById(R.id.tvHabilidad);

        // Mensaje de bienvenida si  si viene a Usuarioactivity
        String usuario = getIntent().getStringExtra("usuario");
        if (usuario != null) {
            tvBienvenida.setText("¡Bienvenido " + usuario + "!");
        }

        btnBuscar.setOnClickListener(v -> {
            String query = etBuscar.getText().toString().trim().toLowerCase();

            if (query.isEmpty()) {
                Toast.makeText(this, "Ingresa un nombre o ID", Toast.LENGTH_SHORT).show();
                return;
            }

            buscarPokemon(query);
        });
    }

    // Esta función busca un Pokémon en la Pokémon API usando el nombre o ID ingresado por el usuario.
    private void buscarPokemon(String query) {
        new Thread(() -> {
            try {
                URL url = new URL("https://pokeapi.co/api/v2/pokemon/" + query);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();

                if (conn.getResponseCode() != 200) {
                    runOnUiThread(() ->
                            Toast.makeText(this, "Pokémon no encontrado", Toast.LENGTH_SHORT).show()
                    );
                    return;
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder json = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) json.append(line);
                br.close();

                JSONObject data = new JSONObject(json.toString());

                // Extraer datos
                String nombre = data.getString("name");
                int id = data.getInt("id");

                String tipo = data.getJSONArray("types")
                        .getJSONObject(0)
                        .getJSONObject("type")
                        .getString("name");

                String altura = data.getInt("height") + " dm";
                String peso = data.getInt("weight") + " hg";

                String habilidad = data.getJSONArray("abilities")
                        .getJSONObject(0)
                        .getJSONObject("ability")
                        .getString("name");

                String imageUrl =
                        data.getJSONObject("sprites")
                                .getString("front_default");

                // Descargar imagen
                URL imgUrl = new URL(imageUrl);
                HttpURLConnection imgConn = (HttpURLConnection) imgUrl.openConnection();
                imgConn.connect();
                InputStream imgStream = imgConn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(imgStream);

                // Mostrar en UI
                runOnUiThread(() -> {
                    ivPokemon.setImageBitmap(bitmap);
                    tvNombre.setText("Nombre: " + nombre);
                    tvId.setText("ID: " + id);
                    tvTipo.setText("Tipo: " + tipo);
                    tvAltura.setText("Altura: " + altura);
                    tvPeso.setText("Peso: " + peso);
                    tvHabilidad.setText("Habilidad: " + habilidad);
                });

            } catch (Exception e) {
                runOnUiThread(() ->
                        Toast.makeText(this, "Error al buscar Pokémon", Toast.LENGTH_SHORT).show()
                );
                e.printStackTrace();
            }
        }).start();
    }
}