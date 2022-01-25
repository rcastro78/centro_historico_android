package sv.gob.sansalvadorhistorico.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import sv.gob.sansalvadorhistorico.modelos.Categorias;
import sv.gob.sansalvadorhistorico.modelos.Evento;
import sv.gob.sansalvadorhistorico.modelos.Lugar;
import sv.gob.sansalvadorhistorico.modelos.LugaresCategoria;

public interface ISanSalvadorHistorico {
    @GET("getEventos.php")
    Call<List<Evento>> getEventos();

    @GET("getLugarCoordenadasFoto.php")
    Call<List<Lugar>> getLugaresPorCoordenadas(@Query("lat") String lat,@Query("lng") String lng, @Query("lang") String lang);

    @GET("getLugaresPorNombre.php")
    Call<List<Lugar>> getLugaresPorNombre(@Query("q") String q);

    @GET("getLugarPorNombre.php")
    Call<List<Lugar>> getLugarPorNombre(@Query("q") String q);

    @GET("getLugaresCategoriaUrl.php")
    Call<List<LugaresCategoria>> getLugaresPorCategoria(@Query("cat") String cat);

    @GET("getPostContent.php")
    Call<String> getPostContent(@Query("pid") String pid);

    @GET("getCategorias.php")
    Call<List<Categorias>> getCategorias();

    @GET("getLugarFoto3.php")
    Call<String> getLugarFoto(@Query("pid") String pid);

    @GET("getCategories.php")
    Call<List<Categorias>> getCategories();





}
