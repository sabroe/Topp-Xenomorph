//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.1 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package net.gexf._1_2.viz;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for node-shape-type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="node-shape-type">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="disc"/>
 *     <enumeration value="square"/>
 *     <enumeration value="triangle"/>
 *     <enumeration value="diamond"/>
 *     <enumeration value="image"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "node-shape-type")
@XmlEnum
public enum NodeShapeType {

    @XmlEnumValue("disc")
    DISC("disc"),
    @XmlEnumValue("square")
    SQUARE("square"),
    @XmlEnumValue("triangle")
    TRIANGLE("triangle"),
    @XmlEnumValue("diamond")
    DIAMOND("diamond"),
    @XmlEnumValue("image")
    IMAGE("image");
    private final String value;

    NodeShapeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static NodeShapeType fromValue(String v) {
        for (NodeShapeType c: NodeShapeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
