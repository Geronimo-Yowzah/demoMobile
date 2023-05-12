package com.example.okhttp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.postsViewHolder> {

    List<Post> postsList;
    Context context;
    PostAdapterListener listener;

    public interface PostAdapterListener{
        void onClicked(Post data);
    }

    public PostAdapter(Context context, List<Post> post, PostAdapterListener listener){
        this.context = context;
        this.listener = listener;
        postsList = post;
    }

    @NonNull
    @Override
    public postsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_item,parent,false);
        return new postsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull postsViewHolder holder, int position) {
        Post post = postsList.get(position);
        holder.id.setText("Id : "+ post.getId());
        holder.body.setText("Body : "+ post.getBody());
        holder.userid.setText("UserId : "+ post.getUserId());
        holder.title.setText("Title : "+ post.getTitle());

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClicked(post);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class postsViewHolder extends RecyclerView.ViewHolder{
        TextView userid,id,body,title;
        LinearLayout item;
        public postsViewHolder(@NonNull View itemView) {
            super(itemView);

            userid = itemView.findViewById(R.id.text_userid);
            id = itemView.findViewById(R.id.text_id);
            title = itemView.findViewById(R.id.text_title);
            body = itemView.findViewById(R.id.text_description);
            item = itemView.findViewById(R.id.item);
        }
    }
}
