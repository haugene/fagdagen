package utils;

import models.Presentation;
import models.Slot;
import models.Track;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for rendering presentations.
 */
public class PresentationHelper {

    /**
     * We want to be able to iterate presentations by row and add them to view.
     * We then need to have three entries per row, even if one is null.
     * @return
     */
    public static List<Presentation> getSortedPresentations(Slot slot){

        List<Presentation> result = new ArrayList<Presentation>();

        List<Track> allTracks = Track.findAll();
        Map<Track, List<Presentation>> presentationsForTrack = new HashMap<Track, List<Presentation>>();

        int maxPresentations = 0;
        for(Track track : allTracks){

            List<Presentation> trackPresentations = slot.getPresentations(track);
            int trackSize = trackPresentations.size();
            if(trackSize > maxPresentations)
            {
                maxPresentations = trackSize;
            }

            // Put presentations in map, save db-calls
            presentationsForTrack.put(track, trackPresentations);
        }

        // Now iterate for max depth in all tracks
        for(int i = 0 ; i < maxPresentations ; i++){

            for(Track track : allTracks)
            {
                // Find the i'th element for this track and slot
                result.add(getElementOrNull(presentationsForTrack.get(track), i));
            }
        }
        return result;
    }

    /**
     * Read a Presentation at given index, return null if index is out of bounds
     * @param presentations
     * @param elementNo
     * @return
     */
    private static Presentation getElementOrNull(List<Presentation> presentations, int elementNo)
    {
        if(elementNo >= presentations.size()){
            return null;
        }
        return presentations.get(elementNo);
    }


}
