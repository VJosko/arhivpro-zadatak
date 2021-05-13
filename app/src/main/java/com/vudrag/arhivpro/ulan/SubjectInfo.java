package com.vudrag.arhivpro.ulan;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

@Root(name="Vocabulary", strict = false)
public class SubjectInfo {

    @Element(name="Subject", required=false)
    public Subject Subject;
    @Element(required = false)
    public String xsi;
    @Attribute(name="noNamespaceSchemaLocation", required=false)
    public String noNamespaceSchemaLocation;

    public static class Subject implements Serializable{
        @Attribute(required = false)
        public String Subject_ID;
        @Element(required = false)
        public Terms Terms;
        @Element(required = false)
        public Biographies Biographies;
        @Element(required = false)
        public Roles Roles;
        @Element(required = false)
        public Nationalities Nationalities;
    }

    public static class Terms implements Serializable{
        @Element(required = false)
        public Preferred_Term Preferred_Term;
    }

    public static class Preferred_Term implements Serializable{
        @Element(required = false)
        public String Term_Text;
    }

    public static class Biographies implements Serializable{
        @Element(required = false)
        public Preferred_Biography Preferred_Biography;
    }

    public static class Preferred_Biography implements Serializable{
        @Element(required = false)
        public String Birth_Place;
        @Element(required = false)
        public String Birth_Date;
        @Element(required = false)
        public String Death_Date;
        @Element(required = false)
        public String Sex;
    }

    public static class Roles implements Serializable{
        @Element(required = false)
        public Preferred_Role Preferred_Role;
    }

    public static class Preferred_Role implements Serializable{
        @Element(required = false)
        public String Role_ID;
    }

    public static class Nationalities implements Serializable{
        @Element(required = false)
        public Preferred_Nationality Preferred_Nationality;
    }

    public static class Preferred_Nationality implements Serializable{
        @Element(required = false)
        public String Nationality_Code;
    }

}