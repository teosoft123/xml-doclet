package com.ownedthx.xmldoclet.xmlbindings;

/** Coments on AnnotationInstance */
public class AnnotationInstance
{
    /**
     * This parameter can be null if the annotation is not included
     * in the parsed source, and if the classpath doesn't contain
     * .class/.jar that defines the annotation
     */
    public String name;

    /**
     * This parameter can be null if the annotation is not included
     * in the parsed source, and if the classpath doesn't contain
     * .class/.jar that defines the annotation
     */
    public String qualifiedName;

    public AnnotationArgument[] arguments;
}
