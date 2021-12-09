package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appzingmp3.R;

import java.util.ArrayList;

import Activity.DanhSachBaihatActivity;
import Model.AlbumHot;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AblumViewHolder>{
    Context context;
    ArrayList<AlbumHot> albumHotArrayList;

    public AlbumAdapter(Context context, ArrayList<AlbumHot> albumHotArrayList) {
        this.context = context;
        this.albumHotArrayList = albumHotArrayList;
    }

    @Override
    public AblumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_album,parent,false);
        return new AblumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumAdapter.AblumViewHolder holder, int position) {
        AlbumHot albumHot = albumHotArrayList.get(position);
        holder.txtTenCasiAlbum.setText(albumHot.getTenCaSiAlbum());
        holder.txtTenAlbum.setText(albumHot.getTenAlbum());
        Glide.with(context).load(albumHot.getHinhAlbum()).into(holder.imgAlbum);
    }

    @Override
    public int getItemCount() {
        return albumHotArrayList.size();
    }

    public class AblumViewHolder extends RecyclerView.ViewHolder{
        ImageView imgAlbum;
        TextView txtTenAlbum , txtTenCasiAlbum;
        public AblumViewHolder(View itemView) {
            super(itemView);
            imgAlbum = itemView.findViewById(R.id.imgAblum);
            txtTenAlbum = itemView.findViewById(R.id.txtTenAlbum);
            txtTenCasiAlbum = itemView.findViewById(R.id.txtTenCasiAlbum);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhSachBaihatActivity.class);
                    intent.putExtra("album",albumHotArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
