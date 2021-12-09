package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.appzingmp3.R;
import com.example.appzingmp3.databinding.ActivityDanhSachBaihatBinding;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import Adapter.DanhSachBaiHatAdapter;
import Model.AlbumHot;
import Model.BaiHat;
import Model.ChuDe;
import Model.PlayList;
import Model.Quangcao;
import Model.TheLoai;
import Service.APIService;
import Service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

public class DanhSachBaihatActivity extends AppCompatActivity {
    ActivityDanhSachBaihatBinding binding;
    Quangcao quangcao;
    PlayList playList;
    TheLoai theLoai;
    AlbumHot album;
    ArrayList<BaiHat> baiHatArrayList = new ArrayList<>();
    DanhSachBaiHatAdapter danhSachBaiHatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_danh_sach_baihat);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        khoitao();
        DataIntent();
        if (quangcao !=null &&!quangcao.getTenBaiHat().equals("")){
            setValueInview(quangcao.getTenBaiHat(),quangcao.getHinhBaiHat());
            getDataQuangCao(quangcao.getIdQuangCao());
        }
        if (playList !=null && !playList.getTen().equals("")){
            setValueInview(playList.getTen(),playList.getHinhicon());
            getDataPlaylist(playList.getIdPlayList());
        }
        if (theLoai!=null && !theLoai.getTenTheLoai().equals("")){
            setValueInview(theLoai.getTenTheLoai(),theLoai.getHinhTheLoai());
            getDataTheLoai(theLoai.getIdTheLoai());
        }
        if (album!=null && !album.getTenAlbum().equals("")){
            setValueInview(album.getTenAlbum(),album.getHinhAlbum());
            getDataAlbum(album.getIdAlbum());
        }
    }

    private void getDataAlbum(String idAlbum) {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.getDanhsachbaihatAlbum(idAlbum);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                baiHatArrayList = (ArrayList<BaiHat>) response.body();
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhSachBaihatActivity.this,baiHatArrayList);
                binding.recylerDanhSachBaiHat.setLayoutManager(new LinearLayoutManager(DanhSachBaihatActivity.this));
                binding.recylerDanhSachBaiHat.setAdapter(danhSachBaiHatAdapter);
                //su kien play tat ca bai hat
                actionButtonClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void getDataTheLoai(String idtheloai) {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.getDanhsachbaihatTheLoai(idtheloai);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                baiHatArrayList = (ArrayList<BaiHat>) response.body();
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhSachBaihatActivity.this,baiHatArrayList);
                binding.recylerDanhSachBaiHat.setLayoutManager(new LinearLayoutManager(DanhSachBaihatActivity.this));
                binding.recylerDanhSachBaiHat.setAdapter(danhSachBaiHatAdapter);
                actionButtonClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void getDataPlaylist(String idplaylist) {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.getDanhsachbaihatPlaylist(idplaylist);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                baiHatArrayList = (ArrayList<BaiHat>) response.body();
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhSachBaihatActivity.this,baiHatArrayList);
                binding.recylerDanhSachBaiHat.setLayoutManager(new LinearLayoutManager(DanhSachBaihatActivity.this));
                binding.recylerDanhSachBaiHat.setAdapter(danhSachBaiHatAdapter);
                actionButtonClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void setValueInview(String ten , String hinh){

        binding.collapsingToolbar.setTitle(ten);
//                try {
//                    //đường dẫn để gắn dữ liệu lên layout của collapsingtoolbar
//                    URL url = new URL(hinh);
//                    //đường dẫn dạng URL phải convert về dạng bitmap mới gắn dữ liệu lên
//                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                    connection.setDoInput(true);
//                    connection.connect();
//                    InputStream inputStream = connection.getInputStream();
//                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                    BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(),bitmap);
//                    binding.collapsingToolbar.setBackground(bitmapDrawable);
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
        Glide.with(this).load(hinh).into(binding.imgDanhsachBaiHat);
    }
    private void getDataQuangCao(String idquangcao) {
        //Khoi tao lai retrofit doc du lieu
        //khoi tao retrofit , khoi tao interface de ket tu server qua client
        DataService dataService = APIService.getService();
        //nhan du lieu va tuong tac voi server
        Call<List<BaiHat>> callback = dataService.getDanhsachbaihatQuangCao(idquangcao);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                baiHatArrayList = (ArrayList<BaiHat>) response.body();
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhSachBaihatActivity.this,baiHatArrayList);
                binding.recylerDanhSachBaiHat.setLayoutManager(new LinearLayoutManager(DanhSachBaihatActivity.this));
                binding.recylerDanhSachBaiHat.setAdapter(danhSachBaiHatAdapter);
                actionButtonClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void khoitao() {
        setSupportActionBar(binding.toolbarDanhsach);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolbarDanhsach.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        binding.collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        binding.floatActionButton.setEnabled(false);
    }

    private void DataIntent() {
        Intent intent = getIntent();
        if (intent!=null){
            if (intent.hasExtra("banner")){
                quangcao = (Quangcao) intent.getSerializableExtra("banner");

            }
        }
        if (intent.hasExtra("playlist")){
            playList = (PlayList) intent.getSerializableExtra("playlist");
        }
        if (intent.hasExtra("idtheloai")){
            theLoai = (TheLoai) intent.getSerializableExtra("idtheloai");
        }
        if (intent.hasExtra("album")){
            album = (AlbumHot) intent.getSerializableExtra("album");
        }
    }
    private void actionButtonClick(){
        binding.floatActionButton.setEnabled(true);
        binding.floatActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachBaihatActivity.this,PlayNhacActivity.class);
                intent.putExtra("mangbaihat",baiHatArrayList);
                startActivity(intent);
            }
        });
    }
}