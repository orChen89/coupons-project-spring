package com.or;

import com.or.model.Cat;
import com.or.model.Person;
import com.or.model.util.JsonUtill;

public class App {
    public static void main(String[] args) {

        Person person = new Person("Or", 33, new Cat("Yossi",25.5 ));
        System.out.println(JsonUtill.serialize(person));

    }
}
