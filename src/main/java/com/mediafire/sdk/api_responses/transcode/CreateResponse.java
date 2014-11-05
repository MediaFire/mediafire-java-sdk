package com.mediafire.sdk.api_responses.transcode;

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
public class CreateResponse {
    private static final String NAME_STATE = "state";
    private static final String NAME_DURATION = "duration";

    public boolean state = false;
    public String duration = "0";

    private Response mResponse;

    public CreateResponse(Response response) {
        this(response, ParseType.NONE);
    }

    public CreateResponse(Response response, ParseType parseType) {
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

                if(nodeName.equals(NAME_STATE)) {
                    state = Boolean.parseBoolean( node.getTextContent() );
                }
                else if(nodeName.equals(NAME_DURATION)) {
                    duration = node.getTextContent();
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
