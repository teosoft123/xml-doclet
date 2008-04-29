package com.ownedthx.xmldoclet.xmlbindings;

/** Coments on Method */
public class Method
{
    public String name;

    public String qualifiedName;
    
    public String comment;

    public String signature;

    public String hash;
    
    public boolean isFinal;

    public boolean isStatic;

    public boolean isNative;

    public boolean isSynchronized;

    public boolean isVarArgs;

    public String scope;
    
    public Param[] parameters;

    public Result result;

    public ExceptionInstance[] exceptions;

    public AnnotationInstance[] annotationInstances;

}

