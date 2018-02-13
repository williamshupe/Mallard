package com.tealdrones.mallard.communication;

import com.tealdrones.mallard.model.Property;
import com.tealdrones.mallard.model.RemoteFile;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * An Implementation of a {@link DefaultHandler} for a {@link PropfindRequest}.
 * Parses a PROPFIND response XML into a list of {@link RemoteFile}.
 */
public class PropfindHandler extends DefaultHandler {
    protected static final String NAMESPACE_DAV = "DAV:";
    protected static final String LOCAL_NAME_MULTISTATUS = "multistatus";
    protected static final String LOCAL_NAME_RESPONSE = "response";
    protected static final String LOCAL_NAME_PROP = "prop";
    protected static final String LOCAL_NAME_PROPSTAT = "propstat";
    protected static final String LOCAL_NAME_STATUS = "status";
    protected static final String LOCAL_NAME_HREF = "href";

    protected List<RemoteFile> remoteFiles;
    protected RemoteFile.Builder remoteFileBuilder;
    protected boolean isInRemoteFileBlock;
    protected boolean isInPropstatBlock;
    protected boolean isInPropBlock;
    protected boolean isInHrefBlock;
    protected boolean isInStatusBlock;
    protected Stack<Property.Builder> propertyBuilderStack;
    protected List<Property> properties;


    public List<RemoteFile> getRemoteFiles() {
        return remoteFiles;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (NAMESPACE_DAV.equals(uri) && LOCAL_NAME_MULTISTATUS.equals(localName)) {
            remoteFiles = new ArrayList<>();
        } else if (NAMESPACE_DAV.equals(uri) && LOCAL_NAME_RESPONSE.equals(localName)) {
            remoteFileBuilder = new RemoteFile.Builder();
            isInRemoteFileBlock = true;
        } else if (isInPropBlock) {
            Property.Builder propertyBuilder = new Property.Builder()
                    .setNamespace(uri)
                    .setName(localName);
            propertyBuilderStack.push(propertyBuilder);
        } else if (isInPropstatBlock && NAMESPACE_DAV.equals(uri) && LOCAL_NAME_PROP.equals(localName)) {
            propertyBuilderStack = new Stack<>();
            properties = new ArrayList<>();
            isInPropBlock = true;
        } else if (isInRemoteFileBlock && NAMESPACE_DAV.equals(uri) && LOCAL_NAME_PROPSTAT.equals(localName)) {
            isInPropstatBlock = true;
        } else if (isInPropstatBlock && NAMESPACE_DAV.equals(uri) && LOCAL_NAME_STATUS.equals(localName)) {
            isInStatusBlock = true;
        } else if (isInRemoteFileBlock && NAMESPACE_DAV.equals(uri) && LOCAL_NAME_HREF.equals(localName)) {
            isInHrefBlock = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (isInPropBlock) {
            if (NAMESPACE_DAV.equals(uri) && LOCAL_NAME_PROP.equals(localName) && propertyBuilderStack.size() == 0) {
                remoteFileBuilder.addProperties(properties);
                isInPropBlock = false;
                propertyBuilderStack = null;
                properties = null;
            } else {
                Property property = propertyBuilderStack.pop().build();

                if (propertyBuilderStack.size() > 0) {
                    propertyBuilderStack.peek().addSubProperty(property);
                } else {
                    properties.add(property);
                }
            }
        } else if (isInStatusBlock) {
            isInStatusBlock = false;
        } else if (isInHrefBlock) {
            isInHrefBlock = false;
        } else if (isInPropstatBlock && NAMESPACE_DAV.equals(uri) && LOCAL_NAME_PROPSTAT.equals(localName)) {
            isInPropstatBlock = false;
        } else if (isInRemoteFileBlock && NAMESPACE_DAV.equals(uri) && LOCAL_NAME_RESPONSE.equals(localName)) {
            RemoteFile remoteFile = remoteFileBuilder.build();
            remoteFiles.add(remoteFile);
            remoteFileBuilder = null;
            isInRemoteFileBlock = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (isInPropBlock) {
            if (propertyBuilderStack.size() > 0) {
                propertyBuilderStack.peek().setText(new String(ch, start, length));
            }
        } else if (isInHrefBlock) {
            remoteFileBuilder.setHref(new String(ch, start, length));
        } else if (isInStatusBlock) {
            remoteFileBuilder.setStatus(new String(ch, start, length));
        }
    }
}
