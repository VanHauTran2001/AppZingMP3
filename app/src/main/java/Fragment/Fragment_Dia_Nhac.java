package Fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.appzingmp3.R;
import com.example.appzingmp3.databinding.FragmentDiaNhacBinding;

public class Fragment_Dia_Nhac extends Fragment{
   FragmentDiaNhacBinding binding;
   public static ObjectAnimator objectAnimator;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dia_nhac,container,false);
        objectAnimator = ObjectAnimator.ofFloat(binding.circleZing,"rotation",0f,360f);
        objectAnimator.setDuration(10000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
        return binding.getRoot();
    }
    public void PlayNhac(String hinhanh){
        Glide.with(getContext()).load(hinhanh).into(binding.circleZing);
    }

}
