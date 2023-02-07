//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.1 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package net.gexf._1_2;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for graph-content complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="graph-content">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice maxOccurs="unbounded" minOccurs="0">
 *         <element ref="{http://www.gexf.net/1.2}attributes"/>
 *         <choice>
 *           <element ref="{http://www.gexf.net/1.2}nodes"/>
 *           <element ref="{http://www.gexf.net/1.2}edges"/>
 *         </choice>
 *       </choice>
 *       <attribute name="timeformat" type="{http://www.gexf.net/1.2}timeformat-type" />
 *       <attribute name="start" type="{http://www.gexf.net/1.2}time-type" />
 *       <attribute name="startopen" type="{http://www.gexf.net/1.2}time-type" />
 *       <attribute name="end" type="{http://www.gexf.net/1.2}time-type" />
 *       <attribute name="endopen" type="{http://www.gexf.net/1.2}time-type" />
 *       <attribute name="defaultedgetype" type="{http://www.gexf.net/1.2}defaultedgetype-type" />
 *       <attribute name="idtype" type="{http://www.gexf.net/1.2}idtype-type" />
 *       <attribute name="mode" type="{http://www.gexf.net/1.2}mode-type" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "graph-content", propOrder = {
    "attributesOrNodesOrEdges"
})
public class GraphContent {

    @XmlElements({
        @XmlElement(name = "attributes", type = AttributesContent.class),
        @XmlElement(name = "nodes", type = NodesContent.class),
        @XmlElement(name = "edges", type = EdgesContent.class)
    })
    protected List<Object> attributesOrNodesOrEdges;
    @XmlAttribute(name = "timeformat")
    protected TimeformatType timeformat;
    @XmlAttribute(name = "start")
    protected String start;
    @XmlAttribute(name = "startopen")
    protected String startopen;
    @XmlAttribute(name = "end")
    protected String end;
    @XmlAttribute(name = "endopen")
    protected String endopen;
    @XmlAttribute(name = "defaultedgetype")
    protected DefaultedgetypeType defaultedgetype;
    @XmlAttribute(name = "idtype")
    protected IdtypeType idtype;
    @XmlAttribute(name = "mode")
    protected ModeType mode;

    /**
     * Gets the value of the attributesOrNodesOrEdges property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the attributesOrNodesOrEdges property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributesOrNodesOrEdges().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributesContent }
     * {@link EdgesContent }
     * {@link NodesContent }
     * 
     * 
     * @return
     *     The value of the attributesOrNodesOrEdges property.
     */
    public List<Object> getAttributesOrNodesOrEdges() {
        if (attributesOrNodesOrEdges == null) {
            attributesOrNodesOrEdges = new ArrayList<>();
        }
        return this.attributesOrNodesOrEdges;
    }

    /**
     * Gets the value of the timeformat property.
     * 
     * @return
     *     possible object is
     *     {@link TimeformatType }
     *     
     */
    public TimeformatType getTimeformat() {
        return timeformat;
    }

    /**
     * Sets the value of the timeformat property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeformatType }
     *     
     */
    public void setTimeformat(TimeformatType value) {
        this.timeformat = value;
    }

    /**
     * Gets the value of the start property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStart() {
        return start;
    }

    /**
     * Sets the value of the start property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStart(String value) {
        this.start = value;
    }

    /**
     * Gets the value of the startopen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartopen() {
        return startopen;
    }

    /**
     * Sets the value of the startopen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartopen(String value) {
        this.startopen = value;
    }

    /**
     * Gets the value of the end property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnd() {
        return end;
    }

    /**
     * Sets the value of the end property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnd(String value) {
        this.end = value;
    }

    /**
     * Gets the value of the endopen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndopen() {
        return endopen;
    }

    /**
     * Sets the value of the endopen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndopen(String value) {
        this.endopen = value;
    }

    /**
     * Gets the value of the defaultedgetype property.
     * 
     * @return
     *     possible object is
     *     {@link DefaultedgetypeType }
     *     
     */
    public DefaultedgetypeType getDefaultedgetype() {
        return defaultedgetype;
    }

    /**
     * Sets the value of the defaultedgetype property.
     * 
     * @param value
     *     allowed object is
     *     {@link DefaultedgetypeType }
     *     
     */
    public void setDefaultedgetype(DefaultedgetypeType value) {
        this.defaultedgetype = value;
    }

    /**
     * Gets the value of the idtype property.
     * 
     * @return
     *     possible object is
     *     {@link IdtypeType }
     *     
     */
    public IdtypeType getIdtype() {
        return idtype;
    }

    /**
     * Sets the value of the idtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdtypeType }
     *     
     */
    public void setIdtype(IdtypeType value) {
        this.idtype = value;
    }

    /**
     * Gets the value of the mode property.
     * 
     * @return
     *     possible object is
     *     {@link ModeType }
     *     
     */
    public ModeType getMode() {
        return mode;
    }

    /**
     * Sets the value of the mode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModeType }
     *     
     */
    public void setMode(ModeType value) {
        this.mode = value;
    }

}
