package com.ownedthx.xmldoclet.parser;

import com.sun.javadoc.*;
import com.sun.javadoc.Parameter;
import com.ownedthx.xmldoclet.xmlbindings.*;
import com.ownedthx.xmldoclet.xmlbindings.Package;
import com.ownedthx.xmldoclet.xmlbindings.Class;
import com.ownedthx.xmldoclet.xmlbindings.Enum;

import java.util.*;

import java.security.*;

import org.apache.log4j.Logger;

// TODO
// 1. -overview option support?
// 2. doc-info (binary data/images, etc) support ?

/**
 * This class comprises the logical bulk of xmldoclet
 */
public class Parser
{
    protected static Root rootXml;
    protected static java.security.MessageDigest md5;
    protected static Map<PackageDoc, ParserMediary> processingStorage;

    static Logger log = Logger.getLogger(Parser.class.getName());

    /**
     * The entry point into the Parser class.
     * @param root A RootDoc intstance obtained via the doclet API
     * @return A XML (XStream) serializable element, containing everything parsed from javadoc doclet
     */
    public static Root ParseRoot(RootDoc root)
    {
        processingStorage = new HashMap<PackageDoc, ParserMediary>();
        
        try
        {
            md5 = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e)
        {
            log.error("unable to acquire MD5 algorithm", e);
            return null;
        }


        rootXml = new Root();

        ClassDoc[] allClasses = root.classes();

        for(ClassDoc classDoc : allClasses)
        {
            PackageDoc doc = classDoc.containingPackage();

            ParserMediary mediary = null;
            
            // the age old 'if I have it pull out existing, if I don't make a new one'
            if(processingStorage.containsKey(doc))
            {
                mediary = processingStorage.get(doc);
            }
            else
            {
                mediary = new ParserMediary(doc.name(),
                        doc.commentText(),
                        ParseAnnotationInstances(doc.annotations()));

                processingStorage.put(doc, mediary);
            }


            if(classDoc.isIncluded())
            {
                // dev comment--why do enums show up as ordinary class?
                if(classDoc.isOrdinaryClass() || classDoc.isException() || classDoc.isError())
                {
                    mediary.addClass(ParseClass(classDoc));
                }
                else if(classDoc.isEnum())
                {
                    mediary.addEnum(ParseEnum(classDoc));
                }
                else if(isAnnotation(classDoc))
                {
                    mediary.addAnnotation(ParseAnnotation(classDoc));
                }
                else if(classDoc.isInterface())
                {
                    mediary.addInterface(ParseInterface(classDoc));
                }
            }
            else
            {
                log.debug("Skipping not-included class " + classDoc.qualifiedName());
            }
        }

        if(processingStorage.size() > 0)
        {
            List list = new ArrayList<Package>();

            for(ParserMediary mediary : processingStorage.values())
            {
               list.add(mediary.wrapup()); 
            }
            
            rootXml.packages = (Package[]) list.toArray(new Package[] {});
        }
        else
        {
            log.warn("No packages found!");
        }

        return rootXml;
    }

