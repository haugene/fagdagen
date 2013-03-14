package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Represents a presentation.
 *
 * @author kristian.haugene
 */
@Entity
public class Presentation extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String name;

    @Constraints.Required
    public String description;

    @Constraints.Required
    public String presenter;
}
