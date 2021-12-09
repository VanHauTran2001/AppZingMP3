package Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appzingmp3.R;
import com.example.appzingmp3.databinding.FragmentBaiHatBinding;

import java.util.ArrayList;
import java.util.List;

import Adapter.BaiHatYeuThichApdater;
import Model.BaiHat;
import Service.APIService;
import Service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBaiHat extends Fragment {
    FragmentBaiHatBinding binding;
    ArrayList<BaiHat> baiHatArrayList = new ArrayList<>();
    BaiHatYeuThichApdater baiHatYeuThichApdater;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bai_hat,container,false);
        getData();
        return binding.getRoot();
    }

    private void getData() {
        //khởi tạo retrofit và khởi tạo class của data Service
        DataService dataService = APIService.getService();
        //Khi gửi lên server và nhận dữ liệu về nó sẽ trả về function của callback
        Call<List<BaiHat>> callback = dataService.getBaiHatYeuThich();
        //nhận dữ liệu sau khi thực thi
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                baiHatArrayList = (ArrayList<BaiHat>) response.body();
                baiHatYeuThichApdater = new BaiHatYeuThichApdater(getActivity(),baiHatArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                binding.recylerBaiHat.setLayoutManager(linearLayoutManager);
                binding.recylerBaiHat.setAdapter(baiHatYeuThichApdater);
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
}
