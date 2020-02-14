package xmlws.roombooking.rest;

import org.springframework.hateoas.ResourceSupport;
import xmlws.roombooking.xmltools.RoomBooking;

import java.util.List;

public class RoomBookingListResource extends ResourceSupport {

    private List<RoomBooking> roomBookingList;

    public RoomBookingListResource(List<RoomBooking> roomBookingList) {
        this.roomBookingList = roomBookingList;
    }

    public List<RoomBooking> getRoomBookingList() {
        return roomBookingList;
    }
}
