//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.1 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package org.graphdrawing.graphml._1_0;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for graph.order.type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="graph.order.type">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     <enumeration value="free"/>
 *     <enumeration value="nodesfirst"/>
 *     <enumeration value="adjacencylist"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "graph.order.type")
@XmlEnum
public enum GraphOrderType {

    @XmlEnumValue("free")
    FREE("free"),
    @XmlEnumValue("nodesfirst")
    NODESFIRST("nodesfirst"),
    @XmlEnumValue("adjacencylist")
    ADJACENCYLIST("adjacencylist");
    private final String value;

    GraphOrderType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GraphOrderType fromValue(String v) {
        for (GraphOrderType c: GraphOrderType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
