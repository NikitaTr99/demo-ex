package com.ntsoftware.vspc.demoex.ui.home;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ntsoftware.vspc.demoex.PeopleDetailActivity;
import com.ntsoftware.vspc.demoex.R;
import com.ntsoftware.vspc.demoex.data.PeopleItem;

import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.HomeHolder> {

    Activity parent_activity;
    List<PeopleItem> people_items;

    View.OnClickListener item_onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            PeopleItem pi = (PeopleItem) view.getTag();
            view.getContext().startActivity(PeopleDetailActivity.newIntent(view.getContext(), pi.get_id()));
        }
    };

    public PeopleAdapter(List<PeopleItem> people_items) {
        this.people_items = people_items;
    }

    public PeopleAdapter(Activity parent_activity, List<PeopleItem> people_items) {
        this.parent_activity = parent_activity;
        this.people_items = people_items;
    }

    @NonNull
    @Override
    public HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rw_content_holder, parent, false);
        return new HomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHolder holder, int position) {
        PeopleItem peopleItem = people_items.get(position);
        holder.bind(peopleItem.getFirst_name(),
                peopleItem.getLast_name(),
                peopleItem.getBirthday(),
                null);
        holder.itemView.setOnClickListener(item_onclick);
        holder.itemView.setTag(people_items.get(position));
    }

    @Override
    public int getItemCount() {
        return people_items.size();
    }

    public static final class HomeHolder extends RecyclerView.ViewHolder {

        private final View root;
        private final TextView first_name;
        private final TextView last_name;
        private final TextView birthday;
        private ImageView image;

        public HomeHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView;
            first_name = itemView.findViewById(R.id.tv_f_name);
            last_name = itemView.findViewById(R.id.tv_l_name);
            birthday = itemView.findViewById(R.id.tv_birthday);
            image = itemView.findViewById(R.id.iv_image);
        }

        public void bind(String f_n, String l_n, String birth, byte[] im) {
            first_name.setText(f_n);
            last_name.setText(l_n);
            birthday.setText(birth);
        }
    }
}
