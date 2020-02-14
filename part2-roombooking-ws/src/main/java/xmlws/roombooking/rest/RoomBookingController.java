package xmlws.roombooking.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xmlws.roombooking.ws.RoomBookingService;
import xmlws.roombooking.xmltools.RoomBooking;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class RoomBookingController {

    @Autowired
    private RoomBookingService roomBookingService;

    @RequestMapping("/roomBookingList")
    public HttpEntity<RoomBookingListResource> getRoomBookingListForRoomLabel(
            @RequestParam(value = "roomLabel", required = false, defaultValue = "B501") String roomLabel) {

        List<RoomBooking> roomBookingList = roomBookingService.findAllRoomBookingsForRoom(roomLabel);
        RoomBookingListResource roomBookingListResource = new RoomBookingListResource(roomBookingList);
        roomBookingListResource.add(linkTo(methodOn(RoomBookingController.class).getRoomBookingListForRoomLabel(roomLabel)).withSelfRel());

        return new ResponseEntity<>(roomBookingListResource, HttpStatus.OK);
    }

}
