package com.example.bewell.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bewell.R;
import com.example.bewell.models.Blog;

import java.util.ArrayList;

public class BlogRecViewAdapter extends RecyclerView.Adapter<BlogRecViewAdapter.ViewHolder>{

    private ArrayList<Blog> blogs = new ArrayList<>();
    private BlogRecViewAdapter.OnBlogListener onBlogListener;



    public BlogRecViewAdapter( BlogRecViewAdapter.OnBlogListener onBlogListener) {
        this.onBlogListener =  onBlogListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_individual_ambassador, parent, false);
        BlogRecViewAdapter.ViewHolder holder = new BlogRecViewAdapter.ViewHolder(view, onBlogListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Blog blogAtRow =  blogs.get(position);
        holder.blogTitle.setText(blogAtRow.getTitle());
    }



    @Override
    public int getItemCount() {
        return blogs.size();
    }







    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        private TextView blogTitle;
        private ImageView  imageBlog;
        private OnBlogListener onBlogListener;

        public ViewHolder(@NonNull View itemView, OnBlogListener onBlogListener) {
            super(itemView);
            blogTitle  =  itemView.findViewById(R.id.blogPostTitle);
            this.onBlogListener =  onBlogListener;

            itemView.setOnClickListener(this);



        }


        @Override
        public void onClick(View v) {
            onBlogListener.onBlogClick(getAdapterPosition());
        }
    }

    public interface OnBlogListener {
        void onBlogClick(int position);
    }


}
