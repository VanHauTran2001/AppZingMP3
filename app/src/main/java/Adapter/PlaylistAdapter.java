package Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.appzingmp3.R;

import java.util.ArrayList;
import java.util.List;

import Activity.DanhSachBaihatActivity;
import Model.PlayList;

public class PlaylistAdapter extends ArrayAdapter<PlayList> {

    public PlaylistAdapter(Context context, int resource, List<PlayList> objects) {
        super(context, resource, objects);
    }
    class ViewHolder{
        TextView txtPlaylist;
        ImageView imgbackgroupPlaylist , imgPlaylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dong_play_list,null);
            viewHolder = new ViewHolder();
            viewHolder.txtPlaylist = convertView.findViewById(R.id.txtPlaylist);
            viewHolder.imgbackgroupPlaylist = convertView.findViewById(R.id.imgbackgroupPlaylist);
            viewHolder.imgPlaylist = convertView.findViewById(R.id.imgPlaylist);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewHolder.imgbackgroupPlaylist.setClipToOutline(true);
        }
        PlayList playList = getItem(position);
        Glide.with(getContext()).load(playList.getHinhnen()).into(viewHolder.imgbackgroupPlaylist);
        Glide.with(getContext()).load(playList.getHinhicon()).into(viewHolder.imgPlaylist);
        viewHolder.txtPlaylist.setText(playList.getTen());
        return convertView;

    }
}
