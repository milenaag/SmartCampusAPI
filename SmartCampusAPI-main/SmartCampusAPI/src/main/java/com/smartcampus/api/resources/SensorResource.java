package com.smartcampus.api.resources;

import com.smartcampus.api.model.Room;
import com.smartcampus.api.model.Sensor;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.smartcampus.api.exception.LinkedResourceNotFoundException;
import jakarta.ws.rs.PathParam;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
public class SensorResource {

    public static Map<String, Sensor> sensors = new HashMap<>();

    @GET
    public Response getAllSensors(@QueryParam("type") String type) {
        if (type == null || type.trim().isEmpty()) {
            return Response.ok(sensors.values()).build();
        }

        List<Sensor> filteredSensors = new ArrayList<>();

        for (Sensor sensor : sensors.values()) {
            if (sensor.getType() != null && sensor.getType().equalsIgnoreCase(type)) {
                filteredSensors.add(sensor);
            }
        }

        return Response.ok(filteredSensors).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSensor(Sensor sensor) {
        if (sensor == null || sensor.getId() == null || sensor.getId().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Sensor ID is required")
                    .build();
        }

        if (sensor.getRoomId() == null || sensor.getRoomId().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("roomId is required")
                    .build();
        }

        if (sensors.containsKey(sensor.getId())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Sensor with this ID already exists")
                    .build();
        }

        Room room = RoomResource.rooms.get(sensor.getRoomId());

        if (room == null) {
    throw new LinkedResourceNotFoundException("Referenced room does not exist");
}

        sensors.put(sensor.getId(), sensor);
        room.getSensorIds().add(sensor.getId());

        return Response.status(Response.Status.CREATED)
                .entity(sensor)
                .build();
    }
    @GET
@Path("/{sensorId}")
public Response getSensorById(@PathParam("sensorId") String sensorId) {
    Sensor sensor = sensors.get(sensorId);

    if (sensor == null) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Sensor not found")
                .build();
    }

    return Response.ok(sensor).build();
}

@Path("/{sensorId}/readings")
public SensorReadingResource getSensorReadingResource(@PathParam("sensorId") String sensorId) {
    return new SensorReadingResource(sensorId);
}
}