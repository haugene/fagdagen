package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Collections;
import java.util.Comparator;
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

    // Create Finder for Track objects
    public static Model.Finder<String, Track> find = new Model.Finder(String.class, Track.class);

    // -- Queries
    /**
     * Retrieve all tracks.
     */
    public static List<Track> findAll() {

        List<Track> tracks = find.all();

        Collections.sort(tracks, new Comparator<Track>() {
            @Override
            public int compare(Track track, Track track2) {
                return track.rank.compareTo(track2.rank);
            }
        });

        return tracks;
    }

}
