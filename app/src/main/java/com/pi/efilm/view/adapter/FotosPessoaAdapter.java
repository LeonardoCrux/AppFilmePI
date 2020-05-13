package com.pi.efilm.view.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.pi.efilm.R;
import com.pi.efilm.model.pessoa.Profile;
import com.pi.efilm.util.Constantes;
import com.pi.efilm.view.activity.FotoDetalheActivity;
import com.squareup.picasso.Picasso;
import java.util.List;

public class FotosPessoaAdapter extends RecyclerView.Adapter<FotosPessoaAdapter.ViewHolder> {
    private List<Profile> fotosPessoaList;

    public FotosPessoaAdapter(List<Profile> fotosPessoaList) {
        this.fotosPessoaList = fotosPessoaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.foto_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Profile profile = fotosPessoaList.get(position);
        holder.onBind(profile);
    }

    public void atualizaLista(List<Profile> novaLista) {
        if (fotosPessoaList.isEmpty()) {
            this.fotosPessoaList = novaLista;
        } else {
            this.fotosPessoaList.addAll(novaLista);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return fotosPessoaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView fotoPessoa;
        private TextView textView;

        public ViewHolder(@NonNull View v) {
            super(v);
            v.setOnClickListener(this);
            fotoPessoa = v.findViewById(R.id.imageRecyclerFotos);
        }

        public void onBind(Profile profile) {
            Picasso.get().load(Constantes.URL_IMAGEM + profile.getFilePath()).into(fotoPessoa);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), FotoDetalheActivity.class);
            String foto = fotosPessoaList.get(getAdapterPosition()).getFilePath();
            intent.putExtra("FOTO", Constantes.URL_IMAGEM + foto);
            v.getContext().startActivity(intent);
        }
    }
}
