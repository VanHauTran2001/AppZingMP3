package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appzingmp3.R;
import com.example.appzingmp3.databinding.DongAllAlbumBinding;

import java.util.ArrayList;

import Activity.DanhSachBaihatActivity;
import Model.AlbumHot;


public class DanhSachAllAlbumAdapter extends RecyclerView.Adapter<DanhSachAllAlbumAdapter.ViewHolder>{
    Context context;
    ArrayList<AlbumHot> albumHotArrayList;
    DongAllAlbumBinding binding;

    public DanhSachAllAlbumAdapter(Context context, ArrayList<AlbumHot> albumHotArrayList) {
        this.context = context;
        this.albumHotArrayList = albumHotArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = DataBindingUtil.inflate(inflater, R.layout.dong_all_album,parent,false);
        return new ViewHolder(binding.getRoot());
    }
    @Override
    public void onBindViewHolder(DanhSachAllAlbumAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(albumHotArrayList.get(position).getHinhAlbum()).into(binding.imgAllAlbum);
        binding.txtTenAllAlbum.setText(albumHotArrayList.get(position).getTenAlbum());
        binding.txtTenCasiAllAlbum.setText(albumHotArrayList.get(position).getTenCaSiAlbum());
    }

    @Override
    public int getItemCount() {
        return albumHotArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,DanhSachBaihatActivity.class);
                    intent.putExtra("album",albumHotArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
