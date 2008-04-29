package com.ownedthx.xmldoclet.parser;

import com.ownedthx.xmldoclet.xmlbindings.*;
import com.ownedthx.xmldoclet.xmlbindings.Package;
import com.ownedthx.xmldoclet.xmlbindings.Enum;
import com.ownedthx.xmldoclet.xmlbindings.Class;

import java.util.ArrayList;


/**
 * This class is just a simple storage utility for collecting classes, interfaces,
 * annotation, enums found while parsing the code.  
 */
public class ParserMediary
{
    protected Package thePackage;

    protected ArrayList<Class> classList;
    protected ArrayList<Interface> interfaceList;
    protected ArrayList<Annotation> annotationList;
    protected ArrayList<Enum> enumList;

    public ParserMediary(String packageName, String packageComment, AnnotationInstance[] annotations)
    {
        thePackage = new Package();
        thePackage.name = packageName;
        thePackage.comment = packageComment;
        thePackage.annotationInstances = annotations;

        this.classList = new ArrayList<Class>();
        this.interfaceList = new ArrayList<Interface>();
        this.annotationList = new ArrayList<Annotation>();
        this.enumList = new ArrayList<Enum>();
    }

    /**
     * Once xmldoclet is done processing the code, this method is used to return an
     * easy-to-use data structure containing the classes, interfaces, annotations, and enums
     * @return
     */
    public Package wrapup()
    {
        if(classList.size() > 0)
        {
            thePackage.classes = classList.toArray(new com.ownedthx.xmldoclet.xmlbindings.Class[]{});
        }

        if(enumList.size() > 0)
        {
            thePackage.enums = enumList.toArray(new Enum[] {});
        }

        if(interfaceList.size() > 0)
        {
            thePackage.interfaces = interfaceList.toArray(new Interface[] {});
        }

        if(annotationList.size() > 0)
        {
            thePackage.annotations = annotationList.toArray(new Annotation[]{});
        }
        
        return thePackage;
    }

    /**
     * Everytime xmldoclet finds and constructs a Class, it adds it using this method
     * @param docClass
     */
    public void addClass(Class docClass)
    {
        classList.add(docClass);
    }

    /**
     * Everytime xmldoclet finds and constructs a Interface, it adds it using this method
     * @param docInterface
     */
    public void addInterface(Interface docInterface)
    {
        interfaceList.add(docInterface);
    }

    /**
     * Everytime xmldoclet finds and constructs a Enum, it adds it using this method
     * @param docEnum
     */
    public void addEnum(Enum docEnum)
    {
        enumList.add(docEnum);
    }

    /**
     * Everytime xmldoclet finds and constructs a Annotation, it adds it using this method
     * @param annotation
     */
    public void addAnnotation(Annotation annotation)
    {
        annotationList.add(annotation);
    }
}
