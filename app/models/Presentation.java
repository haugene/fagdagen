package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Represents a presentation.
 *
 * @author kristian.haugene
 */
@Entity
public class Presentation extends Model {

    @Id
    public Long id;

    // Name of the presentation
    @Constraints.Required
    public String name;

    // The presentation abstract
    @Constraints.Required
    public String description;

    // Who is presenting this?
    @Constraints.Required
    public String presenter;

    // In what slot(time)
    @Constraints.Required
    @ManyToOne
    public Slot slot;

    // Which track(location)
    @Constraints.Required
    @ManyToOne
    public Track track;

    // Rank within slot
    @Constraints.Required
    public Long rank;

    // Is this a presentation for all?(keynotes etc)
    public boolean spanAllTracks = false;
}
