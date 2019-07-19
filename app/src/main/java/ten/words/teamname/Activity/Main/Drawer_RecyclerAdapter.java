package ten.words.teamname.Activity.Main;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ten.words.teamname.R;

public class Drawer_RecyclerAdapter extends RecyclerView.Adapter<Drawer_RecyclerAdapter.ViewHolder> {
    ArrayList<String> items;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.drawer_item_tv);
        }
    }

    public Drawer_RecyclerAdapter(ArrayList<String> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public Drawer_RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_listitem, viewGroup, false);
        Drawer_RecyclerAdapter.ViewHolder vh = new Drawer_RecyclerAdapter.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final @NonNull Drawer_RecyclerAdapter.ViewHolder vh, int i) {
        vh.tv.setText(items.get(i));

        vh.tv.setOnClickListener(view -> {
            ((MainActivity)vh.itemView.getContext()).Listload(items.get(i));
            ((MainActivity)vh.itemView.getContext()).DL.closeDrawers();
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
