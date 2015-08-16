package ua.nure.hanzha.SummaryTask4.security;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ua.nure.hanzha.SummaryTask4.enums.Role;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 05/08/15.
 */
public class XmlAuthorizationMap implements AuthorizationMap {
    private static final String PATH = "path";
    private static final String NAME = "name";
    private static final String ACTION = "action";
    private static final String ROLE = "role";

    private Map<String, Set<Role>> authorizationMap = new HashMap<>();


    public XmlAuthorizationMap(File xml) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory theFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder theBuilder = theFactory.newDocumentBuilder();

        Document theDocument = theBuilder.parse(xml);

        Element theRoot = theDocument.getDocumentElement();

        NodeList actionList = theRoot.getElementsByTagName(ACTION);
        for (int i = 0; i < actionList.getLength(); i++) {
            parseAction(actionList.item(i));
        }
    }

    @Override
    public String isAuthorize(String path, Role role) {
        Set<Role> theRoleSet = authorizationMap.get(path);
        if (theRoleSet == null) {
            return AuthValue.NOT_FOUND;
        }
        return (theRoleSet.contains(role == null ? Role.GUEST : role) ? AuthValue.ALLOWED : AuthValue.UN_ALLOWED);
    }

    private void parseAction(Node action) {
        String thePath = ((Element) action).getAttribute(PATH);
        Set<Role> theRoleSet = authorizationMap.get(thePath);
        if (theRoleSet == null) {
            theRoleSet = new HashSet<Role>();
            authorizationMap.put(thePath, theRoleSet);
        }

        NodeList roleList = ((Element) action).getElementsByTagName(ROLE);
        for (int i = 0; i < roleList.getLength(); i++) {
            Element node = (Element) roleList.item(i);
            String theName = node.getAttribute(NAME).toUpperCase();
            Role theRole = Role.valueOf(theName);
            if (theRole != null) {
                theRoleSet.add(theRole);
            }
        }
    }

    public String toString() {
        return authorizationMap.toString();
    }
}
