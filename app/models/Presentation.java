package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.*;

/**
 * Represents a presentation.
 *
 * @author kristian.haugene
 * @author daniel.nordstrom.carlsen
 */
@Entity
public class Presentation extends Model implements Comparable<Presentation> {

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

    @Constraints.Required
    public String businessUnit;

    // In what slot(time)
    @Constraints.Required
    @ManyToOne
    public Slot slot;

    // Which track(location)
    @Constraints.Required
    @ManyToOne
    public Track track;

    // Rank within slot and track
    @Constraints.Required
    public Integer rank;

    public Presentation() {
    }

    public Presentation(String name, String description, String presenter, String businessUnit, Slot slot, Track track, Integer rank) {
        this.name = name;
        this.description = description;
        this.presenter = presenter;
        this.businessUnit = businessUnit;
        this.slot = slot;
        this.track = track;
        this.rank = rank;
    }

    // Is this a presentation a keynote?(common for all tracks)
    public boolean isKeynote = false;

    // Create a Finder for Presentation objects
    public static Finder<Long, Presentation> find = new Finder(Long.class, Presentation.class);

    @Override
    public int compareTo(Presentation that) {
        return this.rank.compareTo(that.rank);
    }
}
