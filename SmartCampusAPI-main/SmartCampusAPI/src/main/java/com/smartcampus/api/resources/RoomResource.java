/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.api.resources;
import com.smartcampus.api.model.Room;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.DELETE;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.smartcampus.api.exception.RoomNotEmptyException;

/**
 *
 * @author milenagalhardo
 */
@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class RoomResource {

    public static Map<String, Room> rooms = new HashMap<>();

    @GET
    public Collection<Room> getAllRooms() {
        return rooms.values();
    }

    @GET
    @Path("/{roomId}")
    public Response getRoomById(@PathParam("roomId") String roomId) {
        Room room = rooms.get(roomId);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found")
                    .build();
        }

        return Response.ok(room).build();
    }

    @POST
    public Response createRoom(Room room) {
        if (room == null || room.getId() == null || room.getId().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Room ID is required")
                    .build();
        }

        rooms.put(room.getId(), room);

        return Response.status(Response.Status.CREATED)
                .entity(room)
                .build();
    }
    
   @DELETE
@Path("/{roomId}")
public Response deleteRoom(@PathParam("roomId") String roomId) {
    Room room = rooms.get(roomId);

    if (room == null) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Room not found")
                .build();
    }

    if (room.getSensorIds() != null && !room.getSensorIds().isEmpty()) {
        throw new RoomNotEmptyException("Room cannot be deleted because sensors are still assigned to it");
    }

    rooms.remove(roomId);

    return Response.ok("Room deleted successfully").build();
}
}