package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.appzingmp3.R;
import com.example.appzingmp3.databinding.ActivityDanhSachPlaylistBinding;

import java.util.ArrayList;
import java.util.List;

import Adapter.DanhSachPlaylistAdapter;
import Model.PlayList;
import Service.APIService;
import Service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachPlaylistActivity extends AppCompatActivity {
    ActivityDanhSachPlaylistBinding binding;
    ArrayList<PlayList> playListArrayList = new ArrayList<>();
    DanhSachPlaylistAdapter danhSachPlaylistAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_danh_sach_playlist);
        toolbar();
        getData();
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<PlayList>> callback = dataService.getDanhSachPlaylist();
        callback.enqueue(new Callback<List<PlayList>>() {
            @Override
            public void onResponse(Call<List<PlayList>> call, Response<List<PlayList>> response) {
            playListArrayList = (ArrayList<PlayList>) response.body();
            danhSachPlaylistAdapter = new DanhSachPlaylistAdapter(DanhSachPlaylistActivity.this,playListArrayList);
            binding.recylerDanhsachPlaylist.setLayoutManager(new GridLayoutManager(DanhSachPlaylistActivity.this,2));
            binding.recylerDanhsachPlaylist.setAdapter(danhSachPlaylistAdapter);
            }

            @Override
            public void onFailure(Call<List<PlayList>> call, Throwable t) {

            }
        });
    }

    private void toolbar() {
        setSupportActionBar(binding.toolbarPlaylist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Play Lists");
        binding.toolbarPlaylist.setTitleTextColor(getResources().getColor(R.color.purple_500));
        binding.toolbarPlaylist.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}