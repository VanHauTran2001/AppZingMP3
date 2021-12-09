package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.appzingmp3.R;
import com.example.appzingmp3.databinding.ActivityDanhSachTheLoaiTheoChuDeBinding;

import java.util.ArrayList;
import java.util.List;

import Adapter.TheLoaiTheoChuDeAdapter;
import Model.ChuDe;
import Model.TheLoai;
import Service.APIService;
import Service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachTheLoaiTheoChuDeActivity extends AppCompatActivity {
    ActivityDanhSachTheLoaiTheoChuDeBinding binding;
    ChuDe chuDe;
    ArrayList<TheLoai> theLoaiArrayList = new ArrayList<>();
    TheLoaiTheoChuDeAdapter theLoaiTheoChuDeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_danh_sach_the_loai_theo_chu_de);
        getIntentData();
        getDataServer();
        getToolbar();
    }

    private void getDataServer() {
        DataService dataService = APIService.getService();
        Call<List<TheLoai>> callback = dataService.getTheLoaitheoChuDe(chuDe.getIdChuDe());
        callback.enqueue(new Callback<List<TheLoai>>() {
            @Override
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response) {
                theLoaiArrayList = (ArrayList<TheLoai>) response.body();
                theLoaiTheoChuDeAdapter = new TheLoaiTheoChuDeAdapter(DanhSachTheLoaiTheoChuDeActivity.this,theLoaiArrayList);
                binding.recylerTheloaiTheoChude.setLayoutManager(new GridLayoutManager(DanhSachTheLoaiTheoChuDeActivity.this,2));
                binding.recylerTheloaiTheoChude.setAdapter(theLoaiTheoChuDeAdapter);

            }

            @Override
            public void onFailure(Call<List<TheLoai>> call, Throwable t) {

            }
        });
    }

    private void getToolbar() {
        setSupportActionBar(binding.toolbarTheLoaiTheoChuDe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(chuDe.getTenChuDe());
        binding.toolbarTheLoaiTheoChuDe.setTitleTextColor(getResources().getColor(R.color.purple_500));
        binding.toolbarTheLoaiTheoChuDe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra("chude")){
            chuDe = (ChuDe) intent.getSerializableExtra("chude");
        }
    }
}