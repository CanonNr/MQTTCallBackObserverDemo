package org.example.demo.entity;

import java.util.concurrent.ConcurrentHashMap;

public class Result extends ConcurrentHashMap<String, Result.Body> {
    public static class Body {

        public String cmdId;

        public int code;

        public String message;

        public Body(String cmdId,int code, String message) {
            this.cmdId = cmdId;
            this.code = code;
            this.message = message;
        }

        @Override
        public String toString() {
            return "Body{" +
                    "cmdId='" + cmdId + '\'' +
                    ", code=" + code +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}


