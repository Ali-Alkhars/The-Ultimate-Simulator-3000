public enum ClimateChange
{
    SCENARIO1(0.05), SCENARIO2(0.1), SCENARIO3(0.3);

    private double changePercentage;

    ClimateChange(double changePercentage)
    {
        this.changePercentage = changePercentage;
    }

    public double getChangePercentage()
    {
        return changePercentage;
    }
}
