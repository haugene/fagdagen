package utils;

import com.avaje.ebean.Ebean;
import models.Presentation;
import models.Slot;
import models.Track;
import org.joda.time.DateTime;
import play.Logger;

import java.util.Collections;
import java.util.List;

/**
 * This utility class will add some slots and presentations to the database
 */
public class InitialDataUtil {

    public static void addInitialData()
    {
        Logger.of(InitialDataUtil.class).info("Adding initial data");

        addSlots();
        addPresentations();
    }

    /**
     * Helper method that adds the slots.
     * We group them, adding "presentation slots" first and breaks(15 min) after.
     * This is so that we don't assume anything from the ordering in the real database, as this is test data.
     */
    private static void addSlots()
    {
        // First slot
        addSlot(9, 0, null);

        // Second slot
        addSlot(9, 45, null);

        // Third slot
        addSlot(10, 30, null);

        // Break 1
        addSlot(9, 30, 15);

        // Break 2
        addSlot(10, 15, 15);
    }

    /**
     * Adds sample presentations to the slots
     */
    private static void addPresentations() {

        // Get all slots and sort them.
        List<Slot> slots = Slot.find.all();
        Collections.sort(slots);

        // Get all Tracks and sort them
        List<Track> tracks = Track.find.all();
        Collections.sort(tracks);

        // For each slot, add a presentation for each track
        for (Slot slot : slots)
        {
            for(Track track : tracks)
            {
                addPresentation(slot, track);
            }
        }
    }

    private static void addPresentation(Slot slot, Track track) {

        Presentation presentation = new Presentation();
        presentation.name = "Hello World";
        presentation.description = "This is a description of the presentation. Formally we like to call it an abstract. This should give a brief introduction to the topic";
        presentation.presenter = "Stephen Hawking";
        presentation.slot = slot;
        presentation.track = track;

        // For now we only add one presentation per slot. They can all have rank 1.
        presentation.rank = 1;

        Ebean.save(presentation);
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
