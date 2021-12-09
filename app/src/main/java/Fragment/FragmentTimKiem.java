package Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appzingmp3.R;
import com.example.appzingmp3.databinding.FragmentTimKiemBinding;

import java.util.ArrayList;
import java.util.List;

import Adapter.TimKiemBaiHatAdapter;
import Model.BaiHat;
import Service.APIService;
import Service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTimKiem extends Fragment {
    FragmentTimKiemBinding binding;
    TimKiemBaiHatAdapter timKiemBaiHatAdapter;
    ArrayList<BaiHat> baiHatArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tim_kiem,container,false);
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbarSearch);
        binding.toolbarSearch.setTitle("");
        //chuc nang search
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem menuItem = menu.findItem(R.id.menuSearch);
//        SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SearchBaiHat(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void SearchBaiHat(String tukhoa){
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.getSearchBaiHat(tukhoa);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                baiHatArrayList = (ArrayList<BaiHat>) response.body();
                if (baiHatArrayList.size() > 0){
                    timKiemBaiHatAdapter = new TimKiemBaiHatAdapter(getActivity(),baiHatArrayList);
                    binding.recylerSearch.setLayoutManager(new LinearLayoutManager(getActivity()));
                    binding.recylerSearch.setAdapter(timKiemBaiHatAdapter);
                    binding.txtThongbaoSearch.setVisibility(View.GONE);
                    binding.recylerSearch.setVisibility(View.VISIBLE);
                }else {
                    binding.recylerSearch.setVisibility(View.GONE);
                    binding.txtThongbaoSearch.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
}
