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
import com.pi.efilm.model.pessoa.pessoa.ResultPessoaPop;
import com.pi.efilm.util.Constantes;
import com.pi.efilm.view.activity.PessoaDetalheActivity;
import com.squareup.picasso.Picasso;
import java.util.List;
import static com.pi.efilm.util.Constantes.ID;

public class PessoaPopularAdapter extends RecyclerView.Adapter<PessoaPopularAdapter.ViewHolder> {
    private List<ResultPessoaPop> pessoaPopList;

    public PessoaPopularAdapter(List<ResultPessoaPop> pessoaPopList) {
        this.pessoaPopList = pessoaPopList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elenco_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResultPessoaPop pessoaPop = pessoaPopList.get(position);
        holder.onBind(pessoaPop);
    }

    public void atualizaLista(List<ResultPessoaPop> novaLista) {
        if (pessoaPopList.isEmpty()) {
            pessoaPopList = novaLista;
        } else {
            pessoaPopList.addAll(novaLista);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return pessoaPopList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imagem;
        private TextView nome;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imagem = itemView.findViewById(R.id.imagemFilmografia);
            nome = itemView.findViewById(R.id.personagemFilmografia);
        }

        public void onBind(ResultPessoaPop resultPessoaPop) {
            nome.setText(resultPessoaPop.getName());
            Picasso.get().load(Constantes.URL_IMAGEM + resultPessoaPop.getProfilePath()).into(imagem);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), PessoaDetalheActivity.class);
            intent.putExtra(ID, pessoaPopList.get(getAdapterPosition()).getId());
            v.getContext().startActivity(intent);
        }
    }
}
