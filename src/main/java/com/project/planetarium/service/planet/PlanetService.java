package com.project.planetarium.service.planet;

import java.util.List;

import com.project.planetarium.entities.Planet;

public interface PlanetService<T> {

    Planet createPlanet(Planet planet);

    Planet selectPlanet(T idOrName);

    List<Planet> selectAllPlanets();

    List<Planet> selectByOwner(int ownerId);

    Planet updatePlanet(Planet planet);

    String deletePlanet(T idOrName);

}
