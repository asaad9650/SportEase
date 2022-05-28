package com.fyp.sporteaze.User;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.sporteaze.Academy.Ground.AcademyGroundUpdate;
import com.fyp.sporteaze.Admin.UsersHelperAdatper;
import com.fyp.sporteaze.Model.User;
import com.fyp.sporteaze.R;

import java.util.List;

public class UserChatAdapter extends RecyclerView.Adapter{

    List<User> userList;
    String user_id;
    public UserChatAdapter(List<User> userList , String user_id){
        this.userList = userList;
        this.user_id = user_id;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_users_layout,parent,false);

        ViewHolderClass viewHolderClass = new ViewHolderClass(view);

        return viewHolderClass;
//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            ViewHolderClass viewHolderClass = (ViewHolderClass)holder;
            User users= userList.get(position);
            viewHolderClass.user_id.setText("email: " + users.email);
            viewHolderClass.user_name.setText("name: "+users.name);

            viewHolderClass.linear_chat_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(view.getContext(), "Chat with " +users.email +  " " + user_id , Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent( view.getContext(), UserChatWith.class);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("user_chat_with_id",users.user_id);
                    intent.putExtra("user_profile_pic" , users.image);
                    intent.putExtra("user_city_name" , users.address);
                    if(users.name.matches("")){
                        intent.putExtra("user_chat_with" , users.email);
                    }
                    else {
                        intent.putExtra("user_chat_with" , users.name);

                    }
                    view.getContext().startActivity(intent);

                }
            });
    }

    @Override
    public int getItemCount() {
        return userList.size();

    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView user_name, user_id;
        LinearLayout linear_chat_layout;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            user_id = itemView.findViewById(R.id.layout_user_id);
            user_name = itemView.findViewById(R.id.layout_user_name);
            linear_chat_layout = itemView.findViewById(R.id.linear_chat_layout);
        }
    }
}
