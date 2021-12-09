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
import Model.PlayList;

public class DanhSachPlaylistAdapter extends RecyclerView.Adapter<DanhSachPlaylistAdapter.ViewHolder>{
    Context context;
    ArrayList<PlayList> arrayList;

    public DanhSachPlaylistAdapter(Context context, ArrayList<PlayList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dong_danh_sach_play_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DanhSachPlaylistAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(arrayList.get(position).getHinhnen()).into(holder.imgDanhsachplaylist);
        holder.txtTendanhsachplaylist.setText(arrayList.get(position).getTen());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgDanhsachplaylist;
        TextView txtTendanhsachplaylist;
        public ViewHolder( View itemView) {
            super(itemView);
            imgDanhsachplaylist = itemView.findViewById(R.id.imgdanhsachplaylist);
            txtTendanhsachplaylist = itemView.findViewById(R.id.txtTendanhsachplaylist);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhSachBaihatActivity.class);
                    intent.putExtra("playlist",arrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
