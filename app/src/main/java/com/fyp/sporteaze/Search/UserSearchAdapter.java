package com.fyp.sporteaze.Search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fyp.sporteaze.Event.UserShowPostAdapter;
import com.fyp.sporteaze.Model.Team;
import com.fyp.sporteaze.Model.User;
import com.fyp.sporteaze.R;

import java.util.List;

public class UserSearchAdapter extends RecyclerView.Adapter {

    String name, address;
    List<User> userList;
    List<Team> teamList;

    public UserSearchAdapter(String name, String address, List<User> userList, List<Team> teamList) {
        this.name = name;
        this.address = address;
        this.userList = userList;
        this.teamList = teamList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_layout_file, parent, false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass) holder;

        if (teamList == null && userList != null) {
            User user = userList.get(position);
            if (user.getName().matches("")) {
                viewHolderClass.search_name_or_email.setText("Name: " + user.getEmail());


            } else {
                viewHolderClass.search_name_or_email.setText("Name: " + user.getName());

            }
//        Toast.makeText()
            viewHolderClass.search_address.setText("Address: " + user.getAddress());
            if (!user.getImage().matches("")) {
                Glide.with(viewHolderClass.itemView.getContext()).load(user.getImage()).into(viewHolderClass.search_user_image);

            }

        }
        else{
            Team team = teamList.get(position);
            viewHolderClass.search_name_or_email.setText("Team Name: " + team.getTeam_name());
            viewHolderClass.search_address.setText("Captain: " + team.getCreated_by());
        }
    }

    @Override
    public int getItemCount() {
        if(userList==null && teamList!=null){
            return teamList.size();
        }
        return userList.size();
    }
    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView search_name_or_email;
        TextView search_address;
        ImageView search_user_image;


        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);


            search_name_or_email = itemView.findViewById(R.id.search_name_or_email);
            search_address = itemView.findViewById(R.id.search_address);
            search_user_image = itemView.findViewById(R.id.search_user_image);

        }
    }

}
