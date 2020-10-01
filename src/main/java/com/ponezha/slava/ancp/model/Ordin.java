package com.ponezha.slava.ancp.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ordins")
public class Ordin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String url;
    @ElementCollection
    @CollectionTable(name = "ordin_casenumbers") // 2
    @Column(name = "casenumber")

    private List<String> caseNumbers;

    public Ordin() {
    }

    public int getId() {
        return this.id;
    }

    public String getUrl() {
        return url;
    }

    public Ordin setUrl(String url) {
        this.url = url;
        return this;
    }

    public List<String> getCaseNumbers() {
        return caseNumbers;
    }

    public Ordin setCaseNumbers(List<String> caseNumbers) {
        this.caseNumbers = caseNumbers;
        return this;
    }
}
