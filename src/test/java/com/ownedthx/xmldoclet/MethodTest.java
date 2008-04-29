package com.ownedthx.xmldoclet;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.ownedthx.xmldoclet.xmlbindings.Package;
import com.ownedthx.xmldoclet.xmlbindings.*;
import com.ownedthx.xmldoclet.xmlbindings.Class;

import java.util.ArrayList;

/**
 * Unit test group for Methods
 */
public class MethodTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MethodTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( MethodTest.class );
    }

    public void setUp()
    {
        TestGlobals.initializeLogging();
    }

    /**
     * testing a returns of methods
     */
    public void testMethod1()
    {
        App app = new App();

        String[] sourceFiles = new String[] { "./src/test/java/com/ownedthx/xmldoclet/simpledata/Method1.java" };

        Root root = app.processSource(
                null,
                null,
                null,
                sourceFiles,
                null );

        Package sdPackage = root.packages[0];
        Class sdClass1 = sdPackage.classes[0];
        String methodPrepend = sdClass1.qualifiedName;
        Method[] testMethods = sdClass1.methods;

        // with method1 we are checking that a simple method can exist with no arguments and no return
        Method method = findByMethodName("method1", testMethods);
        assertEquals(method.result.type.qualifiedName, "void");
        assertNull(method.result.type.generics);
        assertNull(method.result.type.wildcard);
        assertEquals(method.result.type.dimension, "");

        // method2 - checking Object based returns
        method = findByMethodName("method2", testMethods);
        assertEquals(method.result.type.qualifiedName, "java.lang.Integer");
        assertNull(method.result.type.generics);
        assertNull(method.result.type.wildcard);
        assertEquals(method.result.type.dimension, "");

        // method 3 - checking primitive based returns
        method = findByMethodName("method3", testMethods);
        assertEquals(method.result.type.qualifiedName, "int");
        assertNull(method.result.type.generics);
        assertNull(method.result.type.wildcard);
        assertEquals(method.result.type.dimension, "");

        // method 4 - checking undefined return comment
        method = findByMethodName("method4", testMethods);
        assertNull(method.result.comment);

        // method5 - checking void return type coupled with method comments defined
        method = findByMethodName("method5", testMethods);
        assertNull(method.result.comment);

        // method6 - checking real return type with empty return javadoc
        method = findByMethodName("method6", testMethods);
        assertEquals(method.result.comment, "");

        // method7 - checking real return type with whitespace return javadoc
        method = findByMethodName("method7", testMethods);
        assertEquals(method.result.comment, "");

        // method8 - checking return type with one word javadoc
        method = findByMethodName("method8", testMethods);
        assertEquals(method.result.comment, "line");

        // method9 -- checking return type with two line javadoc
        method = findByMethodName("method9", testMethods);
        assertEquals(method.result.comment, "line\nline");

        // method10 -- checking return type with two line javadoc in the way everyone does it
        method = findByMethodName("method10", testMethods);
        assertEquals(method.result.comment, "line\n line");
    }

    /**
     * testing arguments of methods
     */
    public void testMethod2()
    {
        App app = new App();

        String[] sourceFiles = new String[] { "./src/test/java/com/ownedthx/xmldoclet/simpledata/Method2.java" };

        Root root = app.processSource(
                null,
                null,
                null,
                sourceFiles,
                null );

        Package sdPackage = root.packages[0];
        Class sdClass1 = sdPackage.classes[0];
        String methodPrepend = sdClass1.qualifiedName;
        Method[] testMethods = sdClass1.methods;

        // method - method with no arguments
        Method method = findByMethodName("method1", testMethods);
        assertNull( method.parameters);
        assertEquals( method.signature, "()");

        // method2 - method with one Object-derived argument
        method = findByMethodName("method2", testMethods);
        assertEquals(method.parameters.length, 1);
        assertEquals(method.signature, "(" + Integer.class.getName() + ")");

        // one should be able to reliably access parameters in this fashion
        // since XML order is important, and order of parameters to methods is
        // likewise important.   ORDER MATTERS AND SHOULD BE TRUSTY!
        Param param = method.parameters[0];
        assertEquals(param.type.qualifiedName,  "java.lang.Integer");

        // method3 - check primitive argument
        method = findByMethodName("method3", testMethods);
        assertEquals(method.parameters.length, 1);
        assertEquals(method.signature, "(int)");

        param = method.parameters[0];
        assertEquals(param.type.qualifiedName, "int");
        assertEquals(param.type.dimension, "");
        assertNull(param.type.generics);
        assertNull(param.type.wildcard);

        // method4 - check that two args are OK
        method = findByMethodName("method4", testMethods);
        assertEquals(method.parameters.length, 2);
        assertEquals(method.signature, "(" + Integer.class.getName() + ", " + Integer.class.getName() + ")");

        param = method.parameters[0];
        assertEquals(param.type.qualifiedName, "java.lang.Integer");

        param = method.parameters[1];
        assertEquals(param.type.qualifiedName, "java.lang.Integer") ;


        // method5 - check that a generic argument is valid
        method = findByMethodName("method5", testMethods);
        assertEquals(method.parameters.length, 1);
        assertEquals(method.signature, "(java.util.ArrayList<java.lang.String>)");

        param = method.parameters[0];
        assertEquals(param.name, "arg1");
        assertEquals(param.type.qualifiedName, "java.util.ArrayList");
        assertEquals(param.type.dimension, "");
        assertNull(param.type.wildcard);
        assertEquals(param.type.generics.typeArguments.length, 1);

        TypeInfo type = param.type.generics.typeArguments[0];
        assertEquals(type.qualifiedName, "java.lang.String");
        assertEquals(type.dimension, "");
        assertNull(type.generics);
        assertNull(type.wildcard);

        // method6 - check that a wildcard argument is valid
        method = findByMethodName("method6", testMethods);
        assertEquals(method.parameters.length, 1);
        assertEquals(method.signature, "(java.util.ArrayList<?>)");

        param = method.parameters[0];
        assertEquals(param.name, "arg1");
        assertEquals(param.type.qualifiedName, "java.util.ArrayList");
        assertEquals(param.type.dimension, "");
        assertEquals(param.type.generics.typeArguments.length, 1);
        assertNull(param.type.wildcard);

        type = param.type.generics.typeArguments[0];
        assertEquals(type.qualifiedName, "?");
        assertEquals(type.dimension, "");
        assertNull(type.generics);
        assertNotNull(type.wildcard);

        WildCardInfo wildcard = type.wildcard;
        assertNull(wildcard.extendsBounds);
        assertNull(wildcard.superBounds);

        // method7 - check that a wildcard argument is valid with extends clause
        method = findByMethodName("method7", testMethods);
        assertEquals(method.parameters.length, 1);
        assertEquals(method.signature, "(java.util.ArrayList<? extends java.lang.String>)");

        param = method.parameters[0];
        assertEquals(param.name, "arg1");
        assertEquals(param.type.qualifiedName, "java.util.ArrayList");
        assertEquals(param.type.dimension, "");
        assertEquals(param.type.generics.typeArguments.length, 1);
        assertNull(param.type.wildcard);

        type = param.type.generics.typeArguments[0];
        assertEquals(type.qualifiedName, "?");
        assertEquals(type.dimension, "");
        assertNull(type.generics);
        assertNotNull(type.wildcard);

        wildcard = type.wildcard;
        assertEquals(wildcard.extendsBounds.length, 1);
        assertNull(wildcard.superBounds);

        TypeInfo extendsBound = wildcard.extendsBounds[0];
        assertEquals( extendsBound.qualifiedName, "java.lang.String" );
        assertEquals( extendsBound.dimension, "");
        assertNull( extendsBound.generics );
        assertNull( extendsBound.wildcard );

        // method8 - check that a wildcard argument is valid with super clause
        method = findByMethodName("method8", testMethods);
        assertEquals(method.parameters.length, 1);
        assertEquals(method.signature, "(java.util.ArrayList<? super java.lang.String>)");

        param = method.parameters[0];
        assertEquals(param.name, "arg1");
        assertEquals(param.type.qualifiedName, "java.util.ArrayList");
        assertEquals(param.type.dimension, "");
        assertEquals(param.type.generics.typeArguments.length, 1);
        assertNull(param.type.wildcard);

        type = param.type.generics.typeArguments[0];
        assertEquals(type.qualifiedName, "?");
        assertEquals(type.dimension, "");
        assertNull(type.generics);
        assertNotNull(type.wildcard);

        wildcard = type.wildcard;
        assertEquals(wildcard.superBounds.length, 1);
        assertNull(wildcard.extendsBounds);

        TypeInfo superBounds = wildcard.superBounds[0];
        assertEquals( extendsBound.qualifiedName, "java.lang.String" );
        assertEquals( extendsBound.dimension, "");
        assertNull( extendsBound.generics );
        assertNull( extendsBound.wildcard );

        // method9 - check that a two-level deep nested generic
        method = findByMethodName("method9", testMethods);
        assertEquals(method.parameters.length, 1);
        assertEquals(method.signature, "(java.util.ArrayList<java.util.ArrayList<java.lang.String>>)");

        param = method.parameters[0];
        assertEquals(param.name, "arg1");
        assertEquals(param.type.qualifiedName, "java.util.ArrayList");
        assertEquals(param.type.dimension, "");
        assertEquals(param.type.generics.typeArguments.length, 1);
        assertNull(param.type.wildcard);

        type = param.type.generics.typeArguments[0];
        assertEquals(type.qualifiedName, "java.util.ArrayList");
        assertEquals(type.dimension, "");
        assertEquals(type.generics.typeArguments.length, 1);
        assertNull(type.wildcard);

        type = type.generics.typeArguments[0];
        assertEquals(type.qualifiedName, "java.lang.String");
        assertEquals(type.dimension, "");
        assertNull(type.generics);
        assertNull(type.wildcard);

        // method10 - check var args
        method = findByMethodName("method10", testMethods);
        assertEquals(method.parameters.length, 1);
        assertEquals(method.signature, "(java.lang.Object...)");
        assertTrue(method.isVarArgs);

        param = method.parameters[0];
        assertEquals(param.name, "object");
        assertEquals(param.type.qualifiedName, "java.lang.Object");
        assertEquals(param.type.dimension, "[]"); // a var arg is just an array of the specified type...

        // method9--check var args negative test
        assertFalse(findByMethodName("method9", testMethods).isVarArgs);
    }

    /**
     * testing method properties
     */
    public void testMethod3()
    {
        App app = new App();

        String[] sourceFiles = new String[] { "./src/test/java/com/ownedthx/xmldoclet/simpledata/Method3.java" };

        Root root = app.processSource(
                null,
                null,
                null,
                sourceFiles,
                null );

        Package sdPackage = root.packages[0];
        Class sdClass1 = sdPackage.classes[0];
        String methodPrepend = sdClass1.qualifiedName;
        Method[] testMethods = sdClass1.methods;

        // method1 -- we check public scope
        Method method = findByMethodName("method1", testMethods);
        assertEquals(method.scope, "public");

        // method2 -- we check package scope
        method = findByMethodName("method2", testMethods);
        assertEquals(method.scope, "packageprivate");

        // method3 -- we check private scope
        method = findByMethodName("method3", testMethods);
        assertEquals(method.scope, "private");

        // method4 -- we check private scope
        method = findByMethodName("method4", testMethods);
        assertEquals(method.scope, "protected");

        // method5 -- we check native
        method = findByMethodName("method5", testMethods);
        assertTrue(method.isNative);
        // and negative
        assertFalse(findByMethodName("method4", testMethods).isNative);

        // method6 -- we check static
        method = findByMethodName("method6", testMethods);
        assertTrue(method.isStatic);
        //and negative
        assertFalse(findByMethodName("method4", testMethods).isStatic);

        // method7 -- we check final
        method = findByMethodName("method7", testMethods);
        assertTrue(method.isFinal);
        //and negative
        assertFalse(findByMethodName("method4", testMethods).isFinal);

        // method8 -- we check synchronized
        method = findByMethodName("method8", testMethods);
        assertTrue(method.isSynchronized);
        //and negative
        assertFalse(findByMethodName("method4", testMethods).isSynchronized);

        // method9 -- we check one thrown exception
        method = findByMethodName("method9", testMethods);
        assertEquals(method.exceptions.length, 1);

        ExceptionInstance exception = method.exceptions[0];
        assertEquals(exception.type.qualifiedName, "java.lang.Exception");
        assertEquals(exception.type.dimension, "");
        assertNull(exception.type.generics);
        assertNull(exception.type.wildcard);

        // method10 -- we check two thrown exceptions
        method = findByMethodName("method10", testMethods);
        assertEquals(method.exceptions.length, 2);

        exception = method.exceptions[0];
        assertEquals(exception.type.qualifiedName, "java.lang.OutOfMemoryError");
        assertEquals(exception.type.dimension, "");
        assertNull(exception.type.generics);
        
        exception = method.exceptions[1];
        assertEquals(exception.type.qualifiedName, "java.lang.IllegalArgumentException");
        assertEquals(exception.type.dimension, "");
        assertNull(exception.type.generics);

        // negative--no exceptions
        assertNull(findByMethodName("method4", testMethods).exceptions);

        // method11 -- 1 annotation instance

        method = findByMethodName("method11", testMethods);
        assertEquals(method.annotationInstances.length, 1);

        AnnotationInstance annotation = method.annotationInstances[0];
        assertEquals(annotation.qualifiedName, "java.lang.Deprecated");
        assertNull(annotation.arguments);

        // method12 -- 2 annotation instances
        method = findByMethodName("method12", testMethods);
        assertEquals(method.annotationInstances.length, 2);

        annotation = method.annotationInstances[0];
        assertEquals(annotation.qualifiedName, "java.lang.Deprecated");

        annotation = method.annotationInstances[1];
        assertEquals(annotation.qualifiedName, "java.lang.SuppressWarnings");
        assertEquals(annotation.arguments.length, 1);
        AnnotationArgument annotArgument = annotation.arguments[0];
        assertEquals(annotArgument.name, "value");
        assertEquals(annotArgument.value, "\"java.lang.Warning\"");

        // negative -- no annotations
        assertNull(findByMethodName("method4", testMethods).annotationInstances);
        
    }




    /**
     * Short way of finding methods.  It's meant to only be used for methods
     * that do not share the same name in the same class.  In fact, this class
     * will junit assert that there is only 1 method matching this name in the
     * supplied <code>list</code> parameter.
     * @param methodName the shortname of the method
     * @param list the list of methods to look through.
     * @return The matching method
     */
    private Method findByMethodName(String methodName, Method[] list)
    {
        boolean foundMethod = false;
        Method found = null;
        if(list != null)
        {
            for(Method method : list)
            {
                if(method.name.equals(methodName))
                {
                    // check for duplicates
                    assertFalse(foundMethod);
                    
                    foundMethod = true;
                    found = method;

                    // no break!  keep checking for dups.
                }
            }
        }

        assertTrue(foundMethod);
        return found;
    }
}