package com.mediafire.sdk.transcode.responses;

import com.mediafire.sdk.http.Response;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by jondh on 11/4/14.
 */
public class StatusResponseXml {
    private static final String NAME_TFTP_PROGRESS = "tftp_progress";
    private static final String NAME_VIDEO_WIDTH = "video_width";
    private static final String NAME_VIDEO_HEIGHT = "video_height";
    private static final String NAME_VIDEO_BITRATE = "video_bitrate";
    private static final String NAME_AUDIO_BITRATE = "audio_bitrate";
    private static final String NAME_DURATION = "duration";
    private static final String NAME_ORIGINAL_FILESIZE = "original_filesize";
    private static final String NAME_TRANSCODED_FILESIZE = "transcoded_filesize";
    private static final String NAME_STATUS = "status";
    private static final String NAME_SCHEMA_VERSION = "schema_version";

    public String tftp_progress = "0";
    public int video_width = 0;
    public int video_height = 0;
    public long video_bitrate = 0;
    public long audio_bitrate = 0;
    public int duration = 0;
    public long original_filesize = 0;
    public long transcoded_filesize = 0;
    public String status = "";
    public int schema_version = 0;

    private final Response mResponse;

    public StatusResponseXml(Response response) {
        this(response, ParseTypeXml.NONE);
    }

    public StatusResponseXml(Response response, ParseTypeXml parseType) {
        mResponse = response;

        switch (parseType) {
            case XML:
                parseXml();
                break;
        }
    }

    public void parseXml() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();

            Document document = documentBuilder.parse(new ByteArrayInputStream(mResponse.getBytes()));

            NodeList nodeList = document.getDocumentElement().getChildNodes();

            for(int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                String nodeName = node.getNodeName().trim();

                if(nodeName.equals(NAME_TFTP_PROGRESS)) {
                    tftp_progress = node.getTextContent();
                }
                else if(nodeName.equals(NAME_VIDEO_WIDTH)) {
                    video_width = Integer.parseInt(node.getTextContent());
                }
                else if(nodeName.equals(NAME_VIDEO_HEIGHT)) {
                    video_height = Integer.parseInt(node.getTextContent());
                }
                else if(nodeName.equals(NAME_VIDEO_BITRATE)) {
                    video_bitrate = Long.parseLong(node.getTextContent());
                }
                else if(nodeName.equals(NAME_AUDIO_BITRATE)) {
                    audio_bitrate = Long.parseLong(node.getTextContent());
                }
                else if(nodeName.equals(NAME_DURATION)) {
                    duration = Integer.parseInt(node.getTextContent());
                }
                else if(nodeName.equals(NAME_ORIGINAL_FILESIZE)) {
                    original_filesize = Long.parseLong(node.getTextContent());
                }
                else if(nodeName.equals(NAME_TRANSCODED_FILESIZE)) {
                    transcoded_filesize = Long.parseLong(node.getTextContent());
                }
                else if(nodeName.equals(NAME_STATUS)) {
                    status = node.getTextContent();
                }
                else if(nodeName.equals(NAME_SCHEMA_VERSION)) {
                    schema_version = Integer.parseInt(node.getTextContent());
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
