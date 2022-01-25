package sv.gob.sansalvadorhistorico.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "tbl_notificaciones", id="id")
public class NotificacionDB extends Model {
    @Column(name="postId",unique = true)
    public String postId;
    @Column(name="post_title")
    public String post_title;
    @Column(name="content")
    public String content;
    @Column(name="guid")
    public String guid;
    @Column(name="date_published")
    public String date_published;



}
