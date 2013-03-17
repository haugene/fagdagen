package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Represents a track.
 * Within a track, there are presentations.
 *
 * @author kristian.haugene
 */
@Entity
public class Track extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String location;

    @OneToMany(cascade = CascadeType.PERSIST)
    public List<Presentation> presentations;

}
