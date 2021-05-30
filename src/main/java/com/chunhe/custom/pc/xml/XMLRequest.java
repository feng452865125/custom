package com.chunhe.custom.pc.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

@Data
@XStreamAlias("XMLRequest")
public class XMLRequest {
    public XMLParams XMLParams;
    public XMLMetaData XMLMetaData;

    @XStreamAsAttribute
    public  String Version;
    @XStreamAsAttribute
    public  String Action;

    public XMLRequest() {

    }

    public XMLRequest(String Version, String Action) {
        this.Version = Version;
        this.Action = Action;
    }
}
