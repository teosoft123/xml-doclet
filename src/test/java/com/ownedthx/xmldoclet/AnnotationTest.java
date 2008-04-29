package com.ownedthx.xmldoclet;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.ownedthx.xmldoclet.xmlbindings.Package;
import com.ownedthx.xmldoclet.xmlbindings.*;
import com.ownedthx.xmldoclet.simpledata.*;

/**
 * Unit test group for Annotations
 */
public class AnnotationTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AnnotationTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AnnotationTest.class );
    }

    public void setUp()
    {
        TestGlobals.initializeLogging();
    }
    
    /**
     * Rigourous Parser :-)
     */
    public void testSampledoc()
    {
        App app = new App();
        Root root = app.processSource(
            ".",
            new String[] { "./src/test/java"},
            null,
            null,
            new String[] { "com" });

        assertTrue( true );
    }

    /**
     * testing an annotation with nothing defined
     */
    public void testAnnotation1()
    {
        App app = new App();

        String[] sourceFiles = new String[] { "./src/test/java/com/ownedthx/xmldoclet/simpledata/Annotation1.java" };

        Root root = app.processSource(
                null,
                null,
                null,
                sourceFiles,
                null );

        Package sdPackage = root.packages[0];
        Annotation sdAnnotation1 = sdPackage.annotations[0];

        assertEquals(root.packages.length, 1);
        assertEquals(sdPackage.comment, "");
        assertEquals(sdPackage.name, "com.ownedthx.xmldoclet.simpledata");
        assertNull(sdPackage.interfaces);
        assertNull(sdPackage.exceptions);
        assertEquals(sdPackage.annotations.length, 1);
        assertNull(sdPackage.enums);
        assertNull(sdPackage.classes);

        assertEquals(sdAnnotation1.comment, "Annotation1");
        assertEquals(sdAnnotation1.name, Annotation1.class.getSimpleName());
        assertEquals(sdAnnotation1.qualifiedName, Annotation1.class.getName());
        assertEquals(sdAnnotation1.scope, "public");
        assertNull(sdAnnotation1.annotationInstances);
        assertNull(sdAnnotation1.elements);
        assertTrue(sdAnnotation1.isIncluded);
    }

    /**
     * testing an annotation with an annotation decorating it
     */
    public void testAnnotation2()
    {
        App app = new App();

        String[] sourceFiles = new String[] { "./src/test/java/com/ownedthx/xmldoclet/simpledata/Annotation2.java" };

        Root root = app.processSource(
                null,
                null,
                null,
                sourceFiles,
                null );

        Package sdPackage = root.packages[0];
        Annotation sdAnnotation1 = sdPackage.annotations[0];
        AnnotationInstance annotation = sdAnnotation1.annotationInstances[0];

        assertEquals(root.packages.length, 1);
        assertEquals(sdPackage.comment, "");
        assertEquals(sdPackage.name, "com.ownedthx.xmldoclet.simpledata");
        assertNull(sdPackage.interfaces);
        assertNull(sdPackage.exceptions);
        assertEquals(sdPackage.annotations.length, 1);
        assertNull(sdPackage.enums);
        assertNull(sdPackage.classes);

        assertEquals(sdAnnotation1.comment, "Annotation2");
        assertEquals(sdAnnotation1.name, Annotation2.class.getSimpleName());
        assertEquals(sdAnnotation1.qualifiedName, Annotation2.class.getName());
        assertEquals(sdAnnotation1.scope, "public");
        assertEquals(sdAnnotation1.annotationInstances.length, 1);
        assertNull(sdAnnotation1.elements);
        assertTrue(sdAnnotation1.isIncluded);

        // test annotation 'deprecated' on class
        assertEquals(annotation.qualifiedName, "java.lang.Deprecated");
        assertEquals(annotation.name, "Deprecated");
        assertNull(annotation.arguments);
    }

    /**
     * testing an annotation with one element field
     */
    public void testAnnotation3()
    {
        App app = new App();

        String[] sourceFiles = new String[] { "./src/test/java/com/ownedthx/xmldoclet/simpledata/Annotation3.java" };

        Root root = app.processSource(
                null,
                null,
                null,
                sourceFiles,
                null );

        Package sdPackage = root.packages[0];
        Annotation sdAnnotation1 = sdPackage.annotations[0];
        AnnotationElement element = sdAnnotation1.elements[0];

        assertEquals(root.packages.length, 1);
        assertEquals(sdPackage.comment, "");
        assertEquals(sdPackage.name, "com.ownedthx.xmldoclet.simpledata");
        assertNull(sdPackage.interfaces);
        assertNull(sdPackage.exceptions);
        assertEquals(sdPackage.annotations.length, 1);
        assertNull(sdPackage.enums);
        assertNull(sdPackage.classes);

        assertEquals(sdAnnotation1.comment, "Annotation3");
        assertEquals(sdAnnotation1.name, Annotation3.class.getSimpleName());
        assertEquals(sdAnnotation1.qualifiedName, Annotation3.class.getName());
        assertEquals(sdAnnotation1.scope, "public");
        assertNull(sdAnnotation1.annotationInstances);
        assertEquals(sdAnnotation1.elements.length, 1);
        assertTrue(sdAnnotation1.isIncluded);

        // test annotation element

        assertEquals(element.name, "id");
        assertEquals(element.qualifiedName, "com.ownedthx.xmldoclet.simpledata.Annotation3.id");
        assertEquals(element.type, "int");
        assertEquals(element.defaultValue, Integer.toString(3));
    }

    /**
     * testing an annotation with non-public definition
     */
    public void testAnnotation4()
    {
        App app = new App();

        String[] sourceFiles = new String[] { "./src/test/java/com/ownedthx/xmldoclet/simpledata/Annotation4.java" };

        Root root = app.processSource(
                null,
                null,
                null,
                sourceFiles,
                null );

        Package sdPackage = root.packages[0];
        Annotation sdAnnotation1 = sdPackage.annotations[0];

        assertEquals(root.packages.length, 1);
        assertEquals(sdPackage.comment, "");
        assertEquals(sdPackage.name, "com.ownedthx.xmldoclet.simpledata");
        assertNull(sdPackage.interfaces);
        assertNull(sdPackage.exceptions);
        assertEquals(sdPackage.annotations.length, 1);
        assertNull(sdPackage.enums);
        assertNull(sdPackage.classes);

        assertEquals(sdAnnotation1.comment, "Annotation4");
        assertEquals(sdAnnotation1.name, "Annotation4");
        assertEquals(sdAnnotation1.qualifiedName, "com.ownedthx.xmldoclet.simpledata.Annotation4");
        assertEquals(sdAnnotation1.scope, "packageprivate");
        assertNull(sdAnnotation1.annotationInstances);
        assertNull(sdAnnotation1.elements);
        assertTrue(sdAnnotation1.isIncluded);
    }
}