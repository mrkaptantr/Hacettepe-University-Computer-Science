public class Phone {

    String CountryCode;
    String Code;
    String Number;
    PhoneType Type;

    public Phone(String countryCode, String code, String number, PhoneType type) {
        this.CountryCode = countryCode;
        this.Code = code;
        this.Number = number;
        this.Type = type;
    }

    public Phone(String code, String number, PhoneType type) {
        this.CountryCode = "+90";
        this.Code = code;
        this.Number = number;
        this.Type = type;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        this.CountryCode = countryCode;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        this.Code = code;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        this.Number = number;
    }

    public PhoneType getType() {
        return Type;
    }

    public void setType(PhoneType type) {
        this.Type = type;
    }

    public void Display() {
        switch (Type) {
            case MOBILE:
                System.out.println("MOBİLE phone: " + this.Code + " " + this.CountryCode + " " + this.Number);
                break;
            case OFFICE:
                System.out.println("OFFİCE phone: " + this.Code + " " + this.CountryCode + " " + this.Number);
                break;
            case HOME:
                System.out.println("HOME phone: " + this.Code + " " + this.CountryCode + " " + this.Number);
                break;
        }
    }
	
}