//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.1 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package org.graphdrawing.graphml._1_0;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 *       Complex type for the <data> element.
 *       data.type is mixed, that is, <data> may contain #PCDATA.
 *       Content type: extension of data-extension.type which is empty
 *                     per default.
 *     
 * 
 * <p>Java class for data.type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="data.type">
 *   <complexContent>
 *     <extension base="{http://graphml.graphdrawing.org/xmlns}data-extension.type">
 *       <attGroup ref="{http://graphml.graphdrawing.org/xmlns}data.extra.attrib"/>
 *       <attribute name="key" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *       <attribute name="time" type="{http://www.w3.org/2001/XMLSchema}long" default="0" />
 *       <attribute name="id" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *     </extension>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "data.type")
public class DataType
    extends DataExtensionType
{

    @XmlAttribute(name = "key", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String key;
    @XmlAttribute(name = "time")
    protected Long time;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String id;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKey(String value) {
        this.key = value;
    }

    /**
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getTime() {
        if (time == null) {
            return  0L;
        } else {
            return time;
        }
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTime(Long value) {
        this.time = value;
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

}