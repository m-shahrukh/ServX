package pl.servx.servx.Model;

public class User {
    private String Name;
    private String Email;
    public User(){
     Name= "";
     Email="";

    }
    public User(String name, String email){
        Name = name;
        Email = email;

    }

    public String getName() {
        return Name;
    }


    public String getEmail() {
        return Email;
    }

    public void setName(String name) {
        Name = name;
    }


    public void setEmail(String email){
        Email = email;
    }
}
