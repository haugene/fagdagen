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
        addSlot(9, 0, null);
    }

    /**
     * Adds a slot to the database.
     * @param hour the hour of the day for the presentation
     * @param minute the minute of the hour for the presentation
     * @param duration duration of the slot in minutes, can be null. Defaults to 30 min
     */
    private static void addSlot(Integer hour, Integer minute, Integer duration)
    {
        Slot slot = new Slot();
        slot.startTime = new DateTime(2013, 4, 25, hour, minute).toDate();
        if(duration != null)
        {
            slot.endTime = new DateTime(slot.startTime).plusMinutes(duration).toDate();
        } else
        {
            slot.endTime = new DateTime(slot.startTime).plusMinutes(30).toDate();
        }

        Ebean.save(slot);
    }
}
