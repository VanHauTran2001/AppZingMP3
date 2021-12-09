package Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.appzingmp3.R;
import com.example.appzingmp3.databinding.FragmentPlayListBinding;

import java.util.ArrayList;
import java.util.List;

import Activity.DanhSachBaihatActivity;
import Activity.DanhSachPlaylistActivity;
import Adapter.PlaylistAdapter;
import Model.PlayList;
import Service.APIService;
import Service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPlayList extends Fragment {
    FragmentPlayListBinding binding;
    PlaylistAdapter playlistAdapter;
    ArrayList<PlayList> playListArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_play_list,container,false);
        getData();
        binding.txtXemThemPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanhSachPlaylistActivity.class);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<PlayList>> listCall = dataService.getPlayListCurrentDay();
        listCall.enqueue(new Callback<List<PlayList>>() {
            @Override
            public void onResponse(Call<List<PlayList>> call, Response<List<PlayList>> response) {
                playListArrayList = (ArrayList<PlayList>) response.body();
                playlistAdapter = new PlaylistAdapter(getActivity(), android.R.layout.simple_list_item_1,playListArrayList);
                binding.listviewPlaylist.setAdapter(playlistAdapter);
                setListViewHeightBasedOnChildren(binding.listviewPlaylist);
                binding.listviewPlaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), DanhSachBaihatActivity.class);
                        intent.putExtra("playlist",playListArrayList.get(position));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<PlayList>> call, Throwable t) {

            }
        });
    }
    //set chieu cao cua listview
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if(listItem != null){
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();

            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
