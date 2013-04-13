package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Represents a vote for a Presentation
 */
@Entity
public class Vote extends Model {

    @Id
    public Long id;

    @Constraints.Required
    @ManyToOne
    public Presentation presentation;

    @Constraints.Required
    public Integer rating;

    @Constraints.Required
    public String sessionUuid;

    // Create Finder for Vote objects
    public static Model.Finder<Long, Vote> find = new Model.Finder(Long.class, Vote.class);

    /**
     * Loops all votes for a given presentation. If a vote is found, that is frm the same sessionId as the current,
     * a vote is returned. Otherwise, we return null. No vote registered for this sessionId/presentation pair
     * @param sessionId
     * @param presentation
     * @return
     */
    public Vote getVoteForPresentation(String sessionId, Presentation presentation)
    {
//        for(Vote vote : presentation.votes)
//        {
//            if (vote.sessionUuid.equals(sessionId))
//            {
//                return vote;
//            }
//        }
        return null;
    }

    public Vote(Presentation presentation, Integer rating, String sessionUuid)
    {
        this.presentation = presentation;
        this.rating = rating;
        this.sessionUuid = sessionUuid;
    }
}
