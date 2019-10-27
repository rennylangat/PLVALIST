package com.example.renny.plvlit;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.util.List;
/**
 * Created by Renny on 4/17/2017.
 */
class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {
    private Context mContext;
    private List<Album> albumList;
    class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView title,count;
        ImageView thumbnail,overflow;
        MyViewHolder(View view) {
            super(view);
            title=(TextView)view.findViewById(R.id.title);
            count=(TextView)view.findViewById(R.id.count);
            thumbnail=(ImageView)view.findViewById(R.id.thumbnail);
            overflow=(ImageView)view.findViewById(R.id.overflow);
        }
    }
    AlbumsAdapter(Context mContext, List<Album> albumList){
        this.mContext=mContext;
        this.albumList=albumList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.album_card,parent,false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Album album=albumList.get(position);
        holder.title.setText(album.getName());
        holder.count.setText(album.getNumOfSongs()+ " songs ");

        //loading album cover suing glide gallery
        Glide.with(mContext).load(album.getThumbnails()).into(holder.thumbnail);
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }
    private void showPopupMenu(View view) {
        //inflate the popup menu
        PopupMenu popupMenu=new PopupMenu(mContext,view);
        MenuInflater inflater=popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_album,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new MyMenuClickListener());
        popupMenu.show();
    }
    /*Click Listener for Popup menu items

     */
    private class MyMenuClickListener implements PopupMenu.OnMenuItemClickListener {
        MyMenuClickListener(){
        }
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.action_add_favorites:
                    Toast.makeText(mContext,"Added to Favorites",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext,"Play the Next Song",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_delete:
                    Toast.makeText(mContext,"Album Deleted",Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