    /**
     * Parses the data for a class type definition
     * @param docClass
     * @return
     */
    protected static Class ParseClass(ClassDoc docClass)
    {
        assert(docClass != null);

        Class xmlClass = new Class();

        // illegal use of this class.
        assert(xmlClass != null);    

        xmlClass.name = docClass.name();
        xmlClass.qualifiedName = docClass.qualifiedName();
        xmlClass.isSerializable = docClass.isSerializable();
        xmlClass.isExternalizable = docClass.isExternalizable();
        xmlClass.isAbstract = docClass.isAbstract();
        xmlClass.isException = docClass.isException();
        xmlClass.isError = docClass.isError();
        xmlClass.comment = docClass.commentText();
        xmlClass.scope = DetermineScope(docClass);
        xmlClass.isIncluded = docClass.isIncluded();
        xmlClass.typeVariables = ParseTypeVariables(docClass.typeParameters());
        Type superClassType = docClass.superclassType();
        if(superClassType != null)
        {
            xmlClass.superClass = superClassType.qualifiedTypeName();
        }

        Type[] interfaces = docClass.interfaceTypes();

        ArrayList<String> interfaceTypeNames = new ArrayList<String>();
        if(interfaces != null && interfaces.length > 0)
        {
            for(Type interfaceType : interfaces)
            {
                interfaceTypeNames.add(interfaceType.qualifiedTypeName());
            }
            
            xmlClass.interfaces = interfaceTypeNames.toArray(new String[] {});
        }

        ConstructorDoc[] constructors = docClass.constructors();

        if(constructors != null && constructors.length > 0)
        {
            ArrayList<Constructor> constructorList = new ArrayList<Constructor>();

            for(ConstructorDoc constructor : constructors)
            {
                constructorList.add(ParseConstructor(constructor));
            }

            xmlClass.constructors = constructorList.toArray(new Constructor[] {}) ;
        }
        else
        {
            log.debug("No constructors in class: " + docClass.name());
        }

        MethodDoc[] methods = docClass.methods();

        if(methods != null && methods.length > 0)
        {
            ArrayList<Method> methodList = new ArrayList<Method>();

            for(MethodDoc method : methods)
            {
                methodList.add(ParseMethod(method));
            }

            xmlClass.methods = methodList.toArray(new Method[] {}) ;
        }
        else
        {
            log.debug("No methods in class: " + docClass.name());
        }

        FieldDoc[] fields = docClass.fields();

        if(fields != null && fields.length > 0)
        {
            ArrayList<Field> fieldList = new ArrayList<Field>();

            for(FieldDoc field : fields)
            {
                fieldList.add(ParseField(field));
            }

            xmlClass.fields = fieldList.toArray(new Field[] {});
        }

        xmlClass.annotationInstances = ParseAnnotationInstances(docClass.annotations());
        return xmlClass;
    }

    /**
     * Parses an annotation element
     * @param elementDoc
     * @return
     */
    protected static AnnotationElement ParseAnnotationElement(AnnotationTypeElementDoc elementDoc)
    {
        // AnnotationTypeElementDoc's are basically methods.

        AnnotationElement element = new AnnotationElement();
        element.name = elementDoc.name();
        AnnotationValue value = elementDoc.defaultValue();
        if(value != null)
        {
            element.defaultValue = value.toString();
        }
        element.qualifiedName = elementDoc.qualifiedName();
        element.type = elementDoc.returnType().qualifiedTypeName();

        return element;
    }


    /**
     * Parse type variables for generics
     * @param variables
     * @return
     */
    protected static TypeVar[] ParseTypeVariables(TypeVariable[] variables)
    {
        TypeVar[] vars = null;

        if(variables != null && variables.length > 0)
        {
            ArrayList<TypeVar> varsList = new ArrayList<TypeVar>();

            for(TypeVariable variable : variables)
            {
                TypeVar var = new TypeVar();
                var.name = variable.typeName();
                Type[] bounds = variable.bounds();
                if(bounds != null && bounds.length > 0)
                {
                    ArrayList<String> list = new ArrayList<String>();
                    
                    for(Type bound : bounds)
                    {
                        list.add(bound.qualifiedTypeName());
                    }

                    var.bounds = list.toArray(new String[] {});
                }

                varsList.add(var);
            }

            vars = varsList.toArray(new TypeVar[] {});
        }

        return vars;
    }
    /** Parses annotation instances from the javadoc annotation instance type
     * @param annotationDocs Annotations decorated on some type
     * @return Serializable representation of annotations
     */
    protected static AnnotationInstance[] ParseAnnotationInstances(AnnotationDesc[] annotationDocs)
    {
        AnnotationInstance[] annotations = null;

        if(annotationDocs != null && annotationDocs.length > 0)
        {
            ArrayList<AnnotationInstance> list = new ArrayList<AnnotationInstance>();

            for(AnnotationDesc annot : annotationDocs)
            {
                AnnotationInstance instance = new AnnotationInstance();
                            
                instance.name = annot.annotationType().name();
                instance.qualifiedName = annot.annotationType().qualifiedTypeName();
                AnnotationDesc.ElementValuePair[] arguments = annot.elementValues();
                if(arguments != null && arguments.length > 0)
                {
                    ArrayList<AnnotationArgument> argumentList = new ArrayList<AnnotationArgument>();

                    for(AnnotationDesc.ElementValuePair pair : arguments)
                    {
                        AnnotationArgument annotationArgument = new AnnotationArgument();
                        annotationArgument.name = pair.element().name();
                        annotationArgument.value = pair.value().toString();

                        argumentList.add(annotationArgument);
                    }

                    instance.arguments = argumentList.toArray(new AnnotationArgument[] {});
                }

                list.add(instance);
            }

            annotations = list.toArray(new AnnotationInstance[] {});
        }

        return annotations;
    }

