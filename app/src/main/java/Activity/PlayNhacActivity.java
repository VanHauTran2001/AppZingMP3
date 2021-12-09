package Activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.SeekBar;

import com.example.appzingmp3.R;
import com.example.appzingmp3.databinding.ActivityPlayNhacBinding;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import Model.BaiHat;
import Fragment.Fragment_Dia_Nhac;
import Fragment.FragmentPlayBaiHat;
public class PlayNhacActivity extends AppCompatActivity {
    ActivityPlayNhacBinding binding;
    public static ArrayList<BaiHat> baiHatArrayList = new ArrayList<>();
    MediaPlayer mediaPlayer;
    ViewPagerAdapter viewPagerAdapter;
    Fragment_Dia_Nhac fragmentDiaNhac = new Fragment_Dia_Nhac();
    FragmentPlayBaiHat fragmentPlayBaiHat = new FragmentPlayBaiHat();
    int position =0;
    boolean repeat = false;
    boolean checkrandum = false;
    boolean next = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_play_nhac);
        //kiem tra tin hieu mang
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getDataIntent();
        init();
        eventClick();
    }

    private void eventClick() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //kiem tra fragment co get du lieu dc k
                if (viewPagerAdapter.getItem(1) != null){
                    if (baiHatArrayList.size()>0){
                        fragmentDiaNhac.PlayNhac(baiHatArrayList.get(0).getHinhBaiHat());
                        handler.removeCallbacks(this);
                    }else {
                        handler.postDelayed(this,300);
                    }
                }
            }
        },500);
        binding.imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer !=null){
                    if (mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                        binding.imgPlay.setImageResource(R.drawable.iconplay);
                        Fragment_Dia_Nhac.objectAnimator.cancel();
                    }else {
                        mediaPlayer.start();
                        binding.imgPlay.setImageResource(R.drawable.ic_pause);
                        Fragment_Dia_Nhac.objectAnimator.start();
                    }
                }
            }
        });
        binding.imgRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repeat==false){
                    if (checkrandum == true){
                        checkrandum = false;
                        binding.imgRepeat.setImageResource(R.drawable.iconsyned);
                        binding.imgSuffe.setImageResource(R.drawable.iconsuffle);
                    }
                    binding.imgRepeat.setImageResource(R.drawable.iconsyned);
                    repeat = true;
                }else {
                    binding.imgRepeat.setImageResource(R.drawable.iconrepeat);
                    repeat = false;
                }
            }
        });
        binding.imgSuffe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkrandum==false){
                    if (repeat == true){
                        repeat = false;
                        binding.imgSuffe.setImageResource(R.drawable.iconshuffled);
                        binding.imgRepeat.setImageResource(R.drawable.iconrepeat);
                    }
                    binding.imgSuffe.setImageResource(R.drawable.iconshuffled);
                    checkrandum  = true;
                }else {
                    binding.imgSuffe.setImageResource(R.drawable.iconsuffle);
                    checkrandum = false;
                }
            }
        });
        binding.seekBarPlaynhac.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(binding.seekBarPlaynhac.getProgress());
            }
        });
        binding.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (baiHatArrayList.size()>0){
                    if (mediaPlayer.isPlaying() || mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position < (baiHatArrayList.size())){
                        binding.imgPlay.setImageResource(R.drawable.ic_pause);
                        position++;
                        if (repeat==true){
                            if (position == 0){
                                position = baiHatArrayList.size();
                            }
                            position -= 1;
                        }
                        if (checkrandum == true){
                            Random random = new Random();
                            int index = random.nextInt(baiHatArrayList.size());
                            if (index==position){
                                position = index - 1;
                            }
                            position = index;
                        }
                        if (position > (baiHatArrayList.size() - 1)){
                            position = 0;
                        }
                        new PlayMp3().execute(baiHatArrayList.get(position).getLinkBaiHat());
                        fragmentDiaNhac.PlayNhac(baiHatArrayList.get(position).getHinhBaiHat());
                        getSupportActionBar().setTitle(baiHatArrayList.get(position).getTenBaiHat());
                        updateTime();
                    }
                }
                binding.imgPreview.setEnabled(false);
                binding.imgNext.setEnabled(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.imgPreview.setEnabled(true);
                        binding.imgNext.setEnabled(true);
                    }
                },5000);
            }
        });
        binding.imgPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (baiHatArrayList.size()>0){
                    if (mediaPlayer.isPlaying() || mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position < (baiHatArrayList.size())){
                        binding.imgPlay.setImageResource(R.drawable.ic_pause);
                        position--;
                        if (position <0) {
                            position = baiHatArrayList.size() - 1;
                        }
                        if (repeat==true){
                            position += 1;
                        }
                        if (checkrandum == true){
                            Random random = new Random();
                            int index = random.nextInt(baiHatArrayList.size());
                            if (index==position){
                                position = index - 1;
                            }
                            position = index;
                        }
                        new PlayMp3().execute(baiHatArrayList.get(position).getLinkBaiHat());
                        fragmentDiaNhac.PlayNhac(baiHatArrayList.get(position).getHinhBaiHat());
                        getSupportActionBar().setTitle(baiHatArrayList.get(position).getTenBaiHat());
                        updateTime();
                    }
                }
                binding.imgPreview.setEnabled(false);
                binding.imgNext.setEnabled(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.imgPreview.setEnabled(true);
                        binding.imgNext.setEnabled(true);
                    }
                },5000);
            }
        });
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        baiHatArrayList.clear();
        if (intent !=null){
            if (intent.hasExtra("playnhac")){
                BaiHat baiHat = intent.getParcelableExtra("playnhac");
                baiHatArrayList.add(baiHat);
            }
            if (intent.hasExtra("mangbaihat")){
                ArrayList<BaiHat> mangBaihat = intent.getParcelableArrayListExtra("mangbaihat");
                baiHatArrayList = mangBaihat;
            }
        }

    }

    private void init() {
        setSupportActionBar(binding.toolbarPlayNhac);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarPlayNhac.setTitleTextColor(getResources().getColor(R.color.white));
        binding.toolbarPlayNhac.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                baiHatArrayList.clear();
                finish();
            }
        });
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(fragmentPlayBaiHat);
        viewPagerAdapter.addFragment(fragmentDiaNhac);
        binding.viewPagerPlay.setAdapter(viewPagerAdapter);

        //play nhac
        fragmentDiaNhac = (Fragment_Dia_Nhac) viewPagerAdapter.getItem(1);
        if (baiHatArrayList.size()>0){
            getSupportActionBar().setTitle(baiHatArrayList.get(0).getTenBaiHat());
            new PlayMp3().execute(baiHatArrayList.get(0).getLinkBaiHat());
            binding.imgPlay.setImageResource(R.drawable.ic_pause);
        }

    }
    //custom viewpager
    public class ViewPagerAdapter extends FragmentPagerAdapter{
        private ArrayList<Fragment> fragments;


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments =new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
        @Override
        public int getCount() {
            return fragments.size();
        }
        public void addFragment(Fragment fragment){
            fragments.add(fragment);
        }
    }

    //custom mediaplayer
    class PlayMp3 extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat) {
            super.onPostExecute(baihat);
            try {
            mediaPlayer = new MediaPlayer();
            //play nhạc online
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
            });
            //khởi tạo dữ liệu ca khúc
                mediaPlayer.setDataSource(baihat);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            TimeSong();
            updateTime();
        }
    }
    private void TimeSong(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        //tổng thời gian bài hát
        binding.txtTimeEnd.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        binding.seekBarPlaynhac.setMax(mediaPlayer.getDuration());
    }
    private void updateTime(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            if (mediaPlayer !=null){
                binding.seekBarPlaynhac.setProgress(mediaPlayer.getCurrentPosition());
                SimpleDateFormat simpleDateFormat =new SimpleDateFormat("mm:ss");
                binding.txtTimeStart.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                handler.postDelayed(this,300);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        next = true;
                        try {
                        Thread.sleep(1000);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                });
            }
            }
        },300);
        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (next== true){
                    if (position < (baiHatArrayList.size())){
                        binding.imgPlay.setImageResource(R.drawable.ic_pause);
                        position--;
                        if (position <0) {
                            position = baiHatArrayList.size() - 1;
                        }
                        if (repeat==true){
                            position += 1;
                        }
                        if (checkrandum == true){
                            Random random = new Random();
                            int index = random.nextInt(baiHatArrayList.size());
                            if (index==position){
                                position = index - 1;
                            }
                            position = index;
                        }
                        new PlayMp3().execute(baiHatArrayList.get(position).getLinkBaiHat());
                        fragmentDiaNhac.PlayNhac(baiHatArrayList.get(position).getHinhBaiHat());
                        getSupportActionBar().setTitle(baiHatArrayList.get(position).getTenBaiHat());
                        updateTime();
                    }
                binding.imgPreview.setEnabled(false);
                binding.imgNext.setEnabled(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.imgPreview.setEnabled(true);
                        binding.imgNext.setEnabled(true);
                    }
                },5000);
                next = false;
                handler1.removeCallbacks(this);
                }else {
                    handler1.postDelayed(this,1000);
                }
            }
        },1000);
    }
}