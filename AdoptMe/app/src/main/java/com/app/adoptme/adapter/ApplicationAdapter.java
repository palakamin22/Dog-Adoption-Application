package com.app.adoptme.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.adoptme.databinding.ItemApplicationBinding;
import com.app.adoptme.firebase.storage.UploadDogImage;
import com.app.adoptme.model.Application;
import com.app.adoptme.screens.ApplicationDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.app.adoptme.utils.Constant.APPLICATION_DATA;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ViewHolder> {

    Activity activity;
    private List<Application> applicationList;

    public ApplicationAdapter(Activity activity, List<Application> applicationList) {
        this.activity = activity;
        this.applicationList = applicationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemApplicationBinding binding = ItemApplicationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Application application = applicationList.get(position);


        //Load image icon
        UploadDogImage.getDogImage(application.getPostImage(), uri -> {
            String imageUrl = uri.toString();
            Picasso.get().load(imageUrl).into(holder.imgDog);
        });
        holder.txtName.setText("Dog Name : " + application.getPostName());
        holder.txtUserName.setText("User Name : " + application.getUserName());

    }

    @Override
    public int getItemCount() {
        if (applicationList != null)
            return applicationList.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgDog;
        private TextView txtName;
        private TextView txtUserName;

        ViewHolder(ItemApplicationBinding binding) {
            super(binding.getRoot());

            imgDog = binding.imgDog;
            txtName = binding.txtName;
            txtUserName = binding.txtUserName;


            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, ApplicationDetailActivity.class);
                    intent.putExtra(APPLICATION_DATA, applicationList.get(getAdapterPosition()));
                    activity.startActivity(intent);
                }
            });

        }
    }
}
