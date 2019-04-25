package Course;
import Inter.Competitor;

public class Course {
    String level;
    public Obstacle[] obstacle;

    public Course(String level, Obstacle[] obstacle) {
        this.level = level;
        this.obstacle = obstacle;
    }

    public void doIt(Competitor[] competitor) {
        for (Competitor c : competitor) {
            for (Obstacle o : obstacle) {
                o.doIt(c);
                if (!c.isOnDistance()) break;
            }
        }

    }

}

