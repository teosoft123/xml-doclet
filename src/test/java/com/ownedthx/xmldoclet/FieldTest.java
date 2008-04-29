package com.ownedthx.xmldoclet;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.ownedthx.xmldoclet.xmlbindings.Package;
import com.ownedthx.xmldoclet.xmlbindings.*;
import com.ownedthx.xmldoclet.xmlbindings.Class;

import java.util.ArrayList;

/**
 * Unit test group for Fields
 */
public class FieldTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public FieldTest ( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( FieldTest.class );
    }

    public void setUp()
    {
        TestGlobals.initializeLogging();
    }

    /**
     * testing a returns of fields
     */
    public void testMethod1()
    {
        App app = new App();

        String[] sourceFiles = new String[] { "./src/test/java/com/ownedthx/xmldoclet/simpledata/Field1.java" };

        Root root = app.processSource(
                null,
                null,
                null,
                sourceFiles,
                null );

        Package sdPackage = root.packages[0];
        Class sdClass1 = sdPackage.classes[0];
        Field[] testFields = sdClass1.fields;

        // field0 -- test name
        Field field = findByFieldName("field0", testFields);
        assertEquals(field.name, "field0");
        
        // field1 -- test public field
        field = findByFieldName( "field1", testFields );
        assertEquals(field.scope,  "public");

        // field2 -- test private field
        field = findByFieldName("field2", testFields);
        assertEquals(field.scope, "private");

        // field3 -- default scope field (non defined)
        field = findByFieldName( "field3", testFields );
        assertEquals(field.scope, "packageprivate");

        // field4 -- protected scope field
        field = findByFieldName( "field4", testFields );
        assertEquals(field.scope, "protected");

        // field5 -- volatile field
        field = findByFieldName( "field5", testFields );
        assertTrue(field.isVolatile);

        // negative test of volatile
        assertFalse( findByFieldName( "field4", testFields ).isVolatile );

        // field6 -- static field
        field = findByFieldName("field6", testFields);
        assertTrue(field.isStatic);

        // negative test of static
        assertFalse(findByFieldName("field4", testFields).isStatic);

        // field7 -- transient field
        field = findByFieldName("field7", testFields);
        assertTrue(field.isTransient);

        // negative test of transient
        assertFalse(findByFieldName("field4", testFields).isTransient);

        // field8 -- final field
        field = findByFieldName("field8", testFields);
        assertTrue(field.isFinal);

        // negative test of final
        assertFalse(findByFieldName("field4", testFields).isFinal);

        // field9 -- string final expression
        field = findByFieldName("field9", testFields);
        assertEquals( field.finalExpression, "\"testy\"" );

        // field10 -- int final expression
        field = findByFieldName("field10", testFields);
        assertEquals(field.finalExpression, "10");

        // field11 -- annotation
        field = findByFieldName("field11", testFields);
        assertEquals(field.annotationInstances.length, 1);

        AnnotationInstance annotation = field.annotationInstances[0];
        assertEquals(annotation.qualifiedName, "java.lang.Deprecated");
        assertEquals(annotation.name, "Deprecated");
        assertNull(annotation.arguments);

        // field12 -- two annotations
        field = findByFieldName("field12", testFields);
        assertEquals(field.annotationInstances.length, 2);

        annotation = field.annotationInstances[0];
        assertEquals(annotation.qualifiedName, "java.lang.Deprecated");
        assertEquals(annotation.name, "Deprecated");
        assertNull(annotation.arguments);

        annotation = field.annotationInstances[1];
        assertEquals(annotation.qualifiedName, "java.lang.SuppressWarnings");
        assertEquals(annotation.name, "SuppressWarnings");
        assertEquals(annotation.arguments.length, 1);

        AnnotationArgument argument = annotation.arguments[0];
        assertEquals(argument.name, "value");
        assertEquals(argument.value, "\"mister\"");

        // field13 - type testing
        field = findByFieldName("field13", testFields);
        assertNotNull(field.type);
        assertEquals(field.type.qualifiedName, "java.lang.String");
        assertEquals(field.type.dimension, "");
        assertNull(field.type.wildcard);
        assertNull(field.type.generics);

        // field14 - wild card
        field = findByFieldName("field14", testFields);
        assertNotNull(field.type);
        assertEquals(field.type.qualifiedName, "java.util.ArrayList");
        assertNotNull(field.type.generics);
        assertEquals(field.type.generics.typeArguments.length, 1);
        assertEquals(field.type.generics.typeArguments[0].qualifiedName, "?");
        assertNotNull(field.type.generics.typeArguments[0].wildcard);

        // field15 - typed generic
        field = findByFieldName("field15", testFields);
        assertNotNull(field.type);
        assertEquals(field.type.qualifiedName, "java.util.HashMap");
        assertNotNull(field.type.generics);
        assertEquals(field.type.generics.typeArguments.length, 2);
        assertEquals(field.type.generics.typeArguments[0].qualifiedName, "java.lang.String");
        assertNull(field.type.generics.typeArguments[0].wildcard);
        assertEquals(field.type.generics.typeArguments[1].qualifiedName, "java.lang.Integer");
        assertNull(field.type.generics.typeArguments[1].wildcard);
        
        // field16 - array
        field = findByFieldName("field16", testFields);
        assertNotNull(field.type);
        assertEquals(field.type.qualifiedName, "java.lang.String");
        assertEquals(field.type.dimension, "[]");
        
    }

    /**
     * Short way of finding fields.
     * @param fieldName the shortname of the method
     * @param list the list of methods to look through.
     * @return The matching field
     */
    private Field findByFieldName(String fieldName, Field[] list)
    {
        boolean foundField = false;
        Field found = null;
        if(list != null)
        {
            for(Field field : list)
            {
                if(field.name.equals(fieldName))
                {
                    foundField = true;
                    found = field;
                    break;
                }
            }
        }

        assertTrue(foundField);
        return found;
    }
}