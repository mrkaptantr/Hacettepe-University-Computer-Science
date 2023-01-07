public class Assignment implements Comparable {
    private String name;
    private String start;
    private int duration;
    private int importance;
    private boolean maellard;
 
    /* 
        Getter methods 
    */
    public String getName() {
        return this.name;
    }
    public String getStartTime() {
        return this.start;
    }
    public int getDuration() {
        return this.duration;
    }
    public int getImportance() {
        return this.importance;
    }
    public boolean isMaellard() {
        return maellard;
    }
 
    public Assignment(String name, String start, int duration, int importance, boolean maellard){
        this.name = name;
        this.start = start;
        this.duration = duration;
        this.importance = importance;
        this.maellard = maellard;
    }
 
    /**
     * Finish time should be calculated here
     *
     * @return calculated finish time as String
     */
    public String getFinishTime() {
        int hour = Integer.parseInt((this.start).substring(0,2)) + this.duration;
        if (hour > 24) { hour -= 24; }
        if (hour < 10) { return ("0" + (String.valueOf(hour) + (this.start).substring(2,5))); }
        return (String.valueOf(hour) + (this.start).substring(2,5));
    }
 
    /**
     * Weight calculation should be performed here
     *
     * @return calculated weight
     */
    public double getWeight() {
        return (this.importance * (maellard ? 1001:1)) / ((double) this.duration);
    }
 
    /**
     * This method is needed to use {@link java.util.Arrays#sort(Object[])} ()}, which sorts the given array easily
     *
     * @param o Object to compare to
     * @return If self > object, return > 0 (e.g. 1)
     * If self == object, return 0
     * If self < object, return < 0 (e.g. -1)
     */
    @Override
    public int compareTo(Object o) {
        Assignment other = (Assignment) o;
        if (getFinishTime().compareTo(other.getFinishTime()) > 0){
            return 1;
        } else if (getFinishTime().compareTo(other.getFinishTime()) == 0) {
            return 0;
        } else if (getFinishTime().compareTo(other.getFinishTime()) < 0) {
            return -1;
        }
        return 0;
    }
 
    /**
     * @return Should return a string in the following form:
     * Assignment{name='Refill vending machines', start='12:00', duration=1, importance=45, maellard=false, finish='13:00', weight=45.0}
     */
    @Override
    public String toString() {
        return "Assignment{name='" + this.name + "', start='" + this.start + "', duration=" + this.duration + ", importance=" + this.importance + ", maellard=" + this.maellard + ", finish='" + this.getFinishTime() + "', weight=" + this.getWeight() + "}";
 
    }
}