package itg8.com.labtestingapp.splash.mvvm;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import itg8.com.labtestingapp.db.tables.City;
import itg8.com.labtestingapp.db.tables.State;

public class StateCityController {

    private Context context;
    private String response;

    public StateCityController(Context context) {
        this.context = context;
    }

    public StateCityController(String response) {
        this.response = response;
    }

    public List<State> getStateOnline() throws JSONException {
        List<State> states=new ArrayList<>();
        JSONArray jsonArray=new JSONArray(response);
        for(int i=0; i<jsonArray.length(); i++){
            states.add(getStateByJsonObj(jsonArray.getJSONObject(i)));
        }
        return states;
    }
    public List<City> getCityOnline() throws JSONException {
        List<City> states=new ArrayList<>();
        JSONArray jsonArray=new JSONArray(response);
        for(int i=0; i<jsonArray.length(); i++){
            states.add(getCityByJsonObj(jsonArray.getJSONObject(i)));
        }
        return states;
    }

    private State getStateByJsonObj(JSONObject jsonObject) throws JSONException {
        State state=new State();
        if(jsonObject.has("Statemaster")){
            JSONObject stateJson=jsonObject.getJSONObject("Statemaster");
            state.setId(Integer.parseInt(stateJson.getString("id")));
            state.setName(stateJson.getString("name"));
            state.setCountryid(Integer.parseInt(stateJson.getString("countrymaster_id")));
        }
        return state;
    }
    private City getCityByJsonObj(JSONObject jsonObject) throws JSONException {
        City state=new City();
        if(jsonObject.has("Citymaster")){
            JSONObject stateJson=jsonObject.getJSONObject("Citymaster");
            state.setId(Integer.parseInt(stateJson.getString("id")));
            state.setName(stateJson.getString("name"));
            state.setStateid(Integer.parseInt(stateJson.getString("statemaster_id")));
        }
        return state;
    }


    public List<City> getCities() throws IOException, ParserConfigurationException, SAXException {

        List<City> cities = new ArrayList<>();

        InputStream istream = context.getAssets().open("citymasters.xml");
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(istream);
        NodeList node = doc.getElementsByTagName("database");
        Element e = (Element) node.item(0);
        NodeList nList = e.getElementsByTagName("table");
        for (int i = 0; i < nList.getLength(); i++) {
            if (nList.item(0).getNodeType() == Node.ELEMENT_NODE) {
                HashMap<String, String> user = new HashMap<>();
                Element elm = (Element) nList.item(i);
                NodeList nNodeList = elm.getElementsByTagName("column");
                City city = new City();
                for (int j = 0; j < nNodeList.getLength(); j++) {
                    Element col = (Element) nNodeList.item(j);
                    if (col.hasAttribute("name")) {
                        if (col.getAttribute("name").equalsIgnoreCase("id"))
                            city.setId(Integer.parseInt(col.getChildNodes().item(0).getNodeValue()));
                        else if (col.getAttribute("name").equalsIgnoreCase("name"))
                            city.setName(col.getChildNodes().item(0).getNodeValue());
                        else if (col.getAttribute("name").equalsIgnoreCase("statemaster_id"))
                            city.setStateid(Integer.parseInt(col.getChildNodes().item(0).getNodeValue()));
                    }
                }
                if (city.getId() != 0)
                    cities.add(city);
            }
        }

        return cities;
    }

    public List<State> getState() throws IOException, ParserConfigurationException, SAXException {

        List<State> cities = new ArrayList<>();

        InputStream istream = context.getAssets().open("statemasters.xml");
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(istream);
        NodeList node = doc.getElementsByTagName("database");
        Element e = (Element) node.item(0);
        NodeList nList = e.getElementsByTagName("table");
        for (int i = 0; i < nList.getLength(); i++) {
            if (nList.item(0).getNodeType() == Node.ELEMENT_NODE) {
                HashMap<String, String> user = new HashMap<>();
                Element elm = (Element) nList.item(i);
                NodeList nNodeList = elm.getElementsByTagName("column");
                State city = new State();
                for (int j = 0; j < nNodeList.getLength(); j++) {
                    Element col = (Element) nNodeList.item(j);
                    if (col.hasAttribute("name")) {
                        if (col.getAttribute("name").equalsIgnoreCase("id"))
                            city.setId(Integer.parseInt(col.getChildNodes().item(0).getNodeValue()));
                        else if (col.getAttribute("name").equalsIgnoreCase("name"))
                            city.setName(col.getChildNodes().item(0).getNodeValue());
                        else if (col.getAttribute("name").equalsIgnoreCase("countrymaster_id"))
                            city.setCountryid(Integer.parseInt(col.getChildNodes().item(0).getNodeValue()));
                    }
                }
                if (city.getId() != 0)
                    cities.add(city);
            }
        }

        return cities;
    }

    protected String getNodeValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        Node node = nodeList.item(0);
        if (node != null) {
            if (node.hasChildNodes()) {
                Node child = node.getFirstChild();
                while (child != null) {
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
}
