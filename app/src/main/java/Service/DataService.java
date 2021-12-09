package Service;

import java.util.List;

import Model.AlbumHot;
import Model.BaiHat;
import Model.ChuDe;
import Model.ChuDeTheLoaiToday;
import Model.PlayList;
import Model.Quangcao;
import Model.TheLoai;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {
    @GET("songbaner.php")
    Call<List<Quangcao>> getDataBanner();
    @GET("playlist.php")
    Call<List<PlayList>> getPlayListCurrentDay();
    @GET("chudetheloai.php")
    Call<ChuDeTheLoaiToday> getChuDeTheLoaiToday();
    @GET("albumhot.php")
    Call<List<AlbumHot>> getAlbumHot();
    @GET("baihatyeuthich.php")
    Call<List<BaiHat>> getBaiHatYeuThich();

    //gui du lieu len server
    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> getDanhsachbaihatQuangCao(@Field("idquangcao") String idquangcao);

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> getDanhsachbaihatPlaylist(@Field("idplaylist") String idplaylist);

    @GET("danhsachplaylist.php")
    Call<List<PlayList>> getDanhSachPlaylist();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> getDanhsachbaihatTheLoai(@Field("idtheloai") String idtheloai);

    @GET("tatcachude.php")
    Call<List<ChuDe>> getDanhsachtatcaChuDe();

    @FormUrlEncoded
    @POST("theloaitheochude.php")
    Call<List<TheLoai>> getTheLoaitheoChuDe(@Field("idchude") String idchude);

    @GET("tatcaalbum.php")
    Call<List<AlbumHot>> getDanhsachTatcaAlbum();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> getDanhsachbaihatAlbum(@Field("idalbum") String idalbum);

    @FormUrlEncoded
    @POST("luotthich.php")
    Call<String> UpdateLuotThich(@Field("luotthich") String luotthich , @Field("idbaihat") String idbaihat);

    @FormUrlEncoded
    @POST("bothich.php")
    Call<String> UpdateBoThich(@Field("luotthich") String luotthich , @Field("idbaihat") String idbaihat);

    @FormUrlEncoded
    @POST("timkiembaihat.php")
    Call<List<BaiHat>> getSearchBaiHat(@Field("tukhoa") String tukhoa);
}
