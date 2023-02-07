//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.1 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package org.iso.std.iso._20022.tech.pain_001_001_02;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentCategoryPurpose1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="PaymentCategoryPurpose1Code">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="CORT"/>
 *     <enumeration value="SALA"/>
 *     <enumeration value="TREA"/>
 *     <enumeration value="CASH"/>
 *     <enumeration value="DIVI"/>
 *     <enumeration value="GOVT"/>
 *     <enumeration value="INTE"/>
 *     <enumeration value="LOAN"/>
 *     <enumeration value="PENS"/>
 *     <enumeration value="SECU"/>
 *     <enumeration value="SSBE"/>
 *     <enumeration value="SUPP"/>
 *     <enumeration value="TAXS"/>
 *     <enumeration value="TRAD"/>
 *     <enumeration value="VATX"/>
 *     <enumeration value="HEDG"/>
 *     <enumeration value="INTC"/>
 *     <enumeration value="WHLD"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "PaymentCategoryPurpose1Code")
@XmlEnum
public enum PaymentCategoryPurpose1Code {

    CORT,
    SALA,
    TREA,
    CASH,
    DIVI,
    GOVT,
    INTE,
    LOAN,
    PENS,
    SECU,
    SSBE,
    SUPP,
    TAXS,
    TRAD,
    VATX,
    HEDG,
    INTC,
    WHLD;

    public String value() {
        return name();
    }

    public static PaymentCategoryPurpose1Code fromValue(String v) {
        return valueOf(v);
    }

}
