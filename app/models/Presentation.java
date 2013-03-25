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

    // Rank within slot and track
    @Constraints.Required
    public Integer rank;

    // Is this a presentation for all?(keynotes etc)
    public boolean spanAllTracks = false;

    // Create a Finder for Presentation objects
    public static Finder<Long, Presentation> find = new Finder(Long.class, Presentation.class);

    /**
     * A method for reading presentations in an orderly fashion
     * @return a map of all the slots and the presentations that is attributed to it
     */
    public Map<Slot, List<Presentation>> getPresentationsBySlot()
    {
        // Get all the presentations
        List<Presentation> presentations = find.all();

        // Find all distinct slots
        Set<Slot> slots = new HashSet<Slot>();
        for(Presentation presentation : presentations)
        {
            slots.add(presentation.slot);
        }

        /*
         * Now, create a map of all slots and their presentations.
         * We want the map to be sorted, let's go for a TreeMap
         * // TODO: kristian : 22.03.2013 : There is no sorting atm. Make Slot comparable and so on.
         */
        Map<Slot, List<Presentation>> result = new TreeMap<Slot, List<Presentation>>();

        // Then add, add and add stuff
        for(Slot slot : slots)
        {
            List<Presentation> presentationsForSlot = new ArrayList<Presentation>();

            // Find all presentations for this slot
            for(Presentation presentation : presentations)
            {
                if(presentation.slot.equals(slot))
                {
                    presentationsForSlot.add(presentation);
                }
            }

            // Then add the list to the map, with this Slot as key
            result.put(slot, presentationsForSlot);
        }

        // Our work here is done, return it
        return result;
    }
}
