package Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appzingmp3.R;
import com.example.appzingmp3.databinding.FragmentAlbumHotBinding;

import java.util.ArrayList;
import java.util.List;

import Activity.DanhSachTatCaAlbumActivity;
import Adapter.AlbumAdapter;
import Model.AlbumHot;
import Service.APIService;
import Service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAlbumHot extends Fragment {
    FragmentAlbumHotBinding binding;
    AlbumAdapter albumAdapter;
    ArrayList<AlbumHot> albumHotArrayList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_album_hot,container,false);
        getData();
        binding.txtXemthemAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DanhSachTatCaAlbumActivity.class));
            }
        });
        return binding.getRoot();
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<AlbumHot>> call = dataService.getAlbumHot();
        call.enqueue(new Callback<List<AlbumHot>>() {
            @Override
            public void onResponse(Call<List<AlbumHot>> call, Response<List<AlbumHot>> response) {
                 albumHotArrayList = (ArrayList<AlbumHot>) response.body();
                 albumAdapter = new AlbumAdapter(getActivity(),albumHotArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                binding.recylerAlbum.setAdapter(albumAdapter);
                binding.recylerAlbum.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onFailure(Call<List<AlbumHot>> call, Throwable t) {

            }
        });
    }
}
