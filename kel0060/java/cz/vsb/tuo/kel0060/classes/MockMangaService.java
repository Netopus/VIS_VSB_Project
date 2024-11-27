package cz.vsb.tuo.kel0060.classes;

import Entities.Manga;

/**
 * Mock Implementation of MangaService:
 * Provides fake manga data for testing purposes without accessing the real database.
 */
public class MockMangaService implements MangaServiceInterface{
    @Override
    public Manga getMangaDetails(String title) {
        // Return a predefined object for testing
        return new Manga(0, 0, title, "TestAuthor", 0, null);
    }
}