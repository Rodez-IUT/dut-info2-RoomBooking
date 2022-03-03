package xmlws.roombooking.xmltools;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

/**
 *  Class for objects responsible of RoomBooking xml files parsing
 *  DOM version
 */
public class RoomBookingDomParser implements RoomBookingParser {

    private static Logger logger = Logger.getLogger(RoomBookingDomParser.class.getName());

    /**
     * Parse an xml file provided as an input stream
     * @param inputStream the input stream corresponding to the xml file
     * @return the corresponding RoomBooking object
     */
    public RoomBooking parse(InputStream inputStream) {
        RoomBooking roomBooking = new RoomBooking();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(inputStream);
            roomBooking.setRoomLabel(getValueForElementInDocument(Element.label,doc));
            roomBooking.setUsername(getValueForElementInDocument(Element.username,doc));
            roomBooking.setStartDate(sdf.parse(getValueForElementInDocument(Element.startDate,doc)));
            roomBooking.setEndDate(sdf.parse(getValueForElementInDocument(Element.endDate,doc)));
        } catch (Exception e) {
            logger.severe(e.getMessage());
            roomBooking = null;
        }
        return roomBooking;
    }

    private String getValueForElementInDocument(Element element, Document doc) {
        return doc.getElementsByTagName(element.name()).item(0).getTextContent();
    }
    private enum Element {
        label,
        username,
        startDate,
        endDate
    }
}
