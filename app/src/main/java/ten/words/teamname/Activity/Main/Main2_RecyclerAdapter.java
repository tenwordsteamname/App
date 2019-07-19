package ten.words.teamname.Activity.Main;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ten.words.teamname.Activity.Detail.DetailActivity;
import ten.words.teamname.Data.Main_Data;
import ten.words.teamname.R;

public class Main2_RecyclerAdapter extends RecyclerView.Adapter<Main2_RecyclerAdapter.ViewHolder>{
    ArrayList<Main_Data> items;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;
        TextView agree;
        TextView disagree;
        TextView location;
        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.main2_item_title);
            date = itemView.findViewById(R.id.main2_item_date);
            agree = itemView.findViewById(R.id.main2_item_tv_agree);
            disagree= itemView.findViewById(R.id.main2_item_tv_disagree);
            location = itemView.findViewById(R.id.main2_item_loc);
        }
    }

    public Main2_RecyclerAdapter(ArrayList<Main_Data> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public Main2_RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main2_listitem, viewGroup, false);
        Main2_RecyclerAdapter.ViewHolder vh = new Main2_RecyclerAdapter.ViewHolder(v);

        return vh;
    }
    @Override
    public void onBindViewHolder(final @NonNull Main2_RecyclerAdapter.ViewHolder vh, int i) {
        Main_Data tmp = items.get(i);

        vh.title.setText(tmp.getTitle());
        vh.date.setText(tmp.getDate());
        vh.location.setText(tmp.getLocation());
        vh.agree.setText(""+tmp.getAgree());
        vh.disagree.setText(""+tmp.getDisagree());

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(vh.itemView.getContext(), DetailActivity.class);
                intent.putExtra("title",tmp.getTitle());
                intent.putExtra("date",tmp.getDate());
                intent.putExtra("location",tmp.getLocation());
                intent.putExtra("img",tmp.getImg());
                intent.putExtra("agree",tmp.getAgree());
                intent.putExtra("disagree",tmp.getDisagree());
                intent.putExtra("content",tmp.getContent());
                vh.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
