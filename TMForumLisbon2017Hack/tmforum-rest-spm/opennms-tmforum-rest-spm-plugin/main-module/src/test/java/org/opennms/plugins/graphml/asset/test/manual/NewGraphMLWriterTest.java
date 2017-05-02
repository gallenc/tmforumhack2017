package org.opennms.plugins.graphml.asset.test.manual;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;
import org.opennms.features.graphml.model.GraphML;
import org.opennms.features.graphml.model.GraphMLEdge;
import org.opennms.features.graphml.model.GraphMLElement;
import org.opennms.features.graphml.model.GraphMLGraph;
import org.opennms.features.graphml.model.GraphMLNode;
import org.opennms.features.graphml.model.GraphMLReader;
import org.opennms.features.graphml.model.GraphMLWriter;
import org.opennms.features.graphml.model.InvalidGraphException;

public class NewGraphMLWriterTest {

    @Test
    public void verifyWrite() throws InvalidGraphException, FileNotFoundException {
        GraphML graphML = new GraphML();
        GraphMLGraph graph = new GraphMLGraph();
        
        graph.setProperty("description", 1);
        
        
        
        

        GraphMLNode node1 = new GraphMLNode();
        node1.setId("node1");

        GraphMLNode node2 = new GraphMLNode();
        node2.setId("node2");

        GraphMLEdge edge1 = new GraphMLEdge();
        edge1.setId("edge1");
        edge1.setSource(node1);
        edge1.setTarget(node2);

        graphML.addGraph(graph);
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge(edge1);

        GraphMLWriter.write(graphML, new File("target/output.graphml"));
        GraphML read = GraphMLReader.read(new FileInputStream("target/output.graphml"));
        Assert.assertEquals(read, graphML);
    }

}