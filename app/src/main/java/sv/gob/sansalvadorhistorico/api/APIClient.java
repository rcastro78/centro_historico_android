package sv.gob.sansalvadorhistorico.api;

public class APIClient {
    public static final String BASE_URL = "https://sansalvadorhistorico.com/app/";
    public static ISanSalvadorHistorico getSSHistoricoService(){
        return RetrofitClient.getClient(BASE_URL).create(ISanSalvadorHistorico.class);
    }
}
