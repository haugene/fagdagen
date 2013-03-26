package models;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.*;

/**
 * A slot is a slice of time across all tracks.
 *
 * @author kristian.haugene
 */
@Entity
public class Slot implements Comparable<Slot>{

    @Id
    public Long id;

    // Start time for this slot
    @Constraints.Required
    public Date startTime;

    // End time for this slot
    @Constraints.Required
    public Date endTime;

    public boolean isBreak = false;

    // Presentations for this Slot
    @OneToMany(mappedBy="slot", cascade= CascadeType.ALL)
    public List<Presentation> presentations;

    // Create a finder
    public static Model.Finder<Long, Slot> find = new Model.Finder<Long, Slot>(Long.class, Slot.class);

    /**
     * A method for reading presentations in an orderly fashion
     * @return a map of all the slots and the presentations that is attributed to it
     */
    public static Map<Slot, List<Presentation>> getPresentationsBySlot()
    {
        // Get all the slots
        List<Slot> slots = find.all();

        /*
         * Now, create a map of all slots and their presentations.
         * We want the map to be sorted, let's go for a TreeMap
         */
        Map<Slot, List<Presentation>> result = new TreeMap<Slot, List<Presentation>>();

        for(Slot slot : slots)
        {
            // For each slot, add the slot and its presentations
            result.put(slot, slot.presentations);
        }

        // Our work here is done, return it
        return result;
    }

    @Override
    public int compareTo(Slot that) {
        return this.startTime.compareTo(that.startTime);
    }

    public String getFormattedStartTime()
    {
        return getHoursAndMinutes(startTime);
    }

    public String getFormattedEndTime()
    {
        return getHoursAndMinutes(endTime);
    }

    private String getHoursAndMinutes(Date date)
    {
        // Use DateTime, always simpler
        DateTime dateTime = new DateTime(date);

        String hours = String.valueOf(dateTime.getHourOfDay());
        String minutes = String.valueOf(dateTime.getMinuteOfHour());

        return StringUtils.leftPad(hours, 2, "0") + ":" + StringUtils.leftPad(minutes, 2, "0");
    }
}
