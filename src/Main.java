import Inter.Competitor;
import Team.*;
import Course.*;

public class Main {
    public static void main(String[] args) {
        Competitor[] competitors = {new Human("Вася",900,11,22,true),
                new Human("Петя",100,11,22,true),
                new Human("Сирожа",500,11,22,true)};
        Obstacle[] obstacles = {new Cross(80), new Wall(2), new Wall(1), new Cross(120)};
        Course course1 = new Course("Простой",obstacles);
        Team team1 = new Team("Первые",competitors);
        course1.doIt(team1.com);
        team1.info();
    }
}