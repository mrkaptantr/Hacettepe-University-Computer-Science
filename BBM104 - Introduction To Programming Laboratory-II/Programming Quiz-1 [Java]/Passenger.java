public class Passenger {
	String Name;
    String SurName;
    Gender Gender;
    Phone Phone;

    public Passenger(String name, String surName, Gender gender, Phone phone) {
        this.Name = name;
        this.SurName = surName;
        this.Gender = gender;
        this.Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getSurName() {
        return SurName;
    }

    public void setSurName(String surName) {
        this.SurName = surName;
    }

    public Gender getGender() {
        return Gender;
    }

    public void setGender(Gender gender) {
        this.Gender = gender;
    }

    public Phone getPhone() {
        return Phone;
    }

    public void setPhone(Phone phone) {
        this.Phone = phone;
    }

    public void Display() {
        switch (this.Gender) {
            case MALE:
                System.out.println(this.Name + " " + this.SurName + " (E)");             
                break;
            case FEMALE:
                System.out.println(this.Name + " " + this.SurName + " (K)");
                break;
        }
           this.Phone.Display();
    }
}
