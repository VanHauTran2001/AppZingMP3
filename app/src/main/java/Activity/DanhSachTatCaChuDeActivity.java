package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.appzingmp3.R;
import com.example.appzingmp3.databinding.ActivityDanhSachTatCaChuDeBinding;

import java.util.ArrayList;
import java.util.List;

import Adapter.DanhSachAllChuDeAdapter;
import Model.ChuDe;
import Service.APIService;
import Service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachTatCaChuDeActivity extends AppCompatActivity {
    ActivityDanhSachTatCaChuDeBinding binding;
    ArrayList<ChuDe> chuDeArrayList = new ArrayList<>();
    DanhSachAllChuDeAdapter allChuDeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_danh_sach_tat_ca_chu_de);
        getToolbar();
        getData();
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<ChuDe>> callback = dataService.getDanhsachtatcaChuDe();
        callback.enqueue(new Callback<List<ChuDe>>() {
            @Override
            public void onResponse(Call<List<ChuDe>> call, Response<List<ChuDe>> response) {
                chuDeArrayList = (ArrayList<ChuDe>) response.body();
                allChuDeAdapter = new DanhSachAllChuDeAdapter(DanhSachTatCaChuDeActivity.this,chuDeArrayList);
                binding.recylerTatcaChuDe.setLayoutManager(new LinearLayoutManager(DanhSachTatCaChuDeActivity.this));
                binding.recylerTatcaChuDe.setAdapter(allChuDeAdapter);
            }

            @Override
            public void onFailure(Call<List<ChuDe>> call, Throwable t) {

            }
        });
    }

    private void getToolbar() {
        setSupportActionBar(binding.toolbarTatCaChuDe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Tất Cả Chủ Đề");
        binding.toolbarTatCaChuDe.setTitleTextColor(getResources().getColor(R.color.purple_500));
        binding.toolbarTatCaChuDe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}