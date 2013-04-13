package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
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

    @OneToMany(mappedBy="presentation", cascade= CascadeType.ALL)
    public List<Vote> votes;

    private static final int SHORT_DESCRIPTION_LENGTH = 120;
    private static final int SHORT_KEYNOTE_DESCRIPTION_LENGTH = 400;

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

    // Create a Finder for Presentation objects
    public static Finder<Long, Presentation> find = new Finder(Long.class, Presentation.class);

    @Override
    public int compareTo(Presentation that) {
        return this.rank.compareTo(that.rank);
    }

    /**
     * Retrieves a short version of the description, were length is specified by SHORT_DESCRIPTION_LENGTH.
     *
     * @return
     */
    public String getShortDescription() {
        // Return a longer description if it's a keynote
        if(slot.containsKeynotes()) {
            if(description.length() < SHORT_KEYNOTE_DESCRIPTION_LENGTH) {
                return description;
            }

            String shortDescription = description.substring(0, SHORT_KEYNOTE_DESCRIPTION_LENGTH);

            return shortDescription.substring(0, shortDescription.lastIndexOf(" ")) + "...";
        }

        if(description.length() < SHORT_DESCRIPTION_LENGTH) {
            return description;
        }

        String shortDescription = description.substring(0, SHORT_DESCRIPTION_LENGTH);

        return shortDescription.substring(0, shortDescription.lastIndexOf(" ")) + "...";
    }

    public Integer getNumberOfVotes()
    {
        return votes.size();
    }

    public Double getAverageVote()
    {
        Double total = 0d;
        for(Vote vote : votes)
        {
            total += vote.rating;
        }

        return total / votes.size();
    }
}
