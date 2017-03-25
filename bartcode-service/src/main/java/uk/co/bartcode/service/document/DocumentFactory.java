package uk.co.bartcode.service.document;

public interface DocumentFactory {

    boolean supports(String file);

    Document create(String file);

}
