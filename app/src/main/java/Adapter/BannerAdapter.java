package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.appzingmp3.R;

import java.util.ArrayList;

import Activity.DanhSachBaihatActivity;
import Model.Quangcao;

public class BannerAdapter extends PagerAdapter {
    Context context;
    ArrayList<Quangcao> arrayListBanner;

    public BannerAdapter(Context context, ArrayList<Quangcao> arrayListBanner) {
        this.context = context;
        this.arrayListBanner = arrayListBanner;
    }

    @Override
    public int getCount() {
        return arrayListBanner.size();
    }

    @Override
    public boolean isViewFromObject(View view,Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_banner,null);

        ImageView imgBanner = view.findViewById(R.id.imgBanner);
        ImageView imgBackgroupBanner = view.findViewById(R.id.imgBackgroupBanner);
        TextView txtTiltleBanner = view.findViewById(R.id.txtTiltleBanner);
        TextView txtNoiDung = view.findViewById(R.id.txtNoiDung);

        Glide.with(context).load(arrayListBanner.get(position).getHinhanh()).into(imgBackgroupBanner);
        Glide.with(context).load(arrayListBanner.get(position).getHinhBaiHat()).into(imgBanner);
        txtTiltleBanner.setText(arrayListBanner.get(position).getTenBaiHat());
        txtNoiDung.setText(arrayListBanner.get(position).getNoidung());
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DanhSachBaihatActivity.class);
                intent.putExtra("banner",arrayListBanner.get(position));
                context.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position,Object object) {
       container.removeView((View) object);
    }
}
