package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.appzingmp3.R;
import com.example.appzingmp3.databinding.ActivityDanhSachTatCaAlbumBinding;

import java.util.ArrayList;
import java.util.List;

import Adapter.DanhSachAllAlbumAdapter;
import Model.AlbumHot;
import Service.APIService;
import Service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachTatCaAlbumActivity extends AppCompatActivity {
    ActivityDanhSachTatCaAlbumBinding binding;
    ArrayList<AlbumHot> albumHotArrayList=new ArrayList<>();
    DanhSachAllAlbumAdapter allAlbumAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_danh_sach_tat_ca_album);
        toolbar();
        getData();
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<AlbumHot>> callback = dataService.getDanhsachTatcaAlbum();
        callback.enqueue(new Callback<List<AlbumHot>>() {
            @Override
            public void onResponse(Call<List<AlbumHot>> call, Response<List<AlbumHot>> response) {
                albumHotArrayList = (ArrayList<AlbumHot>) response.body();
                allAlbumAdapter = new DanhSachAllAlbumAdapter(DanhSachTatCaAlbumActivity.this,albumHotArrayList);
                binding.recylerTatcaAlbum.setLayoutManager(new GridLayoutManager(DanhSachTatCaAlbumActivity.this,2));
                binding.recylerTatcaAlbum.setAdapter(allAlbumAdapter);
            }

            @Override
            public void onFailure(Call<List<AlbumHot>> call, Throwable t) {

            }
        });
    }

    private void toolbar() {
        setSupportActionBar(binding.toolbarTatCaAlbum);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Tất Cả Album");
        binding.toolbarTatCaAlbum.setTitleTextColor(getResources().getColor(R.color.purple_500));
        binding.toolbarTatCaAlbum.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}