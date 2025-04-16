import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.DocumentManager;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DocumentManagerTest {

    private DocumentManager manager;
    private DocumentManager.Document doc1;
    private DocumentManager.Document doc2;

    @BeforeEach // Using fixture
    void setup() {
        manager = new DocumentManager();

        doc1 = manager.save(DocumentManager.Document.builder()
                .title("Title")
                .content("content")
                .author(DocumentManager.Author.builder().id("1").name("Author1").build())
                .created(Instant.now())
                .build());

        doc2 = manager.save(DocumentManager.Document.builder()
                .title("Another")
                .content("Another content")
                .author(DocumentManager.Author.builder().id("2").name("Author2").build())
                .created(Instant.now())
                .build());
    }

    @Test
    void testSaveDocument() {
        assertNotNull(doc1.getId());
        assertEquals("Title", doc1.getTitle());
    }

    @Test
    void testFindById() {
        Optional<DocumentManager.Document> foundDocument = manager.findById(doc1.getId());
        assertTrue(foundDocument.isPresent());
        assertEquals("Title", foundDocument.get().getTitle());
    }

    @Test
    void testSearchByTitlePrefix() {
        DocumentManager.SearchRequest request = DocumentManager.SearchRequest.builder()
                .titlePrefixes(List.of("Ti"))
                .build();

        List<DocumentManager.Document> result = manager.search(request);
        assertEquals(1, result.size());
        assertEquals("Title", result.get(0).getTitle());
    }
}
