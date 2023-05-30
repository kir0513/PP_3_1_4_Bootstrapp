package com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model;

import jakarta.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;


    @Entity
    @Table(name = "users231")
    public class User {
        //
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private int id;

        @Column(name = "first_name")
        @NotEmpty(message = "First_Name should not be empty")
        @Size(min = 2, max = 30, message = "First_Name should be between 2 and 30 characters")
        private String firstName;

        @Column(name = "last_name")
        @NotEmpty(message = "Last_Name should not be empty")
        @Size(min = 2, max = 30, message = "last_Name should be between 2 and 30 characters")
        private String lastName;

        @Column(name = "age")
        @Min(value = 0, message = "Age should be greater than 0")
        @NotEmpty(message = "Age should be greater than 0")
        private int age;

        @NotEmpty(message = "Email should not be empty")
        @Email(message = "Email should be valid")
        @Column(name = "email")
        private String email;

        public User() {
        }

        public User(int id, String firstName, String lastName, int age, String email) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.email = email;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", age=" + age +
                    ", email='" + email + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return getId() == user.getId() && getAge() == user.getAge() && Objects.equals(getFirstName(), user.getFirstName()) && Objects.equals(getLastName(), user.getLastName()) && Objects.equals(getEmail(), user.getEmail());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getFirstName(), getLastName(), getAge(), getEmail());
        }

}
