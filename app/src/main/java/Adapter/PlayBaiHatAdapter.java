package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appzingmp3.R;
import com.example.appzingmp3.databinding.DongPlayBaiHatBinding;

import java.util.ArrayList;

import Model.BaiHat;

public class PlayBaiHatAdapter extends RecyclerView.Adapter<PlayBaiHatAdapter.ViewHolder>{
    DongPlayBaiHatBinding binding;
    Context context;
    ArrayList<BaiHat> baiHatArrayList;

    public PlayBaiHatAdapter(Context context, ArrayList<BaiHat> baiHatArrayList) {
        this.context = context;
        this.baiHatArrayList = baiHatArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = DataBindingUtil.inflate(inflater, R.layout.dong_play_bai_hat,parent,false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(PlayBaiHatAdapter.ViewHolder holder, int position) {
        BaiHat baiHat = baiHatArrayList.get(position);
        binding.txtIndexPlaynhac.setText(position + 1 + "");
        binding.txtTenbaihatPlaynhac.setText(baiHat.getTenBaiHat());
        binding.txtTencasiPlaynhac.setText(baiHat.getCasi());
    }

    @Override
    public int getItemCount() {
        return baiHatArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
