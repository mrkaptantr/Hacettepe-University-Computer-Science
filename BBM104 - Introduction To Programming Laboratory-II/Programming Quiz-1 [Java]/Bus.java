public class Bus {

    String Plate;
    int SeatCount;
    Seat[] Seats;
    
    public Bus(String plate, int seatCount) {
        this.Plate = plate;
        this.SeatCount = seatCount;
        this.Seats = new Seat[seatCount];
    }

    public String getPlate() {
        return Plate;
    }

    public void setPlate(String Plate) {
        this.Plate = Plate;
    }

    public int getSeatCount() {
        return SeatCount;
    }

    public void setSeatCount(int SeatCount) {
        this.SeatCount = SeatCount;
    }

    public Seat[] getSeats() {
        return Seats;
    }

    public void setSeats(Seat[] Seats) {
        this.Seats = Seats;
    }

    public void bookSeat(Seat seat, int seatId) {
        if (seatId > SeatCount) {
            System.out.println("Invalid seat id!");
            return;
        }
	if (seatId < 1){
            System.out.println("Invalid seat id!");
            return;
	}
        if (this.Seats[seatId - 1] != null && this.Seats[seatId - 1].getStatus()) {
            System.out.println("This seat has already reserved!");
            return;
        }
        this.Seats[seatId - 1] = seat;
        System.out.println("Successfully reserved!");
    }

    public void cancelBooking(int seatId) {
        if (seatId > SeatCount) {
            System.out.println("Invalid seat id!");
            return;
        }
        if (this.Seats[seatId - 1] != null && !this.Seats[seatId - 1].getStatus()) {
            System.out.println("This seat has already not reserved!");
            return;
        }
        this.Seats[seatId - 1].setStatus(false);
        System.out.println("Successfully canceled!");
    }

    public void printAllPaasengers() {
        System.out.println("ALL PASSENGERS:");
        for (int i = 0; i < this.SeatCount; i++) {
            if (this.Seats[i] != null && this.Seats[i].getStatus()) {
                this.Seats[i].Display();
            }
        }
    }

    public void printAllSeats() {
        System.out.println("ALL SEATS:");
        for (int i = 0; i < this.SeatCount; i++) {
            if (this.Seats[i] != null && this.Seats[i].getStatus()) {
                this.Seats[i].Display();
            } else {
                System.out.println("Seat: " + (i + 1) + " Status: Not reserved");
            }
        }
    }

    public void printAllAvailableSeatIDs() {
        System.out.println("ALL AVAILABLE SEAT IDs:");
        for (int i = 0; i < this.SeatCount; i++) {
            if (this.Seats[i] == null || !this.Seats[i].getStatus()) {
               System.out.println("Seat: " + (i + 1) + " Status: Not reserved");
            }
        }
    }

    public void search(String name, String surName) {
        for (int i = 0; i < this.SeatCount; i++) {
            if (this.Seats[i] != null && this.Seats[i].getStatus()) {
                var seatPassenger = this.Seats[i].getPassenger();
                if ((seatPassenger.getName().equals(name))
                        && seatPassenger.getSurName().equals(surName)) {
                    seatPassenger.Display();
                    return;
                }
            }
        }
        System.out.println("Passenger not found!");
    }
}