    /**
     * Parses an interface type definition
     * @param docClass
     * @return
     */
    protected static Interface ParseInterface(ClassDoc docClass)
    {
        assert(docClass != null);

        Interface xmlInterface = new Interface();

        xmlInterface.name = docClass.name();
        xmlInterface.qualifiedName = docClass.qualifiedName();
        xmlInterface.comment = docClass.commentText();
        xmlInterface.isIncluded = docClass.isIncluded();
        xmlInterface.scope = DetermineScope( docClass );
        xmlInterface.typeVariables = ParseTypeVariables( docClass.typeParameters() );

        Type[] interfaces = docClass.interfaceTypes();

        ArrayList<String> interfaceTypeNames = new ArrayList<String>();
        if(interfaces != null && interfaces.length > 0)
        {
            for(Type interfaceType : interfaces)
            {
                interfaceTypeNames.add(interfaceType.qualifiedTypeName());
            }

            xmlInterface.interfaces = interfaceTypeNames.toArray(new String[] {});            
        }

        MethodDoc[] methods = docClass.methods();

        if(methods != null && methods.length > 0)
        {
            ArrayList<Method> methodList = new ArrayList<Method>();

            for(MethodDoc method : methods)
            {
                methodList.add(ParseMethod(method));
            }

            xmlInterface.methods = methodList.toArray(new Method[] {}) ;
        }
        else
        {
            log.debug("No methods in interface: " + docClass.name());
        }

        xmlInterface.annotationInstances = ParseAnnotationInstances(docClass.annotations());
        return xmlInterface;
    }

    /**
     * Parses an annotation type definition
     * @param docClass
     * @return
     */
    protected static Annotation ParseAnnotation(ClassDoc docClass)
    {
        AnnotationTypeDoc docAnnotation = (AnnotationTypeDoc) docClass;

        assert(docAnnotation != null);

        Annotation xmlAnnotation = new Annotation();

        xmlAnnotation.name = docClass.name();
        xmlAnnotation.qualifiedName = docClass.qualifiedName();
        xmlAnnotation.comment = docClass.commentText();
        xmlAnnotation.isIncluded = docClass.isIncluded();
        xmlAnnotation.scope = DetermineScope( docClass );

        AnnotationTypeElementDoc[] elements = docAnnotation.elements();

        if(elements != null && elements.length > 0)
        {
            ArrayList<AnnotationElement> elementList = new ArrayList<AnnotationElement>();

            for(AnnotationTypeElementDoc element : elements)
            {
                elementList.add(ParseAnnotationElement(element));
            }

            xmlAnnotation.elements = elementList.toArray(new AnnotationElement[] {}) ;
        }
        else
        {
            log.debug("No elements in annotation: " + docClass.name());
        }

        xmlAnnotation.annotationInstances = ParseAnnotationInstances(docClass.annotations());
        return xmlAnnotation;
    }

