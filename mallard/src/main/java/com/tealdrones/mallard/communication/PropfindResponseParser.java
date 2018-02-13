package com.tealdrones.mallard.communication;

import com.tealdrones.mallard.model.RemoteFile;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * A SAX Parser to parse the PROPFIND response XML into a {@link List} of {@link RemoteFile}.
 */
public class PropfindResponseParser {
    public List<RemoteFile> parse(InputStream stream) throws IOException, SAXException, ParserConfigurationException {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            SAXParser parser = factory.newSAXParser();

            PropfindHandler handler = new PropfindHandler();

            parser.parse(stream, handler);

            return handler.getRemoteFiles();

        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw e;
        }
    }
}
