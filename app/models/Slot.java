package models;

import play.data.validation.Constraints;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

/**
 * A slot is a slice of time across all tracks.
 *
 * @author kristian.haugene
 */
@Entity
public class Slot {

    @Id
    public Long id;

    // Start time for this slot
    @Constraints.Required
    public Date startTime;

    // End time for this slot
    @Constraints.Required
    public Date endTime;

    // Presentations for this Slot
    @OneToMany(mappedBy="slot", cascade= CascadeType.ALL)
    public List<Presentation> presentations;

}