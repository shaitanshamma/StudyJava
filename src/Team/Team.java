package Team;
import Inter.*;
public class Team {

    String name;
    public Competitor[] com;

    public Team(String name, Competitor[] com) {
        this.name = name;
        this.com = com;
    }

    public void info(){
        System.out.println("Название команды - " + name);
        for (Competitor c : com) {
            c.info();
        }
    }
}