    /**
     * Parses the enum type definition
     * @param docClass
     * @return
     */
    protected static Enum ParseEnum(ClassDoc docClass)
    {
        assert(docClass != null);

        Enum xmlEnum = new Enum();

        xmlEnum.name = docClass.name();
        xmlEnum.qualifiedName = docClass.qualifiedName();
        xmlEnum.comment = docClass.commentText();
        xmlEnum.isIncluded = docClass.isIncluded();
        xmlEnum.scope = DetermineScope(docClass);
        Type superClassType = docClass.superclassType();
        if(superClassType != null)
        {
            xmlEnum.superClass = superClassType.qualifiedTypeName();
        }

        Type[] interfaces = docClass.interfaceTypes();

        ArrayList<String> interfaceTypeNames = new ArrayList<String>();
        if(interfaces != null && interfaces.length > 0)
        {
            for(Type interfaceType : interfaces)
            {
                interfaceTypeNames.add(interfaceType.qualifiedTypeName());
            }
        }

        xmlEnum.extendedFrom = interfaceTypeNames.toArray(new String[] {});

        FieldDoc[] fields = docClass.enumConstants();

        if(fields != null && fields.length > 0)
        {
            ArrayList<EnumField> fieldList = new ArrayList<EnumField>();

            for(FieldDoc field : fields)
            {
                fieldList.add(ParseEnumField(field));
            }

            xmlEnum.fields = fieldList.toArray(new EnumField[] {});
        }

        xmlEnum.annotationInstances = ParseAnnotationInstances(docClass.annotations());
        return xmlEnum;
    }

    /**
     * Parses a constructor type definition
     * @param docConstructor
     * @return
     */
    protected static Constructor ParseConstructor(ConstructorDoc docConstructor)
    {
        assert(docConstructor != null);

        Constructor xmlConstructor = new Constructor();
  
        xmlConstructor.name = docConstructor.name();
        xmlConstructor.comment = docConstructor.commentText();

        Parameter[] parameters = docConstructor.parameters();

        if(parameters != null && parameters.length > 0)
        {
            ParamTag[] paramComments = docConstructor.paramTags();

            ArrayList<Param> methodList = new ArrayList<Param>();

            for(Parameter parameter : parameters)
            {
                ParamTag paramComment = null;

                // look to see if this parameter has comments
                // if so, paramComment will be set
                for(ParamTag testParam : paramComments)
                {
                    String testParamName = testParam.parameterName();
                    if(testParamName != null)
                    {
                        if(testParamName.compareTo(parameter.name()) == 0)
                        {
                            paramComment = testParam;
                            break;
                        }
                    }
                }

                methodList.add(ParseParameter(parameter, paramComment));
            }

            xmlConstructor.parameters = methodList.toArray(new Param[] {});
        }
        else
        {
            log.debug("No parameters for method: " + docConstructor.name());
        }

        // parse annotations for the constructor
        xmlConstructor.annotationInstances = ParseAnnotationInstances(docConstructor.annotations());

        return xmlConstructor;
    }

