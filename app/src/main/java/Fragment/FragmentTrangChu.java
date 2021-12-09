package Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.appzingmp3.R;
import com.example.appzingmp3.databinding.FragmentTrangChuBinding;

public class FragmentTrangChu extends Fragment {

    FragmentTrangChuBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_trang_chu,container,false);
        return binding.getRoot();
    }
}
