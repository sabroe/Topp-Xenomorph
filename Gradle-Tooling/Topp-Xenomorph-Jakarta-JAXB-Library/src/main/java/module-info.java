/**
 * .
 */
module com.yelstream.topp.jakarta.jaxb {
    requires static lombok;
    requires jakarta.annotation;
    requires jakarta.activation;
//    requires com.sun.activation.registries;
    requires jakarta.xml.bind;
    requires com.sun.xml.bind.core;
    requires com.sun.xml.bind;
    requires com.sun.tools.jxc;
    requires com.sun.tools.xjc;
    exports com.yelstream.topp.jakarta;
}
