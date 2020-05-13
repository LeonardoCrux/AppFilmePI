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
import com.pi.efilm.view.activity.PessoaDetalheActivity;
import com.squareup.picasso.Picasso;
import java.util.List;
import static com.pi.efilm.util.Constantes.ID;
import static com.pi.efilm.util.Constantes.URL_IMAGEM;

public class SearchPessoaAdapter extends RecyclerView.Adapter<SearchPessoaAdapter.ViewHolder> {
    private List<ResultPessoaPop> listaFilmes;

    public SearchPessoaAdapter(List<ResultPessoaPop> listaFilmes) {
        this.listaFilmes = listaFilmes;
    }

    @NonNull
    @Override
    public SearchPessoaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.busca_lista, parent, false);
        return new SearchPessoaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchPessoaAdapter.ViewHolder holder, int position) {
        ResultPessoaPop resultPessoa = listaFilmes.get(position);
        holder.onBind(resultPessoa);

    }

    public void atualizaLista(List<ResultPessoaPop> listaNova) {
        if (listaFilmes.isEmpty()) {
            this.listaFilmes = listaNova;
        } else {
            this.listaFilmes.addAll(listaNova);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (listaFilmes.size() >= 4) {
            return 4;
        } else return listaFilmes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView, textKnow;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textKnow = itemView.findViewById(R.id.estreia_busca_textView);
            imageView = itemView.findViewById(R.id.searchImagem);
            textView = itemView.findViewById(R.id.searchTitulo);
        }

        private void onBind(ResultPessoaPop resultPessoa) {
            textKnow.setText(resultPessoa.getKnownForDepartment());
            textView.setText(resultPessoa.getName());

            Picasso.get().load(URL_IMAGEM + resultPessoa.getProfilePath()).into(imageView);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), PessoaDetalheActivity.class);
            intent.putExtra(ID, listaFilmes.get(getAdapterPosition()).getId());
            v.getContext().startActivity(intent);
        }
    }
}
