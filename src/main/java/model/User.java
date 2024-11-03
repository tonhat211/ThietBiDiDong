package model;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String[] roles;
    private Branch branch;
    private String info;
    private Image image;
    private int avai;

    public User() {
    }

    public User(int id, String name, String email, String password, String[] roles, Branch branch, String info, Image image, int avai) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.branch = branch;
        this.info = info;
        this.image = image;
        this.avai = avai;
    }

    public User(int id) {
        this.id = id;
    }

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User(String name, String email, String password,int avai) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.avai = avai;
    }


    // dung trong image
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getAvai() {
        return avai;
    }

    public void setAvai(int avai) {
        this.avai = avai;
    }

    public static String encodePwd(String pwd) {
        String code ="";
        for(int i=pwd.length() - 1 ; i>=0;i--){
            code+= (int) pwd.charAt(i);
        }

        return code;
    }
}