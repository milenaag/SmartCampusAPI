/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.api.resources;

import com.smartcampus.api.model.Sensor;
import com.smartcampus.api.model.SensorReading;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.smartcampus.api.exception.SensorUnavailableException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Produces(MediaType.APPLICATION_JSON)
/**
 *
 * @author milenagalhardo
 */
public class SensorReadingResource {
    

    public static Map<String, List<SensorReading>> readings = new HashMap<>();
    private String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    public Response getReadings() {
        List<SensorReading> sensorReadings = readings.get(sensorId);

        if (sensorReadings == null) {
            sensorReadings = new ArrayList<>();
        }

        return Response.ok(sensorReadings).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addReading(SensorReading reading) {
        Sensor sensor = SensorResource.sensors.get(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }

       if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
    throw new SensorUnavailableException("Sensor is in maintenance and cannot accept readings");
}

        List<SensorReading> sensorReadings = readings.get(sensorId);
        if (sensorReadings == null) {
            sensorReadings = new ArrayList<>();
            readings.put(sensorId, sensorReadings);
        }

        sensorReadings.add(reading);
        sensor.setCurrentValue(reading.getValue());

        return Response.status(Response.Status.CREATED)
                .entity(reading)
                .build();
    }
}
