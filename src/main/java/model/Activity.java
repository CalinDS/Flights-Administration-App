package model;

// clasa pentru a reprezenta elementele din tabela activities
public class Activity {

    private int id;
    private String activity;
    private String time;
    private String username;
    private String mail;

    public Activity(int id, String activity, String time, String username, String mail) {
        this.id = id;
        this.activity = activity;
        this.time = time;
        this.username = username;
        this.mail = mail;
    }

    public Activity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", activity='" + activity + '\'' +
                ", time='" + time + '\'' +
                ", username='" + username + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
