package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appzingmp3.R;

import java.util.ArrayList;

import Activity.PlayNhacActivity;
import Model.BaiHat;
import Service.APIService;
import Service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaiHatYeuThichApdater extends RecyclerView.Adapter<BaiHatYeuThichApdater.BaiHatViewHolder>{
    Context context;
    ArrayList<BaiHat> baiHatArrayList;
    int checklove = 1;

    public BaiHatYeuThichApdater(Context context, ArrayList<BaiHat> baiHatArrayList) {
        this.context = context;
        this.baiHatArrayList = baiHatArrayList;
    }

    @Override
    public BaiHatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dong_bai_hat_yeu_thich,parent,false);
        return new BaiHatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaiHatYeuThichApdater.BaiHatViewHolder holder, int position) {
        BaiHat baiHat = baiHatArrayList.get(position);
        holder.txtTenCaSiBaiHat.setText(baiHat.getCasi());
        holder.txtTenBaiHat.setText(baiHat.getTenBaiHat());
        Glide.with(context).load(baiHat.getHinhBaiHat()).into(holder.imgHinhBaiHat);
//        Glide.with(context).load(baiHat.getLuotThich()).into(holder.imgYeuThich);
    }

    @Override
    public int getItemCount() {
        return baiHatArrayList.size();
    }

    public class BaiHatViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenBaiHat,txtTenCaSiBaiHat;
        ImageView imgHinhBaiHat,imgYeuThich;
        public BaiHatViewHolder(View itemView) {
            super(itemView);
            txtTenBaiHat = itemView.findViewById(R.id.txtTenBaiHat);
            txtTenCaSiBaiHat = itemView.findViewById(R.id.txtTemCaSiBaiHat);
            imgHinhBaiHat = itemView.findViewById(R.id.imgBaiHatYeuThich);
            imgYeuThich = itemView.findViewById(R.id.imgYeuThich);
            imgYeuThich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checklove==1){
                        imgYeuThich.setImageResource(R.drawable.iconloved);
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
                        checklove = 0;
                    }else {
                        imgYeuThich.setImageResource(R.drawable.iconlove);
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayNhacActivity.class);
                    intent.putExtra("playnhac",baiHatArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
