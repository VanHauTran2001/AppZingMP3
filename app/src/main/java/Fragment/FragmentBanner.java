package Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.appzingmp3.R;
import com.example.appzingmp3.databinding.FragmentBannerBinding;

import java.util.ArrayList;
import java.util.List;

import Adapter.BannerAdapter;
import Model.Quangcao;
import Service.APIService;
import Service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBanner extends Fragment {
   FragmentBannerBinding binding;
   BannerAdapter bannerAdapter;
   Runnable runnable;
   Handler handler;
   int current;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_banner,container,false);
        getData();
        return binding.getRoot();
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<Quangcao>> callBack = dataService.getDataBanner();
        callBack.enqueue(new Callback<List<Quangcao>>() {
            @Override
            public void onResponse(Call<List<Quangcao>> call, Response<List<Quangcao>> response) {
                ArrayList<Quangcao> banners = (ArrayList<Quangcao>) response.body();
                bannerAdapter = new BannerAdapter(getActivity(),banners);
                binding.viewPager.setAdapter(bannerAdapter);
                binding.Circleindicator.setViewPager(binding.viewPager);
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        current = binding.viewPager.getCurrentItem();
                        current++;
                        if (current>=binding.viewPager.getAdapter().getCount()){
                            current=0;
                        }
                        binding.viewPager.setCurrentItem(current,true);
                        handler.postDelayed(runnable,7000);
                    }
                };
                handler.postDelayed(runnable,7000);
            }

            @Override
            public void onFailure(Call<List<Quangcao>> call, Throwable t) {

            }
        });
    }
}
