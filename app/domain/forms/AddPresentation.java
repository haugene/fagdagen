package domain.forms;

import models.Slot;
import models.Track;

/**
 * Represents the add new presentation form
 */
public class AddPresentation {
    public String name;
    public String presenters;
    public String description;

    public Slot slot;
    public Track track;

    public int rank;
}
