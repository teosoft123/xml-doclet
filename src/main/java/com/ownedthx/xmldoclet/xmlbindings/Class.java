package com.ownedthx.xmldoclet.xmlbindings;

public class Class
{
    public String name;

    public String qualifiedName;
    
    public String comment;

    /** isIncluded indicates if this type is defined within the current javadoc */
    public boolean isIncluded;

    public boolean isSerializable;

    public boolean isExternalizable;

    public boolean isAbstract;

    public boolean isException;

    public boolean isError;
    
    public String scope;

    public String superClass;

    public String[] interfaces;

    public Constructor[] constructors;

    public Method[] methods;

    public Field[] fields;

    public TypeVar[] typeVariables;
    
    public AnnotationInstance[] annotationInstances;
    // TODO
    // figure out TypeParameters.  Seems to be related only to generics
}
