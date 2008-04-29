package com.ownedthx.xmldoclet.xmlbindings;

public class Interface
{
    public String name;

    public String qualifiedName;
    
    public String comment;

    /** isIncluded indicates if this type is defined within the current javadoc */
    public boolean isIncluded;
    
    public Method[] methods;

    public String scope;

    public TypeVar[] typeVariables;
    
    public String[] interfaces;

    public AnnotationInstance[] annotationInstances;
}