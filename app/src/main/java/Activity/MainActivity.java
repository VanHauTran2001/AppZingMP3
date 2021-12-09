package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.appzingmp3.R;
import com.example.appzingmp3.databinding.ActivityMainBinding;

import Adapter.MainViewPagerAdapter;
import Fragment.FragmentTimKiem;
import Fragment.FragmentTrangChu;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        init();
    }
    private void init(){
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFragment(new FragmentTrangChu(),"Trang Chủ");
        mainViewPagerAdapter.addFragment(new FragmentTimKiem(),"Tìm Kiếm");
        binding.viewPager.setAdapter(mainViewPagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.getTabAt(0).setIcon(R.drawable.icontrangchu);
        binding.tabLayout.getTabAt(1).setIcon(R.drawable.iconsearch);
    }
}