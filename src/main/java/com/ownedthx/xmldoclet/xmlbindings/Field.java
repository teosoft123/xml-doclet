package com.ownedthx.xmldoclet.xmlbindings;

public class Field
{
    public String name;

    public TypeInfo type;

    public String comment;

    public boolean isVolatile;

    public boolean isTransient;
    
    public boolean isStatic;

    public boolean isFinal;

    public String finalExpression;

    public String scope;
                                   
    public AnnotationInstance[] annotationInstances;
}
