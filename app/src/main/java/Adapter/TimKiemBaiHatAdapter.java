package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appzingmp3.R;
import com.example.appzingmp3.databinding.DongTimKiemBaiHatBinding;

import java.util.ArrayList;

import Activity.PlayNhacActivity;
import Model.BaiHat;
import Service.APIService;
import Service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimKiemBaiHatAdapter extends RecyclerView.Adapter<TimKiemBaiHatAdapter.TimKiemViewholer>{
    Context context;
    ArrayList<BaiHat> baiHatArrayList;
    DongTimKiemBaiHatBinding binding;
    int checklove = 1;

    public TimKiemBaiHatAdapter(Context context, ArrayList<BaiHat> baiHatArrayList) {
        this.context = context;
        this.baiHatArrayList = baiHatArrayList;
    }

    @Override
    public TimKiemViewholer onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = DataBindingUtil.inflate(inflater, R.layout.dong_tim_kiem_bai_hat,parent,false);
        return new TimKiemViewholer(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(TimKiemBaiHatAdapter.TimKiemViewholer holder, int position) {
        BaiHat baiHat = baiHatArrayList.get(position);
        Glide.with(context).load(baiHat.getHinhBaiHat()).into(binding.imgSearch);
        binding.txtSearchTenBaihat.setText(baiHat.getTenBaiHat());
        binding.txtSearchTencasi.setText(baiHat.getCasi());
    }

    @Override
    public int getItemCount() {
        return baiHatArrayList.size();
    }

    public class TimKiemViewholer extends RecyclerView.ViewHolder{

        public TimKiemViewholer(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayNhacActivity.class);
                    intent.putExtra("playnhac",baiHatArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
            binding.imgSearchYeuThich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checklove==1){
                        binding.imgSearchYeuThich.setImageResource(R.drawable.iconloved);
                        DataService dataService = APIService.getService();
                        Call<String> callback = dataService.UpdateLuotThich("1",baiHatArrayList.get(getPosition()).getIdBaiHat());
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String ketqua = response.body();
                                if (ketqua.equals("success")){
                                    Toast.makeText(context, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                        checklove=0;
                    }else {
                        binding.imgSearchYeuThich.setImageResource(R.drawable.iconlove);
                        DataService dataService = APIService.getService();
                        Call<String> call = dataService.UpdateBoThich("1",baiHatArrayList.get(getPosition()).getIdBaiHat());
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String kq = response.body();
                                if (kq.equals("success")){
                                    Toast.makeText(context, "Đã xóa khỏi danh sách yêu thich", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                        checklove = 1;
                    }
                }
            });
        }
    }
}
