package jchess.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;

import static org.junit.Assert.*;

public class JsonTest {

    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    @Test
    public void demonstrateSerializing() throws Exception {
        Person p = new Person("LeLouche", 21);
        String s = Json.getGenson().serialize(p);
        /*
        {
            "age":21,
            "experience":0.0,
            "name":"LeLouche"
        }
         */

        Person clone = Json.getGenson().deserialize(s, Person.class);
        assertEquals(p, clone);

        p.setExperience(1.0);
        assertNotEquals(p, clone);
    }

    @Test
    public void testSerializeToFile() throws Exception {
        File destFile = tmpFolder.newFile();

        Person p = new Person("LeLouche", 21);
        Json.serializeToFile(destFile, p);
        assertTrue(destFile.length() > 0L);
    }

    @Test
    public void testDeserializeToFile() throws Exception {
        File destFile = tmpFolder.newFile();
        String content= "" +
                "{\n" +
                "    \"age\":21,\n" +
                "    \"experience\":0.0,\n" +
                "    \"name\":\"LeLouche\"\n" +
                "}" +
                "";
        try(BufferedWriter bWriter = Files.newBufferedWriter(destFile.toPath())) {
            bWriter.write(content);
        }

        Person p = new Person("LeLouche", 21);
        Person clone = Json.deserializeFromFile(destFile, Person.class);
        assertEquals(p, clone);
    }

    // Sample data-class. Inner-class must be static, otherwise the constructor is not found.
    private static class Person {
        private final String name;
        private final int age;
        private double experience;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public void setExperience(double experience) {
            this.experience = experience;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Person person = (Person) o;

            return age == person.age &&
                    Double.compare(person.experience, experience) == 0
                    && !(name != null ? !name.equals(person.name) : person.name != null);

        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = name != null ? name.hashCode() : 0;
            result = 31 * result + age;
            temp = Double.doubleToLongBits(experience);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }
    }
}