package OOD;

public class UserBuilderPattern {

    private final String firstName;
    private final String lastName;
    private final int age;
    private String address;
    private String phone;

    private UserBuilderPattern(UserBuilder builder){
        //make this constructor private so we must pass in a UserBuilder object to set the fields here
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.address = builder.address;
        this.phone = builder.phone;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public int getAge(){
        return age;
    }

    public String getPhone(){
        return phone;
    }

    public String getAddress(){
        return address;
    }

    public static class UserBuilder{ // why static?
        private String firstName;
        private String lastName;
        private int age = 0;
        private String phone = "";
        private String address = "";

        public UserBuilder setFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public UserBuilder setLastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public UserBuilder setAge(int age){
            this.age = age;
            return this;
        }

        public UserBuilder setPhone(String phone){
            this.phone = phone;
            return this;
        }

        public UserBuilder setAddress(String address){
            this.address = address;
            return this;
        }

        public UserBuilderPattern build(){
            if (firstName == null || lastName == null){
                throw new IllegalArgumentException("Invalid firstname or lastname");
            }
            return new UserBuilderPattern(this);
        }
    }

    public static void main(String[] args){
        UserBuilderPattern obj = new UserBuilderPattern.UserBuilder().setFirstName("WS")
                .setLastName("H")
                .setAge(2)
                .setPhone("202")
                .setAddress("Central Ave")
                .build();

        System.out.println(obj.getFirstName());
    }
}
