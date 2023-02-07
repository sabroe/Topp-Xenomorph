//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.1 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package net.gexf._1_2;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for edgetype-type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="edgetype-type">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="directed"/>
 *     <enumeration value="undirected"/>
 *     <enumeration value="mutual"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "edgetype-type")
@XmlEnum
public enum EdgetypeType {

    @XmlEnumValue("directed")
    DIRECTED("directed"),
    @XmlEnumValue("undirected")
    UNDIRECTED("undirected"),
    @XmlEnumValue("mutual")
    MUTUAL("mutual");
    private final String value;

    EdgetypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EdgetypeType fromValue(String v) {
        for (EdgetypeType c: EdgetypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
