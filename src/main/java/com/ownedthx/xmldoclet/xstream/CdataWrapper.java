package com.ownedthx.xmldoclet.xstream;

import com.thoughtworks.xstream.io.xml.*;
import com.thoughtworks.xstream.core.util.*;

import java.io.Writer;

/**
 * Custom XStream writer.  Used to wrap all element named
 * 'comment' with CDATA blocks.
 * In other words, <![CDATA[  _CONTENT_ [[>
 *
 * The justification for this is readability.  While it would be
 * perfectly technically valid to escape the content and not use CDATA,
 * this makes the most human readable content (the comments) readably
 * from the XML output because there is minimal escaping.
 */
public class CdataWrapper extends PrettyPrintWriter
{
    private boolean toBeWrapped = false;

    /**
     * Fired everytime a node is about to be emitted.
     * If the node's name is 'comment', we flag toBeWrapped to true
     * @param s The name of the node.
     */
    public void startNode(String s)
    {
        if(s != null && s.equals("comment"))
        {
            toBeWrapped = true;
        }
        super.startNode(s);
    }

    /**
     * Fired everytime a node is closed.
     * We always reset toBeWrapped to false.
     */
    public void endNode()
    {
        toBeWrapped = false;
        super.endNode();
    }

    /**
     * If toBeWrapped is true, then we wrap up the content with
     * the correct CDATA start/end markers. Also, we escape the CDATA end tag,
     * if present in the data.
     * @param quickWriter
     * @param s
     */
    protected void writeText(QuickWriter quickWriter, String s)
    {
        if(toBeWrapped && s != null)
        {
            quickWriter.write("<![CDATA[");
            // XStream doesn't escape the > in ]]>.
            // Let's do it for them!
            s = s.replaceAll("]]>", "]]&gt;");
            quickWriter.write(s);
            quickWriter.write("]]>");
        }
        else
        {
            super.writeText(quickWriter, s);
        }
    }

    // --------- CONSTRUCTORS ---------==

    public CdataWrapper(Writer writer, char[] chars, String s, XmlFriendlyReplacer xmlFriendlyReplacer)
    {
        super(writer, chars, s, xmlFriendlyReplacer);
    }

    public CdataWrapper(Writer writer, char[] chars, String s)
    {
        super(writer, chars, s);
    }

    public CdataWrapper(Writer writer, char[] chars)
    {
        super(writer, chars);
    }

    public CdataWrapper(Writer writer, String s, String s1)
    {
        super(writer, s, s1);
    }

    public CdataWrapper(Writer writer, String s)
    {
        super(writer, s);
    }

    public CdataWrapper(Writer writer, XmlFriendlyReplacer xmlFriendlyReplacer)
    {
        super(writer, xmlFriendlyReplacer);
    }

    public CdataWrapper(Writer writer)
    {
        super(writer);
    }
}
