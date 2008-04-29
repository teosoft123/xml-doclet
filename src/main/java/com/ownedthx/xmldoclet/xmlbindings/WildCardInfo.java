package com.ownedthx.xmldoclet.xmlbindings;

/** Used to convey info about any wild card info on the type */
public class WildCardInfo
{
    /** List<? extends String> */
    public TypeInfo[] extendsBounds;

    /** List<? super String> */
    public TypeInfo[] superBounds;
}
