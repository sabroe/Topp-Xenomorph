//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.1 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package net.gexf._1_2;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
import net.gexf._1_2.viz.ColorContent;
import net.gexf._1_2.viz.PositionContent;
import net.gexf._1_2.viz.SizeContent;
import net.gexf._1_2.viz.ThicknessContent;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.gexf._1_2 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Attributes_QNAME = new QName("http://www.gexf.net/1.2", "attributes");
    private final static QName _Nodes_QNAME = new QName("http://www.gexf.net/1.2", "nodes");
    private final static QName _Edges_QNAME = new QName("http://www.gexf.net/1.2", "edges");
    private final static QName _Attvalues_QNAME = new QName("http://www.gexf.net/1.2", "attvalues");
    private final static QName _Spells_QNAME = new QName("http://www.gexf.net/1.2", "spells");
    private final static QName _Parents_QNAME = new QName("http://www.gexf.net/1.2", "parents");
    private final static QName _Color_QNAME = new QName("http://www.gexf.net/1.2", "color");
    private final static QName _Position_QNAME = new QName("http://www.gexf.net/1.2", "position");
    private final static QName _Size_QNAME = new QName("http://www.gexf.net/1.2", "size");
    private final static QName _Thickness_QNAME = new QName("http://www.gexf.net/1.2", "thickness");
    private final static QName _Attribute_QNAME = new QName("http://www.gexf.net/1.2", "attribute");
    private final static QName _Default_QNAME = new QName("http://www.gexf.net/1.2", "default");
    private final static QName _Options_QNAME = new QName("http://www.gexf.net/1.2", "options");
    private final static QName _Gexf_QNAME = new QName("http://www.gexf.net/1.2", "gexf");
    private final static QName _Meta_QNAME = new QName("http://www.gexf.net/1.2", "meta");
    private final static QName _Graph_QNAME = new QName("http://www.gexf.net/1.2", "graph");
    private final static QName _Creator_QNAME = new QName("http://www.gexf.net/1.2", "creator");
    private final static QName _Keywords_QNAME = new QName("http://www.gexf.net/1.2", "keywords");
    private final static QName _Description_QNAME = new QName("http://www.gexf.net/1.2", "description");
    private final static QName _Node_QNAME = new QName("http://www.gexf.net/1.2", "node");
    private final static QName _Edge_QNAME = new QName("http://www.gexf.net/1.2", "edge");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.gexf._1_2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AttributesContent }
     * 
     * @return
     *     the new instance of {@link AttributesContent }
     */
    public AttributesContent createAttributesContent() {
        return new AttributesContent();
    }

    /**
     * Create an instance of {@link NodesContent }
     * 
     * @return
     *     the new instance of {@link NodesContent }
     */
    public NodesContent createNodesContent() {
        return new NodesContent();
    }

    /**
     * Create an instance of {@link EdgesContent }
     * 
     * @return
     *     the new instance of {@link EdgesContent }
     */
    public EdgesContent createEdgesContent() {
        return new EdgesContent();
    }

    /**
     * Create an instance of {@link AttvaluesContent }
     * 
     * @return
     *     the new instance of {@link AttvaluesContent }
     */
    public AttvaluesContent createAttvaluesContent() {
        return new AttvaluesContent();
    }

    /**
     * Create an instance of {@link SpellsContent }
     * 
     * @return
     *     the new instance of {@link SpellsContent }
     */
    public SpellsContent createSpellsContent() {
        return new SpellsContent();
    }

    /**
     * Create an instance of {@link ParentsContent }
     * 
     * @return
     *     the new instance of {@link ParentsContent }
     */
    public ParentsContent createParentsContent() {
        return new ParentsContent();
    }

    /**
     * Create an instance of {@link AttributeContent }
     * 
     * @return
     *     the new instance of {@link AttributeContent }
     */
    public AttributeContent createAttributeContent() {
        return new AttributeContent();
    }

    /**
     * Create an instance of {@link Attvalue }
     * 
     * @return
     *     the new instance of {@link Attvalue }
     */
    public Attvalue createAttvalue() {
        return new Attvalue();
    }

    /**
     * Create an instance of {@link Spell }
     * 
     * @return
     *     the new instance of {@link Spell }
     */
    public Spell createSpell() {
        return new Spell();
    }

    /**
     * Create an instance of {@link Parent }
     * 
     * @return
     *     the new instance of {@link Parent }
     */
    public Parent createParent() {
        return new Parent();
    }

    /**
     * Create an instance of {@link GexfContent }
     * 
     * @return
     *     the new instance of {@link GexfContent }
     */
    public GexfContent createGexfContent() {
        return new GexfContent();
    }

    /**
     * Create an instance of {@link MetaContent }
     * 
     * @return
     *     the new instance of {@link MetaContent }
     */
    public MetaContent createMetaContent() {
        return new MetaContent();
    }

    /**
     * Create an instance of {@link GraphContent }
     * 
     * @return
     *     the new instance of {@link GraphContent }
     */
    public GraphContent createGraphContent() {
        return new GraphContent();
    }

    /**
     * Create an instance of {@link NodeContent }
     * 
     * @return
     *     the new instance of {@link NodeContent }
     */
    public NodeContent createNodeContent() {
        return new NodeContent();
    }

    /**
     * Create an instance of {@link EdgeContent }
     * 
     * @return
     *     the new instance of {@link EdgeContent }
     */
    public EdgeContent createEdgeContent() {
        return new EdgeContent();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributesContent }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AttributesContent }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.gexf.net/1.2", name = "attributes")
    public JAXBElement<AttributesContent> createAttributes(AttributesContent value) {
        return new JAXBElement<>(_Attributes_QNAME, AttributesContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NodesContent }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NodesContent }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.gexf.net/1.2", name = "nodes")
    public JAXBElement<NodesContent> createNodes(NodesContent value) {
        return new JAXBElement<>(_Nodes_QNAME, NodesContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EdgesContent }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EdgesContent }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.gexf.net/1.2", name = "edges")
    public JAXBElement<EdgesContent> createEdges(EdgesContent value) {
        return new JAXBElement<>(_Edges_QNAME, EdgesContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttvaluesContent }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AttvaluesContent }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.gexf.net/1.2", name = "attvalues")
    public JAXBElement<AttvaluesContent> createAttvalues(AttvaluesContent value) {
        return new JAXBElement<>(_Attvalues_QNAME, AttvaluesContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SpellsContent }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SpellsContent }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.gexf.net/1.2", name = "spells")
    public JAXBElement<SpellsContent> createSpells(SpellsContent value) {
        return new JAXBElement<>(_Spells_QNAME, SpellsContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParentsContent }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ParentsContent }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.gexf.net/1.2", name = "parents")
    public JAXBElement<ParentsContent> createParents(ParentsContent value) {
        return new JAXBElement<>(_Parents_QNAME, ParentsContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ColorContent }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ColorContent }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.gexf.net/1.2", name = "color")
    public JAXBElement<ColorContent> createColor(ColorContent value) {
        return new JAXBElement<>(_Color_QNAME, ColorContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PositionContent }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PositionContent }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.gexf.net/1.2", name = "position")
    public JAXBElement<PositionContent> createPosition(PositionContent value) {
        return new JAXBElement<>(_Position_QNAME, PositionContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SizeContent }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SizeContent }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.gexf.net/1.2", name = "size")
    public JAXBElement<SizeContent> createSize(SizeContent value) {
        return new JAXBElement<>(_Size_QNAME, SizeContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ThicknessContent }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ThicknessContent }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.gexf.net/1.2", name = "thickness")
    public JAXBElement<ThicknessContent> createThickness(ThicknessContent value) {
        return new JAXBElement<>(_Thickness_QNAME, ThicknessContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributeContent }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AttributeContent }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.gexf.net/1.2", name = "attribute")
    public JAXBElement<AttributeContent> createAttribute(AttributeContent value) {
        return new JAXBElement<>(_Attribute_QNAME, AttributeContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.gexf.net/1.2", name = "default")
    public JAXBElement<String> createDefault(String value) {
        return new JAXBElement<>(_Default_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.gexf.net/1.2", name = "options")
    public JAXBElement<String> createOptions(String value) {
        return new JAXBElement<>(_Options_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GexfContent }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GexfContent }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.gexf.net/1.2", name = "gexf")
    public JAXBElement<GexfContent> createGexf(GexfContent value) {
        return new JAXBElement<>(_Gexf_QNAME, GexfContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MetaContent }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MetaContent }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.gexf.net/1.2", name = "meta")
    public JAXBElement<MetaContent> createMeta(MetaContent value) {
        return new JAXBElement<>(_Meta_QNAME, MetaContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GraphContent }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GraphContent }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.gexf.net/1.2", name = "graph")
    public JAXBElement<GraphContent> createGraph(GraphContent value) {
        return new JAXBElement<>(_Graph_QNAME, GraphContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.gexf.net/1.2", name = "creator")
    public JAXBElement<String> createCreator(String value) {
        return new JAXBElement<>(_Creator_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.gexf.net/1.2", name = "keywords")
    public JAXBElement<String> createKeywords(String value) {
        return new JAXBElement<>(_Keywords_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.gexf.net/1.2", name = "description")
    public JAXBElement<String> createDescription(String value) {
        return new JAXBElement<>(_Description_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NodeContent }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NodeContent }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.gexf.net/1.2", name = "node")
    public JAXBElement<NodeContent> createNode(NodeContent value) {
        return new JAXBElement<>(_Node_QNAME, NodeContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EdgeContent }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EdgeContent }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.gexf.net/1.2", name = "edge")
    public JAXBElement<EdgeContent> createEdge(EdgeContent value) {
        return new JAXBElement<>(_Edge_QNAME, EdgeContent.class, null, value);
    }

}
