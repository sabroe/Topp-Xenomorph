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
 * <p>Java class for key.for.type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="key.for.type">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     <enumeration value="all"/>
 *     <enumeration value="graphml"/>
 *     <enumeration value="graph"/>
 *     <enumeration value="node"/>
 *     <enumeration value="edge"/>
 *     <enumeration value="hyperedge"/>
 *     <enumeration value="port"/>
 *     <enumeration value="endpoint"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "key.for.type")
@XmlEnum
public enum KeyForType {

    @XmlEnumValue("all")
    ALL("all"),
    @XmlEnumValue("graphml")
    GRAPHML("graphml"),
    @XmlEnumValue("graph")
    GRAPH("graph"),
    @XmlEnumValue("node")
    NODE("node"),
    @XmlEnumValue("edge")
    EDGE("edge"),
    @XmlEnumValue("hyperedge")
    HYPEREDGE("hyperedge"),
    @XmlEnumValue("port")
    PORT("port"),
    @XmlEnumValue("endpoint")
    ENDPOINT("endpoint");
    private final String value;

    KeyForType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static KeyForType fromValue(String v) {
        for (KeyForType c: KeyForType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}