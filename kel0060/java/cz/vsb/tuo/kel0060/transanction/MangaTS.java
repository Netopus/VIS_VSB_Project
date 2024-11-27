package cz.vsb.tuo.kel0060.transanction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Entities.Author;
import Entities.Category;
import Entities.Language;
import Entities.Manga;
import Entities.AuthorManga;
import Entities.MangaCategory;
import cz.vsb.tuo.kel0060.repository.MangaRepository;
import cz.vsb.tuo.kel0060.repository.AuthorRepository;
import cz.vsb.tuo.kel0060.repository.CategoryRepository;
import cz.vsb.tuo.kel0060.repository.LanguageRepository;
import cz.vsb.tuo.kel0060.repository.AuthorMangaRepository;
import cz.vsb.tuo.kel0060.repository.MangaCategoryRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
public class MangaTS {

    @Autowired
    private MangaRepository mangaRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthorMangaRepository authorMangaRepository;

    @Autowired
    private MangaCategoryRepository mangaCategoryRepository;

    @Transactional
    public Manga addManga(String name, String description, int languageId, List<Integer> authorIds, List<Integer> categoryIds, int rating) {
        // 1. Add or retrieve language
        Language language = languageRepository.findById(languageId)
                .orElseThrow(() -> new IllegalArgumentException("Language not found"));
        

        // 2. Retrieve authors
        List<Author> authors = authorRepository.findAllById(authorIds);
        if (authors.size() != authorIds.size()) {
            throw new IllegalArgumentException("Some authors not found");
        }

        // 3. Retrieve categories
        List<Category> categories = categoryRepository.findAllById(categoryIds);
        if (categories.size() != categoryIds.size()) {
            throw new IllegalArgumentException("Some categories not found");
        }

        // 4. Create Manga entity
        Manga manga = new Manga();
        manga.setNames(name);
        manga.setDescriptions(description);
        manga.setLanguageId(languageId);
        manga.setRating(rating);
        manga.setLastUpdate(new Timestamp(System.currentTimeMillis()));

        // 5. Save Manga entity
        Manga savedManga = mangaRepository.save(manga);

        // 6. Link authors and categories by saving to the relationship tables
        authorIds.forEach(authorId -> {
            AuthorManga authorManga = new AuthorManga();
            authorManga.setMangaId(savedManga.getMangaId());
            authorManga.setAuthorId(authorId);
            authorManga.setLastUpdate(new Timestamp(System.currentTimeMillis()));
            authorMangaRepository.save(authorManga);
        });

        categoryIds.forEach(categoryId -> {
            MangaCategory mangaCategory = new MangaCategory();
            mangaCategory.setMangaId(savedManga.getMangaId());
            mangaCategory.setCategoryId(categoryId);
            mangaCategory.setLastUpdate(new Timestamp(System.currentTimeMillis()));
            mangaCategoryRepository.save(mangaCategory);
        });
        
        
        return manga;
    }
}
