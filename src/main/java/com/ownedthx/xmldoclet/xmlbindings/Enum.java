package com.ownedthx.xmldoclet.xmlbindings;

public class Enum
{
    public String name;

    public String qualifiedName;

    public String comment;

    /** isIncluded indicates if this type is defined within the current javadoc */
    public boolean isIncluded;

    public EnumField[] fields;
    
    public AnnotationInstance[] annotationInstances;

    public String scope;

    public String superClass;

    public String[] extendedFrom;
}
