//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.1 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package net.gexf._1_2;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementRefs;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for attribute-content complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="attribute-content">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice maxOccurs="unbounded" minOccurs="0">
 *         <element ref="{http://www.gexf.net/1.2}default"/>
 *         <element ref="{http://www.gexf.net/1.2}options"/>
 *       </choice>
 *       <attribute name="id" use="required" type="{http://www.gexf.net/1.2}id-type" />
 *       <attribute name="title" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       <attribute name="type" use="required" type="{http://www.gexf.net/1.2}attrtype-type" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "attribute-content", propOrder = {
    "defaultOrOptions"
})
public class AttributeContent {

    @XmlElementRefs({
        @XmlElementRef(name = "default", namespace = "http://www.gexf.net/1.2", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "options", namespace = "http://www.gexf.net/1.2", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<String>> defaultOrOptions;
    @XmlAttribute(name = "id", required = true)
    protected String id;
    @XmlAttribute(name = "title", required = true)
    protected String title;
    @XmlAttribute(name = "type", required = true)
    protected AttrtypeType type;

    /**
     * Gets the value of the defaultOrOptions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the defaultOrOptions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDefaultOrOptions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     * @return
     *     The value of the defaultOrOptions property.
     */
    public List<JAXBElement<String>> getDefaultOrOptions() {
        if (defaultOrOptions == null) {
            defaultOrOptions = new ArrayList<>();
        }
        return this.defaultOrOptions;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link AttrtypeType }
     *     
     */
    public AttrtypeType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttrtypeType }
     *     
     */
    public void setType(AttrtypeType value) {
        this.type = value;
    }

}