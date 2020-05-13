package com.pi.efilm.view.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.pi.efilm.R;
import com.squareup.picasso.Picasso;

public class FotoDetalheActivity extends AppCompatActivity {
    private ImageView imageView;
    private String fotoString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_detalhe);
        imageView = findViewById(R.id.imagemFoto);
        Bundle bundle = getIntent().getExtras();
        fotoString = bundle.getString(getString(R.string.foto));
        Picasso.get().load(fotoString).into(imageView);
    }
}
