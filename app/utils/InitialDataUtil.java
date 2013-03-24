package utils;

import com.avaje.ebean.Ebean;
import models.Slot;
import org.joda.time.DateTime;
import play.Logger;

/**
 * This utility class will add some slots and presentations to the database
 */
public class InitialDataUtil {

    public static void addInitialData()
    {
        Logger.of(InitialDataUtil.class).info("Adding initial data");

        addSlots();
    }

    private static void addSlots()
    {
        // First slot
        addSlot(9, 0, null);

        // Break
        addSlot(9, 30, 15);

        // Second slot
        addSlot(9, 45, null);

        // Break
        addSlot(10, 15, 15);

        // Third slot
        addSlot(10, 30, null);
    }

    /**
     * Adds a slot to the database.
     * @param hour the hour of the day for the presentation
     * @param minute the minute of the hour for the presentation
     * @param duration duration of the slot in minutes, can be null. Defaults to 30 min
     */
    private static void addSlot(Integer hour, Integer minute, Integer duration)
    {

        // Create a new slot
        Slot slot = new Slot();

        // Set startTime
        slot.startTime = new DateTime(2013, 4, 25, hour, minute).toDate();

        // Set endTime. If duration is given, use it. Else default to 30 min
        if(duration != null)
        {
            slot.endTime = new DateTime(slot.startTime).plusMinutes(duration).toDate();
        } else
        {
            slot.endTime = new DateTime(slot.startTime).plusMinutes(30).toDate();
        }

        // And save
        Ebean.save(slot);
    }
}
