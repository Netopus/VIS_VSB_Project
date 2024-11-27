package cz.vsb.tuo.kel0060.classes;

import Entities.Manga;

/**
 * Service Stub/Mock Object:
 * Defines the contract for a service that provides manga details.
 * Real implementations fetch data from a database, while mock implementations provide fake data for testing.
 */

public interface MangaServiceInterface {
	Manga getMangaDetails(String title);
}
