package com.vudrag.arhivpro.ulan;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

import retrofit2.http.Url;

@Root(name="RDF", strict = false)
public class SubjectDbpedia {

    @Element(name = "thumbnail")
    public Thumbnail thumbnail;

    public static class Thumbnail implements Serializable{
        @Attribute(name = "resource", required = false)
        public String thumbnail;
    }


}
