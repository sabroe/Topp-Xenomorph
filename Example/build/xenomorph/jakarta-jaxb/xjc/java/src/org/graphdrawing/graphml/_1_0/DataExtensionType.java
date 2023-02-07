//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.1 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package org.graphdrawing.graphml._1_0;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;


/**
 * 
 *       Extension mechanism for the content of <data> and <default>.
 *       The complex type data-extension.type is empty per default.
 *       Users may redefine this type in order to add content to 
 *       the complex types data.type and default.type which are 
 *       extensions of data-extension.type.
 *     
 * 
 * <p>Java class for data-extension.type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="data-extension.type">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "data-extension.type", propOrder = {
    "content"
})
@XmlSeeAlso({
    DataType.class,
    DefaultType.class
})
public class DataExtensionType {

    @XmlValue
    protected String content;

    /**
     * 
     *       Extension mechanism for the content of <data> and <default>.
     *       The complex type data-extension.type is empty per default.
     *       Users may redefine this type in order to add content to 
     *       the complex types data.type and default.type which are 
     *       extensions of data-extension.type.
     *     
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
    }

}
