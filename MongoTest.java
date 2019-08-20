package com.cstary.currency;


import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MongoTest {
    public static JSONArray makedata() {
        List<Document> data = new ArrayList<>();
        data.add(new Document("PID", "ID1").append("PNAME", "PN1")
                .append("PATH", Arrays.asList(
                        new Document("RS", "DEFAULT").append("GID", "G1").append("GNAME", "GN1"),
                        new Document("RS", "DEFAULT").append("GID", "G2").append("GNAME", "GN2"),
                        new Document("RS", "DEFAULT").append("GID", "G3").append("GNAME", "GN3")))
                .append("ENID", Arrays.asList("EID1", "EID2", "EID3"))
                .append("IN_ENID", Arrays.asList("IEID1", "IEID2", "IEID3"))
        );
        data.add(new Document("PID", "ID1").append("PNAME", "PN1")
                .append("PATH", Arrays.asList(
                        new Document("RS", "DEFAULT").append("GID", "G11").append("GNAME", "GN11"),
                        new Document("RS", "DEFAULT").append("GID", "G2").append("GNAME", "GN2"),
                        new Document("RS", "DEFAULT").append("GID", "G31").append("GNAME", "GN31")))
                .append("ENID", Arrays.asList("EID1", "EID2", "EID3"))
                .append("IN_ENID", Arrays.asList("IEID1", "IEID2", "IEID3"))
        );
        data.add(new Document("PID", "ID1").append("PNAME", "PN1")
                .append("PATH", Arrays.asList(
                        new Document("RS", "DEFAULT").append("GID", "G0").append("GNAME", "GN0"),
                        new Document("RS", "DEFAULT").append("GID", "G1").append("GNAME", "GN1"),
                        new Document("RS", "DEFAULT").append("GID", "G2").append("GNAME", "GN2"),
                        new Document("RS", "DEFAULT").append("GID", "G33").append("GNAME", "GN33")))
                .append("ENID", Arrays.asList("EID1", "EID2", "EID3"))
                .append("IN_ENID", Arrays.asList("IEID1", "IEID2", "IEID3"))
        );
        data.add(new Document("PID", "ID1").append("PNAME", "PN1")
                .append("PATH", Arrays.asList(
                        new Document("RS", "DEFAULT").append("GID", "G2").append("GNAME", "GN2"),
                        new Document("RS", "DEFAULT").append("GID", "G33").append("GNAME", "GN33"),
                        new Document("RS", "DEFAULT").append("GID", "G4").append("GNAME", "GN4")))
                .append("ENID", Arrays.asList("EID1", "EID2", "EID4"))
                .append("IN_ENID", Arrays.asList("IEID1", "IEID2", "IEID4"))
        );

        JSONArray jsonarr = new JSONArray();
        String gid = "G2";
        JSONObject json = new JSONObject();
        for (Document d : data) {
            JSONObject jtmp = new JSONObject(d.toJson());
            JSONArray path = (JSONArray) jtmp.get("PATH");
            JSONArray in_enid = (JSONArray) jtmp.get("IN_ENID");
            JSONObject front_path = new JSONObject();
            JSONObject post_path = new JSONObject();
            for (int i = 0; i < path.length(); i++) {
                JSONObject tmp = path.getJSONObject(i);
                if (tmp.getString("GID").equals(gid)) {
                    System.out.println(front_path);
                    break;
                } else if (front_path.length() != 0) {
                    JSONArray arr = new JSONArray();
                    tmp.put("parents", arr.put(front_path));
                    tmp.put("type","GROUP");
                    front_path = tmp;
                } else {
                    JSONObject tmp2=new JSONObject();
                    tmp2.put("IN_ENID",in_enid).put("type","IN_ENID");
                    tmp.put("parents",tmp2);
                    tmp.put("type","ROOT_GROUP");
                    front_path = tmp;
                }
            }
        }
        return jsonarr;
    }

    public static void main(String[] args) {
        makedata();

    }


}
