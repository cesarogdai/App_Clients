package cesar.ogdai.clientes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cesar.ogdai.clientes.R;
import cesar.ogdai.clientes.entidades.User;


public class ListClientAdapter extends RecyclerView.Adapter<ListClientAdapter.PersonasViewHolder> {

    ArrayList<User> listaUsuario;

    public ListClientAdapter(ArrayList<User> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    @Override
    public PersonasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_client_details,null,false);
        return new PersonasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonasViewHolder holder, int position) {

            holder.id.setText(listaUsuario.get(position).getId());
            holder.name.setText(listaUsuario.get(position).getName().toString());
            holder.phone.setText(listaUsuario.get(position).getPhone().toString());
            holder.model.setText(listaUsuario.get(position).getModel().toString());
            holder.action.setText(listaUsuario.get(position).getAction().toString());
    }

    @Override
    public int getItemCount() {
        return listaUsuario.size();
    }

    public class PersonasViewHolder extends RecyclerView.ViewHolder {

        TextView name, phone, model,id, action;

        public PersonasViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.idText);
            name = (TextView) itemView.findViewById(R.id.user_name);
            phone = (TextView) itemView.findViewById(R.id.user_phone);
            model = (TextView) itemView.findViewById(R.id.user_model);
            action = (TextView) itemView.findViewById(R.id.user_action);
        }
    }
}
