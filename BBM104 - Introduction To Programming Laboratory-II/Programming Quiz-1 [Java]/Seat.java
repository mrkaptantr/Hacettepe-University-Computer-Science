public class Seat {
    int SeatID;
    boolean Status;
    Passenger Passenger;

    public Seat(int seatId, boolean status, Passenger passenger) {
        this.SeatID = seatId;
        this.Status = status;
        this.Passenger = passenger;
    }

    public int getSeatID() {
        return SeatID;
    }

    public void setSeatID(int seatID) {
        this.SeatID = seatID;
    }

    public boolean getStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        this.Status = status;
    }

    public Passenger getPassenger() {
        return Passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.Passenger = passenger;
    }
    
    public void Display(){
        System.out.println("Seat: " + this.SeatID + " Status: " + (this.Status ? "Reserved" : "Not Reserved"));
        this.Passenger.Display();
    }    
}
