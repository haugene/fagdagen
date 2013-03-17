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

    // Each track should have a namae
    @Constraints.Required
    public String name;

    // Each track has a rank that allows us to place it correctly
    @Constraints.Required
    public Long rank;

    // Presentations for this Track
    @OneToMany(mappedBy="track", cascade= CascadeType.ALL)
    public List<Presentation> presentations;

}
