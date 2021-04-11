package ddwucom.mobile.finalproject.ma01_20170922;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class SpotXmlParsher {
    public enum TagType { NONE, TITLE, ADDRRESS, MAPX, MAPY, IMAGE };

    final static String TAG_ITEM = "item";
    final static String TAG_TITLE = "title";
    final static String TAG_ADDRESS = "addr1";
    final static String TAG_MAPX = "mapx";
    final static String TAG_MAPY = "mapy";
    final static String TAG_IMAGE = "firstimage";

    public SpotXmlParsher() {
    }

    public ArrayList<SpotDto> parse(String xml) {

        ArrayList<SpotDto> resultList = new ArrayList();
        SpotDto dto = null;

        TagType tagType = TagType.NONE;

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xml));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals(TAG_ITEM)) {
                            dto = new SpotDto();
                        } else if (parser.getName().equals(TAG_TITLE)) {
                            if (dto != null) tagType = TagType.TITLE;
                        } else if (parser.getName().equals(TAG_ADDRESS)) {
                            if (dto != null) tagType = TagType.ADDRRESS;
                        } else if (parser.getName().equals(TAG_MAPX)) {
                            if (dto != null) tagType = TagType.MAPX;
                        } else if (parser.getName().equals(TAG_MAPY)) {
                            if (dto != null) tagType = TagType.MAPY;
                        } else if (parser.getName().equals(TAG_IMAGE)) {
                            if (dto != null) tagType = TagType.IMAGE;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals(TAG_ITEM)) {
                            resultList.add(dto);
                            dto = null;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        switch(tagType) {
                            case TITLE:
                                dto.setTitle(parser.getText());
                                break;
                            case ADDRRESS:
                                dto.setAddress(parser.getText());
                                break;
                            case MAPX:
                                dto.setMapx(parser.getText());
                                break;
                            case MAPY:
                                dto.setMapy(parser.getText());
                                break;
                            case IMAGE:
                                dto.setImageLink(parser.getText());
                                break;
                        }
                        tagType = TagType.NONE;
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }
}
