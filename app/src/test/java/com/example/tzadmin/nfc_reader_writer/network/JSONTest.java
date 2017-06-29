package com.example.tzadmin.nfc_reader_writer.network;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JSONTest {
    @Test
    public void jsonTest() throws Exception {
        TestClass testClass = new TestClass("test", "asd");
        String json = JSON.toString(testClass);
        TestClass formJson = JSON.fromString(json, TestClass.class);
        assertEquals(testClass, formJson);
    }

    private static final class TestClass {
        private final String a;
        private final String b;

        TestClass(String a, String b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if(this == o) return true;
            if(!(o instanceof TestClass)) return false;

            TestClass testClass = (TestClass) o;

            if(a != null ? !a.equals(testClass.a) : testClass.a != null) return false;
            return b != null ? b.equals(testClass.b) : testClass.b == null;
        }

        @Override
        public int hashCode() {
            int result = a != null ? a.hashCode() : 0;
            result = 31 * result + (b != null ? b.hashCode() : 0);
            return result;
        }
    }
}