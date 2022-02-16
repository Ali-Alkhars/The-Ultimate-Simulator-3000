/**
 * 2022.02.16
 */
public class AliTester
{
    public static void main(String[] args)
    {
//        Simulator sim = new Simulator(100, 100);
//        sim.runLongSimulation();

//        timeClassTest();
        habitatTester();
    }

    public static void timeClassTest()
    {
        SimulationStep sim = new SimulationStep(0);
        Time time = new Time(sim, false);

        for(int i= 0; i < 50; i++)
        {
            time.checkDay();
            System.out.println("Step: " + sim.getCurrentStep() + "\t" + time.timeString()+ "\n");
            sim.incStep();
            time.incHour();
        }
    }

    public static void habitatTester()
    {
        Season summer = new Season("summer", 35, 5);
        Season spring = new Season("spring", 20, 1);
        Season winter = new Season("winter", 0, 10);
        Season autumn = new Season("autumn", 15, 5);
        SimulationStep sim = new SimulationStep();

        Habitat italy = new Habitat(sim, false, spring, summer, autumn, winter);
        Time time = new Time(sim, false);

        for(int i=0; i < 729; i++)
        {
            System.out.println("Step: " + sim.getCurrentStep() + "\n" + time.timeString()+ "\nSeason: " + italy.getCurrentSeason() + "\nTemperature: " + italy.getCurrentTemperature() + "\n");
            italy.checkSeason();
            time.checkDay();
            sim.incStep();
            time.incHour();
        }
    }
}
