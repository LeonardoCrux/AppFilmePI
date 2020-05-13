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
import com.pi.efilm.model.filme.creditos.Cast;
import com.pi.efilm.util.Constantes;
import com.pi.efilm.view.activity.PessoaDetalheActivity;
import com.squareup.picasso.Picasso;
import java.util.List;
import static com.pi.efilm.util.Constantes.ID;

public class ElencoAdapter extends RecyclerView.Adapter<ElencoAdapter.ViewHolder> {
    private List<Cast> listaCast;

    public ElencoAdapter(List<Cast> listaCast) {
        this.listaCast = listaCast;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elenco_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cast cast = listaCast.get(position);
        holder.onBind(cast);
    }

    public void atualizaLista(List<Cast> novaLista) {
        if (listaCast.isEmpty()) {
            this.listaCast = novaLista;
        } else {
            this.listaCast.addAll(novaLista);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaCast.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imagemElenco;
        private TextView textNome;
        private TextView textPersonagem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imagemElenco = itemView.findViewById(R.id.imagemFilmografia);
            textNome = itemView.findViewById(R.id.personagemFilmografia);
            textPersonagem = itemView.findViewById(R.id.personagemElenco);
        }

        public void onBind(Cast cast) {
            Picasso.get().load(Constantes.URL_IMAGEM + cast.getProfilePath()).into(imagemElenco);
            textNome.setText(cast.getName());
            textPersonagem.setText(cast.getCharacter());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), PessoaDetalheActivity.class);
            intent.putExtra(ID, listaCast.get(getAdapterPosition()).getId());
            v.getContext().startActivity(intent);
        }
    }
}
