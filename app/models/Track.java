package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a track.
 * Within a track, there are presentations.
 *
 * @author kristian.haugene
 * @author daniel.nordstrom.carlsen
 */
@Entity
public class Track extends Model implements Comparable<Track> {

    @Id
    public Long id;

    // Each track should have a namae
    @Constraints.Required
    public String name;

    // Each track has a rank that allows us to place it correctly
    @Constraints.Required
    public Integer rank;

    // Presentations for this Track
    @OneToMany(mappedBy="track", cascade= CascadeType.ALL)
    public List<Presentation> presentations;

    // Create Finder for Track objects
    public static Model.Finder<Long, Track> find = new Model.Finder(Long.class, Track.class);

    /**
     * Retrieve relative rank compared to other tracks
     */
    public int getRelativeRanking() {
        List<Track> tracks = find.all();

        int relativeRank = 1;

        for(Track track: tracks) {
            if(rank > track.rank) {
                relativeRank++;
            }
        }

        return relativeRank;
    }

    // -- Queries
    /**
     * Retrieve all tracks.
     */
    public static List<Track> findAll() {

        List<Track> tracks = find.all();

        Collections.sort(tracks);

        return tracks;
    }

    public List<Presentation> getPresentations(Slot slot) {
        List<Presentation> presentations = new ArrayList<Presentation>();

        for(Presentation presentation: slot.presentations) {
            if(presentation.track.id.equals(this.id)) {
                presentations.add(presentation);
            }
        }

        return presentations;
    }


    @Override
    public int compareTo(Track that) {
        return this.rank.compareTo(that.rank);
    }
}
