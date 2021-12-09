package Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.appzingmp3.R;
import com.example.appzingmp3.databinding.FragmentPlayBaiHatBinding;
import Activity.PlayNhacActivity;
import Adapter.PlayBaiHatAdapter;

public class FragmentPlayBaiHat extends Fragment {
    FragmentPlayBaiHatBinding binding;
    PlayBaiHatAdapter playBaiHatAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_play_bai_hat,container,false);
        if (PlayNhacActivity.baiHatArrayList.size()>0){
            playBaiHatAdapter = new PlayBaiHatAdapter(getActivity(), PlayNhacActivity.baiHatArrayList);
            binding.recylerPlayBaiHat.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.recylerPlayBaiHat.setAdapter(playBaiHatAdapter);
        }
        return binding.getRoot();
    }
}
