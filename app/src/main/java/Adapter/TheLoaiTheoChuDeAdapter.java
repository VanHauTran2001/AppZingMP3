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
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appzingmp3.R;


import java.util.ArrayList;

import Activity.DanhSachBaihatActivity;
import Model.TheLoai;

public class TheLoaiTheoChuDeAdapter extends RecyclerView.Adapter<TheLoaiTheoChuDeAdapter.ViewHolder>{
    Context context;
    ArrayList<TheLoai> theLoaiArrayList;

    public TheLoaiTheoChuDeAdapter(Context context, ArrayList<TheLoai> theLoaiArrayList) {
        this.context = context;
        this.theLoaiArrayList = theLoaiArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dong_theloai_theo_chude,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TheLoaiTheoChuDeAdapter.ViewHolder holder, int position) {
        TheLoai theLoai = theLoaiArrayList.get(position);
        Glide.with(context).load(theLoai.getHinhTheLoai()).into(holder.imgTheloaitheochude);
        holder.txtTentheloaitheochude.setText(theLoai.getTenTheLoai());
    }

    @Override
    public int getItemCount() {
        return theLoaiArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgTheloaitheochude;
        TextView txtTentheloaitheochude;
        public ViewHolder(View itemView) {
            super(itemView);
            imgTheloaitheochude = itemView.findViewById(R.id.imgTheloaitheoChude);
            txtTentheloaitheochude = itemView.findViewById(R.id.txtTentheloaitheochude);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhSachBaihatActivity.class);
                    intent.putExtra("idtheloai",theLoaiArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