    /**
     * Parses a method type definition
     * @param docMethod
     * @return
     */
    protected static Method ParseMethod(MethodDoc docMethod)
    {
        assert(docMethod != null);

        Method xmlMethod = new Method();

        xmlMethod.name = docMethod.name();
        xmlMethod.hash = computeHash(docMethod.qualifiedName(), docMethod.signature());
        xmlMethod.qualifiedName = docMethod.qualifiedName();
        xmlMethod.comment = docMethod.commentText();
        xmlMethod.signature = docMethod.signature();
        xmlMethod.isNative = docMethod.isNative();
        xmlMethod.isVarArgs = docMethod.isVarArgs();
        xmlMethod.isSynchronized = docMethod.isSynchronized();
        xmlMethod.isFinal = docMethod.isFinal();
        xmlMethod.isStatic = docMethod.isStatic();
        
        xmlMethod.scope = DetermineScope(docMethod);

        // Parse parameters of the method
        Parameter[] parameters = docMethod.parameters();

        if(parameters != null && parameters.length > 0)
        {
            ParamTag[] paramComments = docMethod.paramTags();

            ArrayList<Param> paramList = new ArrayList<Param>();

            for(Parameter parameter : parameters)
            {
                ParamTag paramComment = null;

                // look to see if this parameter has comments
                // if so, paramComment will be set
                for(ParamTag testParam : paramComments)
                {
                    String testParamName = testParam.parameterName();
                    if(testParamName != null)
                    {
                        if(testParamName.compareTo(parameter.name()) == 0)
                        {
                            paramComment = testParam;
                            break;
                        }
                    }
                }

                paramList.add(ParseParameter(parameter, paramComment));
            }

            xmlMethod.parameters = paramList.toArray(new Param[] {});
        }
        else
        {
            log.debug("No parameters for method: " + docMethod.name());
        }

        // Parse result data

        Result returnInfo = new Result();

        Tag[] returnTags = docMethod.tags("@return");
        if(returnTags != null && returnTags.length > 0)
        {
            // there should be only one return tag.  but heck,
            // if they specify two, so what...
            StringBuilder builder = new StringBuilder();
            for(Tag returnTag : returnTags)
            {
                String returnTagText = returnTag.text();
                if(returnTagText != null)
                {
                    builder.append(returnTagText);
                    builder.append("\n");
                }
            }

            returnInfo.comment = builder.substring(0, builder.length() - 1);
        }

        returnInfo.type = ParseType(docMethod.returnType());
        xmlMethod.result = returnInfo;

        // Parse exceptions of the method

        Type[] types = docMethod.thrownExceptionTypes();
        ThrowsTag[] exceptionComments = docMethod.throwsTags();

        if(types != null && types.length > 0)
        {
            ArrayList<ExceptionInstance> exceptionList = new ArrayList<ExceptionInstance>();

            for(Type exceptionType : types)
            {
                ExceptionInstance exception = new ExceptionInstance();

                exception.type = ParseType(exceptionType);

                for(ThrowsTag exceptionComment : exceptionComments)
                {
                    if(exceptionType == exceptionComment.exceptionType())
                    {
                        exception.comment = exceptionComment.exceptionComment();

                        ClassDoc exceptionDetails = exceptionComment.exception();

                        // not yet parsing Exceptions defined within the supplied code set
                        exception.type = ParseType(exceptionComment.exceptionType());
                        break;
                    }
    
                    }

                exceptionList.add(exception);
            }

            xmlMethod.exceptions = exceptionList.toArray(new ExceptionInstance[] { });
        }

        // parse annotations from the method
        xmlMethod.annotationInstances = ParseAnnotationInstances(docMethod.annotations());
        
        return xmlMethod;
    }


    /**
     * Parses a field type definition
     * @param docField
     * @return
     */
    protected static Field ParseField(FieldDoc docField)
    {
        assert(docField != null);

        Field xmlField = new Field();

        xmlField.name = docField.name();
        xmlField.comment = docField.commentText();
        xmlField.type = ParseType(docField.type());
        xmlField.isFinal = docField.isFinal();
        if(xmlField.isFinal)
        {
            xmlField.finalExpression = docField.constantValueExpression();
        }
        else if(docField.constantValueExpression() != null)
        {
            // how would a non-final field have a constant value expression?
            // my understanding is that this field is only != null when is not final
            assert(false);
        }
        xmlField.isStatic = docField.isStatic();
        xmlField.isVolatile = docField.isVolatile();
        xmlField.isTransient = docField.isTransient();
        xmlField.scope = DetermineScope(docField);

        // parse annotations from the field
        xmlField.annotationInstances = ParseAnnotationInstances(docField.annotations());

        return xmlField;
    }


    /**
     * Parses an enum type definition
     * @param docField
     * @return
     */
    protected static EnumField ParseEnumField(FieldDoc docField)
    {
        assert(docField != null);

        EnumField xmlEnumField = new EnumField();

        xmlEnumField.name = docField.name();
        xmlEnumField.comment = docField.commentText();

        return xmlEnumField;
    }

    /**
     * Parses a method parameter type definition
     * @param docParameter
     * @param paramComment
     * @return
     */
    protected static Param ParseParameter(Parameter docParameter, ParamTag paramComment)
    {
        assert(docParameter != null);

        Param xmlParameter = new Param();
        xmlParameter.name = docParameter.name();

        xmlParameter.type = ParseType(docParameter.type());
                
        if(paramComment != null)
        {
             xmlParameter.comment = paramComment.parameterComment();  
        }

        xmlParameter.annotationInstances = ParseAnnotationInstances(docParameter.annotations());
        return xmlParameter;
    }

    /**
     * Parses a type definition
     * @param type
     * @return
     */
    protected static TypeInfo ParseType(Type type)
    {
        TypeInfo typeInfo = new TypeInfo();
        typeInfo.qualifiedName = type.qualifiedTypeName();
        typeInfo.dimension = type.dimension();
        typeInfo.wildcard = ParseWildCard(type.asWildcardType());
        typeInfo.generics = ParseGenerics(type.asParameterizedType());
        return typeInfo;
    }

