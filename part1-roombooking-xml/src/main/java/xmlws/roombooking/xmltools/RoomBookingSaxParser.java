package xmlws.roombooking.xmltools;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for objects responsible of RoomBooking xml files parsing
 * SAX version
 */
public class RoomBookingSaxParser implements RoomBookingParser {

    private static Logger logger = Logger.getLogger(RoomBookingSaxParser.class.getName());

    /**
     * Parse an xml file provided as an input stream
     *
     * @param inputStream the input stream corresponding to the xml file
     * @return the corresponding RoomBooking object
     */
    public RoomBooking parse(InputStream inputStream) {
        RoomBooking roomBooking = new RoomBooking();
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser saxParser = spf.newSAXParser();
            saxParser.parse(inputStream, new RoomBookingHandler(roomBooking));
        } catch (Exception e) {
            logger.severe(e.getMessage());
            roomBooking = null;
        }
        return roomBooking;
    }

    private class RoomBookingHandler extends DefaultHandler {
        private RoomBooking roomBooking;
        private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        private CurrentElement currentElement;

        public RoomBookingHandler(RoomBooking roomBooking) {
            this.roomBooking = roomBooking;
        }

        public void startElement(String namespaceURI,
                                 String localName,
                                 String qName,
                                 Attributes atts)
                throws SAXException {
            currentElement = CurrentElement.valueOf(localName);
        }

        public void characters(char ch[], int start, int length)
                throws SAXException {
            switch (currentElement) {
                case label:
                    logger.info("in label element");
                    String label = new String(ch, start, length);
                    roomBooking.setRoomLabel(label);
                    currentElement = CurrentElement.OTHER;
                    break;
                case username:
                    String username = new String(ch, start, length);
                    roomBooking.setUsername(username);
                    currentElement = CurrentElement.OTHER;
                    break;
                case startDate:
                    try {
                        String dateAsString = new String(ch, start, length);
                        roomBooking.setStartDate(sdf.parse(dateAsString));
                    } catch (ParseException e) {
                        logger.severe(e.getMessage());
                    }
                    currentElement = CurrentElement.OTHER;
                    break;
                case endDate:
                    try {
                        String dateAsString = new String(ch, start, length);
                        roomBooking.setEndDate(sdf.parse(dateAsString));
                    } catch (ParseException e) {
                        logger.severe(e.getMessage());
                    }
                    currentElement = CurrentElement.OTHER;
                    break;
                default:
                    currentElement = CurrentElement.OTHER;
            }
        }
    }

    private enum CurrentElement {
        label,
        username,
        startDate,
        endDate,
        roombooking,
        room,
        user,
        booking,
        OTHER
    }
}
