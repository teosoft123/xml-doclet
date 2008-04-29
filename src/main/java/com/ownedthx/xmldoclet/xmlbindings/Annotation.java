package com.ownedthx.xmldoclet.xmlbindings;

public class Annotation
{
    public String name;

    public String qualifiedName;

    public String comment;

    public String scope;

    /** isIncluded indicates if this type is defined within the current javadoc */
    public boolean isIncluded;

    public AnnotationElement[] elements;

    public AnnotationInstance[] annotationInstances;
    // TODO
    // figure out TypeParameters.  Seems to be related only to generics
}