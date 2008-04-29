package com.ownedthx.xmldoclet.xmlbindings;

public class TypeInfo
{
    public String qualifiedName;

    /** Null indicates that type is not a wildcard */
    public WildCardInfo wildcard;

    /** Null indicates that type has no generics/wildcard info */
    public GenericsInfo generics;

    /** A non-array is an empty-string */
    public String dimension;
}