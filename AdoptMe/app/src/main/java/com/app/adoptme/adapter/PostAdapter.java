package com.app.adoptme.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.adoptme.databinding.ItemPostBinding;
import com.app.adoptme.firebase.storage.UploadDogImage;
import com.app.adoptme.model.Post;
import com.app.adoptme.screens.PostDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.app.adoptme.utils.Constant.POST_DATA;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    Activity activity;
    private List<Post> postList;

    public PostAdapter(Activity activity,  List<Post> postList) {
        this.activity = activity;
        this.postList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = postList.get(position);
        //holder.txtUserName.setText(post.getUserName());
        /*Picasso.get().load("https://i.pinimg.com/736x/89/90/48/899048ab0cc455154006fdb9676964b3.jpg")
                .into(holder.imgUser);*/

        //Load image icon
        UploadDogImage.getDogImage(post.getImage(), uri -> {
            String imageUrl = uri.toString();
            Picasso.get().load(imageUrl).into(holder.imgDog);
        });
    }

    @Override
    public int getItemCount() {
        if (postList != null)
            return postList.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
       /* private CircleImageView imgUser;
        private TextView txtUserName;*/
        private ImageView imgDog;
        /*private TextView txtApply;
        private TextView txtKnowMore;*/

        ViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            //imgUser = binding.imgUser;
            //txtUserName = binding.txtUserName;
            imgDog = binding.imgDog;
            //txtApply = binding.txtApply;
            //txtKnowMore = binding.txtKnowMore;

            binding.imgDog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, PostDetailsActivity.class);
                    intent.putExtra(POST_DATA, postList.get(getAdapterPosition()));
                    activity.startActivity(intent);
                }
            });
            /*binding.txtKnowMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, PostDetailsActivity.class);
                    intent.putExtra(POST_DATA, postList.get(getAdapterPosition()));
                    activity.startActivity(intent);
                }
            });*/
        }
    }
}
