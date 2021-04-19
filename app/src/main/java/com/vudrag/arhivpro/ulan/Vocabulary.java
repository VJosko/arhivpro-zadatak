package com.vudrag.arhivpro.ulan;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

@Root(name="Vocabulary")
public class Vocabulary {

    @Attribute(name="noNamespaceSchemaLocation", required=false)
    URL noNamespaceSchemaLocation;

    @Element(name="Count", required=false)
    String count;

    @ElementList(name="Subject", required=false, entry="Subject", inline = true)
    List<Subject> subject;

    public URL getNoNamespaceSchemaLocation() {return this.noNamespaceSchemaLocation;}
    public void setNoNamespaceSchemaLocation(URL value) {this.noNamespaceSchemaLocation = value;}

    public String getCount() {return this.count;}
    public void setCount(String value) {this.count = value;}

    public List<Subject> getSubject() {return this.subject;}
    public void setSubject(List<Subject> value) {this.subject = value;}

    public static class PreferredTerm implements Serializable {

        @Attribute(name="termid", required=false)
        Integer termid;

        @Text(required=false)
        String textValue;

        public Integer getTermid() {return this.termid;}
        public void setTermid(Integer value) {this.termid = value;}

        public String getTextValue() {return this.textValue;}
        public void setTextValue(String value) {this.textValue = value;}

    }

    public static class Term implements Serializable {

        @Attribute(name="termid", required=false)
        Integer termid;

        @Text(required=false)
        String textValue;

        public Integer getTermid() {return this.termid;}
        public void setTermid(Integer value) {this.termid = value;}

        public String getTextValue() {return this.textValue;}
        public void setTextValue(String value) {this.textValue = value;}

    }

    public static class Subject implements Serializable {

        @Element(name="Preferred_Term", required=false)
        PreferredTerm preferredTerm;

        @Element(name="Preferred_Biography", required=false)
        String preferredBiography;

        @ElementList(name="Term", required=false, entry="Term", inline=true)
        List<Term> term;

        @Element(name="Subject_ID", required=false)
        String subjectID;

        @Element(name="Preferred_Parent", required=false)
        String preferredParent;

        public PreferredTerm getPreferredTerm() {return this.preferredTerm;}
        public void setPreferredTerm(PreferredTerm value) {this.preferredTerm = value;}

        public String getPreferredBiography() {return this.preferredBiography;}
        public void setPreferredBiography(String value) {this.preferredBiography = value;}

        public List<Term> getTerm() {return this.term;}
        public void setTerm(List<Term> value) {this.term = value;}

        public String getSubjectID() {return this.subjectID;}
        public void setSubjectID(String value) {this.subjectID = value;}

        public String getPreferredParent() {return this.preferredParent;}
        public void setPreferredParent(String value) {this.preferredParent = value;}

    }

}