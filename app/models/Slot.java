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
     * Retrieves all slots from database and sorts them
     * @return sorted list of slots
     */
    public static List<Slot> getSortedSlots()
    {
        List<Slot> allSlots = find.all();
        Collections.sort(allSlots);

        return allSlots;
    }

    /**
     * Checks if this Slot contains one or more keynote presentations
     * @return
     */
    public Boolean containsKeynote()
    {
        for (Presentation presentation : presentations)
        {
            if (presentation.isKeynote)
            {
                // If any presentation is a keynote, return true
                return true;
            }
        }

        // None of the presentations were keynotes, return false
        return false;
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
