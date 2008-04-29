package com.ownedthx.xmldoclet;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.ownedthx.xmldoclet.xmlbindings.Package;
import com.ownedthx.xmldoclet.xmlbindings.*;
import com.ownedthx.xmldoclet.xmlbindings.Class;
import com.ownedthx.xmldoclet.parser.Parser;
import com.ownedthx.xmldoclet.simpledata.*;

import java.util.List;

/**
 * Unit test group for Classes
 */
public class ClassTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ClassTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ClassTest.class );
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
     * testing a class with nothing defined
     * EMPIRICAL OBSERVATION: The default constructor created by the java compiler is not marked synthetic. um what?
     */
    public void testClass1()
    {
        App app = new App();

        String[] sourceFiles = new String[] { "./src/test/java/com/ownedthx/xmldoclet/simpledata/Class1.java" };

        Root root = app.processSource(
                null,
                null,
                null,
                sourceFiles,
                null );

        Package sdPackage = root.packages[0];
        Class sdClass1 = sdPackage.classes[0];

        assertEquals(root.packages.length, 1);
        assertEquals(sdPackage.comment, "");
        assertEquals(sdPackage.name, "com.ownedthx.xmldoclet.simpledata");
        assertNull(sdPackage.interfaces);
        assertNull(sdPackage.exceptions);
        assertNull(sdPackage.annotations);
        assertNull(sdPackage.enums);
        assertEquals(sdPackage.classes.length, 1);

        assertEquals(sdClass1.comment, "Class1");
        assertEquals(sdClass1.name, Class1.class.getSimpleName());
        assertEquals(sdClass1.qualifiedName, Class1.class.getName());
        assertEquals(sdClass1.scope, "public");
        assertEquals(sdClass1.constructors.length, 1);
        assertNull(sdClass1.fields);
        assertNull(sdClass1.methods);
        assertNull(sdClass1.annotationInstances);
        assertNull(sdClass1.interfaces);
        assertEquals(sdClass1.superClass, Object.class.getName());
        assertEquals(sdClass1.isAbstract, false);
        assertEquals(sdClass1.isExternalizable, false);
        assertEquals(sdClass1.isIncluded, true);
        assertEquals(sdClass1.isSerializable, false);
        assertFalse(sdClass1.isException);
        assertFalse(sdClass1.isError);
        assertNull(sdClass1.typeVariables);
    }

    /**
     * testing a class with 1 constructor
     */
    public void testClass2()
    {
        App app = new App();

        String[] sourceFiles = new String[] { "./src/test/java/com/ownedthx/xmldoclet/simpledata/Class2.java" };

        Root root = app.processSource(
                null,
                null,
                null,
                sourceFiles,
                null );

        Package sdPackage = root.packages[0];
        Class sdClass2 = sdPackage.classes[0];
        Constructor constructor1 = sdClass2.constructors[0];

        assertEquals(root.packages.length, 1);
        assertEquals(sdPackage.comment, "");
        assertEquals(sdPackage.name, "com.ownedthx.xmldoclet.simpledata");
        assertNull(sdPackage.interfaces);
        assertNull(sdPackage.exceptions);
        assertNull(sdPackage.annotations);
        assertNull(sdPackage.enums);
        assertEquals(sdPackage.classes.length, 1);

        assertEquals(sdClass2.comment, "Class2");
        assertEquals(sdClass2.constructors.length, 1);
        assertEquals(sdClass2.name, Class2.class.getSimpleName());
        assertEquals(sdClass2.qualifiedName, Class2.class.getName());
        assertEquals(sdClass2.scope, "public");
        assertNull(sdClass2.methods);
        assertNull(sdClass2.fields);
        assertNull(sdClass2.annotationInstances);
        assertNull(sdClass2.interfaces);
        assertEquals(sdClass2.superClass, Object.class.getName());
        assertEquals(sdClass2.isAbstract, false);
        assertEquals(sdClass2.isExternalizable, false);
        assertEquals(sdClass2.isIncluded, true);
        assertEquals(sdClass2.isSerializable, false);
        assertFalse(sdClass2.isException);
        assertFalse(sdClass2.isError);
        assertNull(sdClass2.typeVariables);

        assertEquals(constructor1.comment, "Constructor1");
        assertEquals(constructor1.name, "Class2");
        assertNull(constructor1.parameters);
        assertNull(constructor1.annotationInstances);
    }

    /**
     * testing a class with 1 method
     */
    public void testClass3()
    {
        App app = new App();

        String[] sourceFiles = new String[] { "./src/test/java/com/ownedthx/xmldoclet/simpledata/Class3.java" };

        Root root = app.processSource(
                null,
                null,
                null,
                sourceFiles,
                null );

        Package sdPackage = root.packages[0];
        Class sdClass3 = sdPackage.classes[0];
        Method method1 = sdClass3.methods[0];

        assertEquals(root.packages.length, 1);
        assertEquals(sdPackage.comment, "");
        assertEquals(sdPackage.name, "com.ownedthx.xmldoclet.simpledata");
        assertNull(sdPackage.interfaces);
        assertNull(sdPackage.exceptions);
        assertNull(sdPackage.annotations);
        assertNull(sdPackage.enums);
        assertEquals(sdPackage.classes.length, 1);

        assertEquals(sdClass3.comment, "Class3");
        assertEquals(sdClass3.constructors.length, 1);
        assertEquals(sdClass3.name, Class3.class.getSimpleName());
        assertEquals(sdClass3.qualifiedName, Class3.class.getName());
        assertEquals(sdClass3.scope, "public");
        assertEquals(sdClass3.methods.length, 1);
        assertNull(sdClass3.fields);
        assertNull(sdClass3.annotationInstances);
        assertNull(sdClass3.interfaces);
        assertEquals(sdClass3.superClass, Object.class.getName());
        assertFalse(sdClass3.isAbstract);
        assertFalse(sdClass3.isExternalizable);
        assertTrue(sdClass3.isIncluded);
        assertFalse(sdClass3.isSerializable);
        assertFalse(sdClass3.isException);
        assertFalse(sdClass3.isError);
        assertNull(sdClass3.typeVariables);

        assertEquals(method1.comment, "method1");
        assertEquals(method1.name, "method1");
        assertEquals(method1.signature, "()");
        assertFalse(method1.isFinal);
        assertFalse(method1.isNative);
        assertFalse(method1.isStatic);
        assertFalse(method1.isSynchronized);
        assertFalse(method1.isVarArgs);
        assertEquals(method1.qualifiedName, "com.ownedthx.xmldoclet.simpledata.Class3.method1");
        assertEquals(method1.hash, Parser.computeHash(method1.qualifiedName, method1.signature));
        assertEquals(method1.scope, "public");
        assertNull(method1.annotationInstances);
        assertNull(method1.parameters);
        assertNull(method1.exceptions);

        Result result = method1.result;
        assertEquals(result.comment, "int");
        assertEquals(result.type.qualifiedName, "int");
        assertEquals(result.type.dimension, "");
        assertNull(result.type.generics);
        assertNull(result.type.wildcard);
    }

    /**
     * testing a class with 1 field
     */
    public void testClass4()
    {
        App app = new App();

        String[] sourceFiles = new String[] { "./src/test/java/com/ownedthx/xmldoclet/simpledata/Class4.java" };

        Root root = app.processSource(
                null,
                null,
                null,
                sourceFiles,
                null );

        Package sdPackage = root.packages[0];
        Class sdClass4 = sdPackage.classes[0];
        Field field1 = sdClass4.fields[0];

        assertEquals(root.packages.length, 1);
        assertEquals(sdPackage.comment, "");
        assertEquals(sdPackage.name, "com.ownedthx.xmldoclet.simpledata");
        assertNull(sdPackage.interfaces);
        assertNull(sdPackage.exceptions);
        assertNull(sdPackage.annotations);
        assertNull(sdPackage.enums);
        assertEquals(sdPackage.classes.length, 1);

        assertEquals(sdClass4.comment, "Class4");
        assertEquals(sdClass4.constructors.length, 1);
        assertEquals(sdClass4.name, Class4.class.getSimpleName());
        assertEquals(sdClass4.qualifiedName, Class4.class.getName());
        assertEquals(sdClass4.scope, "public");
        assertNull(sdClass4.annotationInstances);
        assertNull(sdClass4.methods);
        assertNull(sdClass4.interfaces);
        assertEquals(sdClass4.superClass, Object.class.getName());
        assertFalse(sdClass4.isAbstract);
        assertFalse(sdClass4.isExternalizable);
        assertTrue(sdClass4.isIncluded);
        assertFalse(sdClass4.isSerializable);
        assertFalse(sdClass4.isException);
        assertFalse(sdClass4.isError);
        assertNull(sdClass4.typeVariables);

        // test field
        assertEquals(field1.comment, "field1");
        assertEquals(field1.name,  "field1");
        assertEquals(field1.scope, "public");
        assertEquals(field1.type.qualifiedName, "int");
        assertEquals(field1.type.dimension, "");
        assertNull(field1.type.generics);
        assertNull(field1.type.wildcard);
        assertFalse(field1.isStatic);
        assertFalse(field1.isTransient);
        assertFalse(field1.isVolatile);
        assertFalse(field1.isFinal);
        assertNull(field1.finalExpression);
        assertNull(field1.annotationInstances);
    }


    /**
     * testing a class that extends another class with 1 method
     */
    public void testClass5()
    {
        App app = new App();

        String[] sourceFiles = new String[] {
                "./src/test/java/com/ownedthx/xmldoclet/simpledata/Class5.java",
                "./src/test/java/com/ownedthx/xmldoclet/simpledata/Class3.java"};

        Root root = app.processSource(
                null,
                null,
                null,
                sourceFiles,
                null );

        Package sdPackage = root.packages[0];
        Class sdClass5 = sdPackage.classes[0];

        assertEquals(root.packages.length, 1);
        assertEquals(sdPackage.comment, "");
        assertEquals(sdPackage.name, "com.ownedthx.xmldoclet.simpledata");
        assertNull(sdPackage.interfaces);
        assertNull(sdPackage.exceptions);
        assertNull(sdPackage.annotations);
        assertNull(sdPackage.enums);
        assertEquals(sdPackage.classes.length, 2);

        assertEquals(sdClass5.comment, "Class5");
        assertEquals(sdClass5.constructors.length, 1);
        assertEquals(sdClass5.name, Class5.class.getSimpleName());
        assertEquals(sdClass5.qualifiedName, Class5.class.getName());
        assertEquals(sdClass5.scope, "public");
        assertNull(sdClass5.methods);
        assertNull(sdClass5.fields);
        assertNull(sdClass5.annotationInstances);
        assertNull(sdClass5.interfaces);
        assertEquals(sdClass5.superClass, "com.ownedthx.xmldoclet.simpledata.Class3");
        assertFalse(sdClass5.isAbstract);
        assertFalse(sdClass5.isExternalizable);
        assertTrue(sdClass5.isIncluded);
        assertFalse(sdClass5.isSerializable);
        assertFalse(sdClass5.isException);
        assertFalse(sdClass5.isError);
        assertNull(sdClass5.typeVariables);
    }

    /**
     * testing a class that implements one interface
     */
    public void testClass6()
    {
        App app = new App();

        String[] sourceFiles = new String[] { "./src/test/java/com/ownedthx/xmldoclet/simpledata/Class6.java" };

        Root root = app.processSource(
                null,
                null,
                null,
                sourceFiles,
                null );

        Package sdPackage = root.packages[0];
        Class sdClass6 = sdPackage.classes[0];
        String seriInterface = sdClass6.interfaces[0];

        assertEquals(root.packages.length, 1);
        assertEquals(sdPackage.comment, "");
        assertEquals(sdPackage.name, "com.ownedthx.xmldoclet.simpledata");
        assertNull(sdPackage.interfaces);
        assertNull(sdPackage.exceptions);
        assertNull(sdPackage.annotations);
        assertNull(sdPackage.enums);
        assertEquals(sdPackage.classes.length, 1);

        assertEquals(sdClass6.comment, "Class6");
        assertEquals(sdClass6.constructors.length, 1);
        assertEquals(sdClass6.name, Class6.class.getSimpleName());
        assertEquals(sdClass6.qualifiedName, Class6.class.getName());
        assertEquals(sdClass6.scope, "public");
        assertNull(sdClass6.methods);
        assertNull(sdClass6.fields);
        assertNull(sdClass6.annotationInstances);
        assertEquals(sdClass6.interfaces.length, 1);
        assertEquals(sdClass6.superClass, Object.class.getName());
        assertFalse(sdClass6.isAbstract);
        assertFalse(sdClass6.isExternalizable);
        assertTrue(sdClass6.isIncluded);
        assertFalse(sdClass6.isException);
        assertFalse(sdClass6.isError);
        assertNull(sdClass6.typeVariables);

        // the particular interface chosen for this test also will change this flag to true!
        assertTrue(sdClass6.isSerializable);

        // verify interface
        assertEquals(seriInterface, java.io.Serializable.class.getName());
    }

    /**
     * testing one annotation instance on the class
     */
    public void testClass7()
    {
        App app = new App();

        String[] sourceFiles = new String[] { "./src/test/java/com/ownedthx/xmldoclet/simpledata/Class7.java" };

        Root root = app.processSource(
                null,
                null,
                null,
                sourceFiles,
                null );

        Package sdPackage = root.packages[0];
        Class sdClass7 = sdPackage.classes[0];
        AnnotationInstance annotation = sdClass7.annotationInstances[0];

        assertEquals(root.packages.length, 1);
        assertEquals(sdPackage.comment, "");
        assertEquals(sdPackage.name, "com.ownedthx.xmldoclet.simpledata");
        assertNull(sdPackage.interfaces);
        assertNull(sdPackage.exceptions);
        assertNull(sdPackage.annotations);
        assertNull(sdPackage.enums);
        assertEquals(sdPackage.classes.length, 1);

        assertEquals(sdClass7.comment, "Class7");
        assertEquals(sdClass7.constructors.length, 1);
        assertEquals(sdClass7.name, Class7.class.getSimpleName());
        assertEquals(sdClass7.qualifiedName, Class7.class.getName());
        assertEquals(sdClass7.scope, "public");
        assertNull(sdClass7.methods);
        assertNull(sdClass7.fields);
        assertEquals(sdClass7.annotationInstances.length, 1);
        assertNull(sdClass7.interfaces);
        assertEquals(sdClass7.superClass, Object.class.getName());
        assertFalse(sdClass7.isAbstract);
        assertFalse(sdClass7.isExternalizable);
        assertTrue(sdClass7.isIncluded);
        assertFalse(sdClass7.isSerializable);
        assertFalse(sdClass7.isException);
        assertFalse(sdClass7.isError);
        assertNull(sdClass7.typeVariables);

        // test annotation 'deprecated' on class
        assertEquals(annotation.qualifiedName, "java.lang.Deprecated");
        assertEquals(annotation.name, "Deprecated");
        assertNull(annotation.arguments);
     }

    /**
     * testing abstract keyword on class
     */
    public void testClass8()
    {
        App app = new App();

        String[] sourceFiles = new String[] { "./src/test/java/com/ownedthx/xmldoclet/simpledata/Class8.java" };

        Root root = app.processSource(
                null,
                null,
                null,
                sourceFiles,
                null );

        Package sdPackage = root.packages[0];
        Class sdClass8 = sdPackage.classes[0];
        
        assertEquals(root.packages.length, 1);
        assertEquals(sdPackage.comment, "");
        assertEquals(sdPackage.name, "com.ownedthx.xmldoclet.simpledata");
        assertNull(sdPackage.interfaces);
        assertNull(sdPackage.exceptions);
        assertNull(sdPackage.annotations);
        assertNull(sdPackage.enums);
        assertEquals(sdPackage.classes.length, 1);

        assertEquals(sdClass8.comment, "Class8");
        assertEquals(sdClass8.constructors.length, 1);
        assertEquals(sdClass8.name, Class8.class.getSimpleName());
        assertEquals(sdClass8.qualifiedName, Class8.class.getName());
        assertEquals(sdClass8.scope, "public");
        assertNull(sdClass8.methods);
        assertNull(sdClass8.fields);
        assertNull(sdClass8.annotationInstances);
        assertNull(sdClass8.interfaces);
        assertEquals(sdClass8.superClass, Object.class.getName());
        assertTrue(sdClass8.isAbstract);
        assertFalse(sdClass8.isExternalizable);
        assertTrue(sdClass8.isIncluded);
        assertFalse(sdClass8.isSerializable);
        assertFalse(sdClass8.isException);
        assertFalse(sdClass8.isError);
        assertNull(sdClass8.typeVariables);
    }

    /**
     * testing java.io.Externalizable interface on class
     */
    public void testClass9()
    {
        App app = new App();

        String[] sourceFiles = new String[] { "./src/test/java/com/ownedthx/xmldoclet/simpledata/Class9.java" };

        Root root = app.processSource(
                null,
                null,
                null,
                sourceFiles,
                null );

        Package sdPackage = root.packages[0];
        Class sdClass9 = sdPackage.classes[0];

        assertEquals(root.packages.length, 1);
        assertEquals(sdPackage.comment, "");
        assertEquals(sdPackage.name, "com.ownedthx.xmldoclet.simpledata");
        assertNull(sdPackage.interfaces);
        assertNull(sdPackage.exceptions);
        assertNull(sdPackage.annotations);
        assertNull(sdPackage.enums);
        assertEquals(sdPackage.classes.length, 1);

        assertEquals(sdClass9.comment, "Class9");
        assertEquals(sdClass9.constructors.length, 1);
        assertEquals(sdClass9.name, Class9.class.getSimpleName());
        assertEquals(sdClass9.qualifiedName, Class9.class.getName());
        assertEquals(sdClass9.scope, "public");
        assertEquals(sdClass9.methods.length, 2);
        assertNull(sdClass9.fields);
        assertNull(sdClass9.annotationInstances);
        assertEquals(sdClass9.interfaces.length, 1);
        assertEquals(sdClass9.superClass, Object.class.getName());
        assertFalse(sdClass9.isAbstract);
        assertTrue(sdClass9.isExternalizable);
        assertTrue(sdClass9.isIncluded);
        assertTrue(sdClass9.isSerializable);
        assertFalse(sdClass9.isException);
        assertFalse(sdClass9.isError);
        assertNull(sdClass9.typeVariables);
    }

    /**
     * testing difference of scope modifier on class
     */
    public void testClass10()
    {
        App app = new App();

        String[] sourceFiles = new String[] { "./src/test/java/com/ownedthx/xmldoclet/simpledata/Class10.java" };

        Root root = app.processSource(
                null,
                null,
                null,
                sourceFiles,
                null );

        Package sdPackage = root.packages[0];
        Class sdClass10 = sdPackage.classes[0];

        assertEquals(root.packages.length, 1);
        assertEquals(sdPackage.comment, "");
        assertEquals(sdPackage.name, "com.ownedthx.xmldoclet.simpledata");
        assertNull(sdPackage.interfaces);
        assertNull(sdPackage.exceptions);
        assertNull(sdPackage.annotations);
        assertNull(sdPackage.enums);
        assertEquals(sdPackage.classes.length, 1);

        assertEquals(sdClass10.comment, "Class10");
        assertEquals(sdClass10.constructors.length, 1);
        assertEquals(sdClass10.name, "Class10");
        assertEquals(sdClass10.qualifiedName, "com.ownedthx.xmldoclet.simpledata.Class10");
        assertEquals(sdClass10.scope, "packageprivate");
        assertNull(sdClass10.methods);
        assertNull(sdClass10.fields);
        assertNull(sdClass10.annotationInstances);
        assertNull(sdClass10.interfaces);
        assertEquals(sdClass10.superClass, Object.class.getName());
        assertFalse(sdClass10.isAbstract);
        assertFalse(sdClass10.isExternalizable);
        assertTrue(sdClass10.isIncluded);
        assertFalse(sdClass10.isSerializable);
        assertFalse(sdClass10.isException);
        assertFalse(sdClass10.isError);
        assertNull(sdClass10.typeVariables);
    }

    /**
     * testing if isException is populated correctly
     */
    public void testClass11()
    {
        App app = new App();

        String[] sourceFiles = new String[] { "./src/test/java/com/ownedthx/xmldoclet/simpledata/Class11.java" };

        Root root = app.processSource(
                null,
                null,
                null,
                sourceFiles,
                null );

        Package sdPackage = root.packages[0];
        Class sdClass11 = sdPackage.classes[0];

        assertEquals(root.packages.length, 1);
        assertEquals(sdPackage.comment, "");
        assertEquals(sdPackage.name, "com.ownedthx.xmldoclet.simpledata");
        assertNull(sdPackage.interfaces);
        assertNull(sdPackage.exceptions);
        assertNull(sdPackage.annotations);
        assertNull(sdPackage.enums);
        assertEquals(sdPackage.classes.length, 1);

        assertEquals(sdClass11.comment, "Class11");
        assertEquals(sdClass11.constructors.length, 1);
        assertEquals(sdClass11.name, "Class11");
        assertEquals(sdClass11.qualifiedName, "com.ownedthx.xmldoclet.simpledata.Class11");
        assertEquals(sdClass11.scope, "public");
        assertNull(sdClass11.methods);
        assertNull(sdClass11.fields);
        assertNull(sdClass11.annotationInstances);
        assertNull(sdClass11.interfaces);
        assertEquals(sdClass11.superClass, java.lang.Exception.class.getName());
        assertFalse(sdClass11.isAbstract);
        assertFalse(sdClass11.isExternalizable);
        assertTrue(sdClass11.isIncluded);
        assertTrue(sdClass11.isSerializable);
        assertTrue(sdClass11.isException);
        assertFalse(sdClass11.isError);
        assertNull(sdClass11.typeVariables);
    }

    /**
     * testing if isError is populated correctly
     */
    public void testClass12()
    {
        App app = new App();

        String[] sourceFiles = new String[] { "./src/test/java/com/ownedthx/xmldoclet/simpledata/Class12.java" };

        Root root = app.processSource(
                null,
                null,
                null,
                sourceFiles,
                null );

        Package sdPackage = root.packages[0];
        Class sdClass12 = sdPackage.classes[0];

        assertEquals(root.packages.length, 1);
        assertEquals(sdPackage.comment, "");
        assertEquals(sdPackage.name, "com.ownedthx.xmldoclet.simpledata");
        assertNull(sdPackage.interfaces);
        assertNull(sdPackage.exceptions);
        assertNull(sdPackage.annotations);
        assertNull(sdPackage.enums);
        assertEquals(sdPackage.classes.length, 1);

        assertEquals(sdClass12.comment, "Class12");
        assertEquals(sdClass12.constructors.length, 1);
        assertEquals(sdClass12.name, "Class12");
        assertEquals(sdClass12.qualifiedName, "com.ownedthx.xmldoclet.simpledata.Class12");
        assertEquals(sdClass12.scope, "public");
        assertNull(sdClass12.methods);
        assertNull(sdClass12.fields);
        assertNull(sdClass12.annotationInstances);
        assertNull(sdClass12.interfaces);
        assertEquals(sdClass12.superClass, java.lang.Error.class.getName());
        assertFalse(sdClass12.isAbstract);
        assertFalse(sdClass12.isExternalizable);
        assertTrue(sdClass12.isIncluded);
        assertTrue(sdClass12.isSerializable);
        assertFalse(sdClass12.isException);
        assertTrue(sdClass12.isError);
        assertNull(sdClass12.typeVariables);
    }

     /**
     * testing if type variables can be determined
     */
    public void testClass13()
    {
        App app = new App();

        String[] sourceFiles = new String[] { "./src/test/java/com/ownedthx/xmldoclet/simpledata/Class13.java" };

        Root root = app.processSource(
                null,
                null,
                null,
                sourceFiles,
                null );

        Package sdPackage = root.packages[0];
        Class sdClass13 = sdPackage.classes[0];
        TypeVar typeVar = sdClass13.typeVariables[0];

        assertEquals(root.packages.length, 1);
        assertEquals(sdPackage.comment, "");
        assertEquals(sdPackage.name, "com.ownedthx.xmldoclet.simpledata");
        assertNull(sdPackage.interfaces);
        assertNull(sdPackage.exceptions);
        assertNull(sdPackage.annotations);
        assertNull(sdPackage.enums);
        assertEquals(sdPackage.classes.length, 1);

        assertEquals(sdClass13.comment, "Class13");
        assertEquals(sdClass13.constructors.length, 1);
        assertEquals(sdClass13.name, "Class13");
        assertEquals(sdClass13.qualifiedName, "com.ownedthx.xmldoclet.simpledata.Class13");
        assertEquals(sdClass13.scope, "public");
        assertEquals(sdClass13.typeVariables.length , 1);
        assertNull(sdClass13.methods);
        assertNull(sdClass13.fields);
        assertNull(sdClass13.annotationInstances);
        assertNull(sdClass13.interfaces);
        assertEquals(sdClass13.superClass, Object.class.getName());
        assertFalse(sdClass13.isAbstract);
        assertFalse(sdClass13.isExternalizable);
        assertTrue(sdClass13.isIncluded);
        assertFalse(sdClass13.isSerializable);
        assertFalse(sdClass13.isException);
        assertFalse(sdClass13.isError);

        // check the 'fun' type var

        assertEquals(typeVar.name, "Fun");
        assertNull(typeVar.bounds);

    }

    /**
     * testing if a single bounds can be determined
     */
    public void testClass14()
    {
        App app = new App();

        String[] sourceFiles = new String[] { "./src/test/java/com/ownedthx/xmldoclet/simpledata/Class14.java" };

        Root root = app.processSource(
                null,
                null,
                null,
                sourceFiles,
                null );

        Package sdPackage = root.packages[0];
        Class sdClass14 = sdPackage.classes[0];
        TypeVar typeVar = sdClass14.typeVariables[0];

        assertEquals(root.packages.length, 1);
        assertEquals(sdPackage.comment, "");
        assertEquals(sdPackage.name, "com.ownedthx.xmldoclet.simpledata");
        assertNull(sdPackage.interfaces);
        assertNull(sdPackage.exceptions);
        assertNull(sdPackage.annotations);
        assertNull(sdPackage.enums);
        assertEquals(sdPackage.classes.length, 1);

        assertEquals(sdClass14.comment, "Class14");
        assertEquals(sdClass14.constructors.length, 1);
        assertEquals(sdClass14.name, "Class14");
        assertEquals(sdClass14.qualifiedName, "com.ownedthx.xmldoclet.simpledata.Class14");
        assertEquals(sdClass14.scope, "public");
        assertEquals(sdClass14.typeVariables.length , 1);
        assertNull(sdClass14.methods);
        assertNull(sdClass14.fields);
        assertNull(sdClass14.annotationInstances);
        assertNull(sdClass14.interfaces);
        assertEquals(sdClass14.superClass, Object.class.getName());
        assertFalse(sdClass14.isAbstract);
        assertFalse(sdClass14.isExternalizable);
        assertTrue(sdClass14.isIncluded);
        assertFalse(sdClass14.isSerializable);
        assertFalse(sdClass14.isException);
        assertFalse(sdClass14.isError);

        // check the 'fun' type var

        assertEquals(typeVar.name, "Fun");
        assertEquals(typeVar.bounds.length, 1);
        assertEquals(typeVar.bounds[0], String.class.getName());
    }

    /**
     * testing if a multiple bounds can be determined
     */
    public void testClass15()
    {
        App app = new App();

        String[] sourceFiles = new String[] { "./src/test/java/com/ownedthx/xmldoclet/simpledata/Class15.java" };

        Root root = app.processSource(
                null,
                null,
                null,
                sourceFiles,
                null );

        Package sdPackage = root.packages[0];
        Class sdClass15 = sdPackage.classes[0];
        TypeVar typeVar = sdClass15.typeVariables[0];

        assertEquals(root.packages.length, 1);
        assertEquals(sdPackage.comment, "");
        assertEquals(sdPackage.name, "com.ownedthx.xmldoclet.simpledata");
        assertNull(sdPackage.interfaces);
        assertNull(sdPackage.exceptions);
        assertNull(sdPackage.annotations);
        assertNull(sdPackage.enums);
        assertEquals(sdPackage.classes.length, 1);

        assertEquals(sdClass15.comment, "Class15");
        assertEquals(sdClass15.constructors.length, 1);
        assertEquals(sdClass15.name, "Class15");
        assertEquals(sdClass15.qualifiedName, "com.ownedthx.xmldoclet.simpledata.Class15");
        assertEquals(sdClass15.scope, "public");
        assertEquals(sdClass15.typeVariables.length , 1);
        assertNull(sdClass15.methods);
        assertNull(sdClass15.fields);
        assertNull(sdClass15.annotationInstances);
        assertNull(sdClass15.interfaces);
        assertEquals(sdClass15.superClass, Object.class.getName());
        assertFalse(sdClass15.isAbstract);
        assertFalse(sdClass15.isExternalizable);
        assertTrue(sdClass15.isIncluded);
        assertFalse(sdClass15.isSerializable);
        assertFalse(sdClass15.isException);
        assertFalse(sdClass15.isError);

        // check the 'fun' type var

        assertEquals(typeVar.name, "Fun");
        assertEquals(typeVar.bounds.length, 2);
        assertEquals(typeVar.bounds[0], String.class.getName());
        assertEquals(typeVar.bounds[1], Runnable.class.getName());
    }
}

