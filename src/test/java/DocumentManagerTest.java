import task.DocumentManager;


import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DocumentManagerTest {

    @Test
    void testSaveDocument() {
DocumentManager manager = new DocumentManager();
        DocumentManager.Document  document = DocumentManager.Document.builder()
                .title("title")
                .content("content")
                .author(DocumentManager.Author.builder().id("1").name("Author1").build())
                .created(Instant.now())
                .build();

        DocumentManager.Document savedDocument = manager.save(document);

        assertNotNull(savedDocument.getId());
        assertEquals(document.getTitle(), savedDocument.getTitle());

    }


    @Test
    void testFindById() {
        DocumentManager manager = new DocumentManager();
        DocumentManager.Document document = manager.save(DocumentManager.Document.builder()
                .title("Test")
                .content("content")
                .author(DocumentManager.Author.builder()
                        .id("1")
                        .name("Author Name")
                        .build()
)
                .created(Instant.now())
                .build());

       Optional<DocumentManager.Document> foundDocument = manager.findById(document.getId());

       assertTrue(foundDocument.isPresent());
       assertEquals(document.getTitle(), foundDocument.get().getTitle());
    }


    @Test
    void testSearchByTitlePrefix() {
        DocumentManager manager = new DocumentManager();
        DocumentManager.Document doc1 = manager.save(DocumentManager.Document.builder().title("Title").content("content").author(DocumentManager.Author.builder().id("1").name("Author1").build()).created(Instant.now()).build());
        DocumentManager.Document doc2 =  manager.save(DocumentManager.Document.builder().title("Another").content("Another content").author(DocumentManager.Author.builder().id("2").name("Author2").build()).created(Instant.now()).build());

        DocumentManager.SearchRequest request = DocumentManager.SearchRequest.builder().titlePrefixes(List.of("Ti")).build();
        List<DocumentManager.Document> result = manager.search(request);

        assertEquals(1, result.size());
        assertEquals(result.get(0).getTitle(), doc1.getTitle());

    }
}