    /**
     * Parses wildcard info from type definitions
     * @param wildcard
     * @return
     */
    protected static WildCardInfo ParseWildCard(WildcardType wildcard)
    {
        WildCardInfo wildcardInfo = null;

        if(wildcard != null)
        {
            wildcardInfo = new WildCardInfo();

            Type[] extendTypes = wildcard.extendsBounds();

            if(extendTypes != null && extendTypes.length > 0)
            {
                TypeInfo[] extendInfo = new TypeInfo[extendTypes.length];
                for(int i = 0; i < extendTypes.length; i++)
                {
                    extendInfo[i] = ParseType(extendTypes[i]);
                }
                wildcardInfo.extendsBounds = extendInfo;
            }

            Type[] superTypes = wildcard.superBounds();

            if(superTypes != null && superTypes.length > 0)
            {
                TypeInfo[] superInfo = new TypeInfo[superTypes.length];
                for(int i = 0; i < superTypes.length; i++)
                {
                    superInfo[i] = ParseType(superTypes[i]);
                }
                wildcardInfo.superBounds = superInfo;
            }
        }

        return wildcardInfo;
    }

    protected static GenericsInfo ParseGenerics(ParameterizedType parameterized)
    {
        GenericsInfo generics = null;

        if(parameterized != null && parameterized.typeArguments() != null && parameterized.typeArguments().length > 0)
        {
            generics = new GenericsInfo();

            Type[] types = parameterized.typeArguments();

            TypeInfo[] typeInfoList = new TypeInfo[types.length];

            for(int i = 0; i < types.length; i++)
            {
                typeInfoList[i] = ParseType(types[i]);       
            }

            generics.typeArguments = typeInfoList;
        }

        return generics;
    }

    /**
     * Returns string representation of scope
     * @param doc
     * @return
     */
    protected static String DetermineScope(ProgramElementDoc doc)
    {
        ScopeModifier scope;

        if(doc.isPrivate())
        {
            scope = ScopeModifier.PRIVATE;
        }
        else if(doc.isProtected())
        {
            scope = ScopeModifier.PROTECTED;
        }
        else if(doc.isPackagePrivate())
        {
            scope = ScopeModifier.PACKAGEPRIVATE;
        }
        else if(doc.isPublic())
        {
            scope = ScopeModifier.PUBLIC;
        }
        else
        {
            log.error("No scope defined for: " + doc.name());
            scope = ScopeModifier.PACKAGEPRIVATE;
        }

        return scope.toString().toLowerCase();
    }


    /**
     * Computing the hash for a method is useful so as to create an alternate key
     * from the method name + signature.
     * @param qualifiedMethodName
     * @param methodSignature
     * @return
     */
    public static String computeHash(String qualifiedMethodName, String methodSignature)
    {
        String fullMethodName = qualifiedMethodName + methodSignature;

        byte[] bytes = fullMethodName.getBytes();
        md5.reset();
        md5.update(bytes);

        byte[] messageBytes = md5.digest();
        
        StringBuffer message = new StringBuffer();
        for (byte aByte : messageBytes)
        {
            String expanded = Integer.toHexString(0xFF & aByte);
            if(expanded.length() == 1)
            {
                // normalize length of all strings
                message.append("0");
            }

           message.append(expanded);
        }

        return message.toString();
    }

    /**
     * Determinse if a specified class definition is a Annotation
     * @param classDoc
     * @return
     */
    protected static boolean isAnnotation(ClassDoc classDoc)
    {
        boolean isAnnotation = false;
        if(classDoc != null)
        {
            Type[] types = classDoc.interfaceTypes();
            if(types != null)
            {
                for(Type type : types)
                {
                    isAnnotation = 0 == type.qualifiedTypeName().compareTo(java.lang.annotation.Annotation.class.getName());
                    if(isAnnotation)
                    {
                        break;
                    }
                }
            }
        }

        return isAnnotation;
    }
}
                                                  