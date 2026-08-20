package com.app.model;

public class User {

    private String id;
    private String ref;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public User() {}

    public User(String id, String ref, String firstName,
                String lastName, String email, String phone) {
        this.id        = id;
        this.ref       = ref;
        this.firstName = firstName;
        this.lastName  = lastName;
        this.email     = email;
        this.phone     = phone;
    }

    // ---------- Getters / Setters ----------

    public String getId()                    { return id; }
    public void   setId(String id)           { this.id = id; }

    public String getRef()                   { return ref; }
    public void   setRef(String ref)         { this.ref = ref; }

    public String getFirstName()             { return firstName; }
    public void   setFirstName(String v)     { this.firstName = v; }

    public String getLastName()              { return lastName; }
    public void   setLastName(String v)      { this.lastName = v; }

    public String getEmail()                 { return email; }
    public void   setEmail(String email)     { this.email = email; }

    public String getPhone()                 { return phone; }
    public void   setPhone(String phone)     { this.phone = phone; }
}
