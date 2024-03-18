package com.atlassian.tutorial.ao.todo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Path("/my-rest-api")
public class ProjectAPI {

    @GET
    @Path("/get-project-info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectInfo() {
        try {
            String projectKey = "TEST";
//{
//   "fields": {"projectId":   "%s" { "id": "10102"  // Đảm bảo rằng giá trị này phù hợp với ID dự án của bạn
//        // }
//        ,
//        "summary": "something's wrong",
//        "issuetypeId": "10002"
//        //  {
//        //     "id": "10002"  // Đã thay đổi từ "10000" sang "10002" để phù hợp với ID của "Task"
//        // }
//        ,
//        "labels": [
//            "bugfix",
//            "blitz_test"
//        ],
//        //   "timetracking": {
//        //     "originalEstimate": "10",
//        //     "remainingEstimate": "5"
//        // },
//        "description": "description",
//        "duedate": "2011-03-11"
//    }
//}
            URL url = new URL("http://localhost:8080/rest/api/2/project/" + projectKey);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "SGUxNTM2NzRA");
            conn.setRequestProperty("Content-Type", "application/json");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                return Response.ok(response.toString()).build();
            } else {
                return Response.serverError().build();
            }
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}
