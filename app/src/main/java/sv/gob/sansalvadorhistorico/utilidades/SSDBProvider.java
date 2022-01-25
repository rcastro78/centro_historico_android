package sv.gob.sansalvadorhistorico.utilidades;

import com.activeandroid.Configuration;
import com.activeandroid.content.ContentProvider;
import sv.gob.sansalvadorhistorico.db.NotificacionDB;

public class SSDBProvider extends ContentProvider {
    protected Configuration getConfiguration() {
        Configuration.Builder builder = new Configuration.Builder(getContext());
        builder.addModelClass(NotificacionDB.class);
        return builder.create();
    }
}