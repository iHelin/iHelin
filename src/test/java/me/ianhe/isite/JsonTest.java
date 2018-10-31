package me.ianhe.isite;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.MoreObjects;

import java.io.IOException;

/**
 * @author iHelin
 * @since 2018/4/27 21:32
 */
public class JsonTest {

    public static void main(String args[]) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "{\"name\":\"Mahesh\", \"age\":21}";
        //map json to student
        try {
            Student student = mapper.readValue(jsonString, Student.class);
            System.out.println(student);
//            mapper.configure(MapperFeature.ALLOW_COERCION_OF_SCALARS,true);
//            mapper.enable(new SerializationConfig());
            jsonString = mapper.writeValueAsString(student);
            System.out.println(jsonString);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Student {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("name", name)
                    .add("age", age)
                    .toString();
        }
    }
}
