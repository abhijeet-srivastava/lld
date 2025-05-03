package com.test.assignments.coding_probs;

public class StringManipulations {
    public static void main(String[] args) {
        StringManipulations sm = new StringManipulations();
        sm.multiLineString();
    }


    public void multiLineString() {
        String res = String.join("\n",
                "Hi this is",
                " A multi line String example");
        String sql = """
                UPDATE "public"."office"
                SET ("address_first", "address_second", "phone") =
                (SELECT "public"."employee"."first_name",
                                     "public"."employee"."last_name", ?"
                FROM "public"."employee"
                WHERE "public"."employee"."job_title" = ?""";
        System.out.printf("%s\n", res);
        String json = """
              {
                "widget": {
                  "debug": "on",
                  "window": {
                    "title": "Sample Widget 1",
                    "name": "back_window"
                  },
                  "image": {
                    "src": "images\\sw.png"
                    }, 
                    "text": {
                        "data": "Click Me",
                    "size": 39 
                    }
                    }
              }""";
        System.out.printf("%s\n", json);
        String fn = "Abhijeet", ln = "Srivastva";
        String xml = """
            <user>
               <firstName>\
            """
                + fn + """
             </firstName>
                <lastName>\
"""
                + ln + """
             </lastName>
             </user>
             """;
        System.out.printf("%s\n", xml);


    }
}
