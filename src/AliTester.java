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
//        habitatTester();


//        System.out.println(cg.getChangePercentage());
    }

    public static void timeClassTest()
    {
        SimulationStep sim = new SimulationStep(0);
        Time time = new Time(sim, false);

        for(int i= 0; i < 50; i++)
        {
            System.out.println("Step: " + sim.getCurrentStep() + "\t" + time.timeString()+ "\n");
            sim.incStep();
            time.timeStep();
        }
    }

    public static void habitatTester()
    {
        Season summer = new Season("summer", 35, 5);
        Season spring = new Season("spring", 20, 1);
        Season winter = new Season("winter", 0, 10);
        Season autumn = new Season("autumn", 15, 5);
        ClimateScenarios change = ClimateScenarios.SCENARIO2;

        SimulationStep sim = new SimulationStep();

        Habitat italy = new Habitat(sim, change, new int[]{30, 5}, new int[]{20, 7}, new int[]{-4, 10}, new int[]{0, 5});
        Time time = new Time(sim, false);

        for(int i=0; i < 729; i++)
        {
            System.out.println("Step: " + sim.getCurrentStep() + "\n" + time.timeString()+ "\nSeason: " + italy.getCurrentSeason() + "\nTemperature: " + italy.getCurrentTemperature() + "\n");
            sim.incStep();
            italy.habitatStep();
            time.timeStep();
        }
    }
}
