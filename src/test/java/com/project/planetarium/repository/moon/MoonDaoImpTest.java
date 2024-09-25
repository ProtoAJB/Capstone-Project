package com.project.planetarium.repository.moon;

import com.project.Setup;
import com.project.planetarium.entities.Moon;
import com.project.planetarium.entities.Planet;
import com.project.planetarium.exceptions.MoonFail;
import com.project.planetarium.repository.moon.MoonDaoImp;

import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class MoonDaoImpTest {
    private Moon positiveMoon;
    private Moon existingMoon;
    private MoonDaoImp dao;
    private Planet planet;

    @BeforeClass
    public static void testDatabaseSetup() throws SQLException {
        Setup.getConnection();
    }

    @Before
    public void setUp() throws Exception {
        planet = new Planet(3, "jupiter", 3);
        Setup.resetTestDatabase();
        positiveMoon = new Moon(3, "pluto", planet.getPlanetId());
        existingMoon = new Moon(4, "lanris", planet.getPlanetId());
        dao = new MoonDaoImp();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createMoonPositive() {
        Optional<Moon> theNewMoon = dao.createMoon(positiveMoon);
        System.out.println(theNewMoon.isPresent());
        Assert.assertTrue(theNewMoon.isPresent());
        assertNotNull(theNewMoon.get().getMoonId());

    }

    @Test
    public void createMoonPositiveWithImg() throws IOException {
        File imageFile = new File("src/main/resources/images/galaxy-4.jpg");
        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
        String imageDataBase64 = Base64.getEncoder().encodeToString(imageBytes);

        Moon moon = new Moon(1, "moonWithImg", 3);
        moon.setImageData(imageDataBase64);
        Optional<Moon> theNewMoon = dao.createMoon(moon);
        System.out.println(theNewMoon.isPresent());
        Assert.assertTrue(theNewMoon.isPresent());
        assertNotNull(theNewMoon.get().getMoonId());

    }

    @Test(expected = MoonFail.class)
    public void createMoonFail() {
        Moon moon = new Moon(1, null, 3);
        dao.createMoon(moon);

    }

    @Test(expected = MoonFail.class)
    public void createDuplicateMoonFail() {
        Moon moon = new Moon(1, null, 3);
        dao.createMoon(moon);
        dao.createMoon(moon);

    }

    @Test
    public void readMoonByID() {
        Optional<Moon> theNewMoon = dao.createMoon(positiveMoon);
        Assert.assertTrue(theNewMoon.isPresent());

        Optional<Moon> readMoon = dao.readMoon(positiveMoon.getMoonId());
        assertTrue(readMoon.isPresent());
        assertEquals("pluto", readMoon.get().getMoonName());
    }

    @Test
    public void readMoonByMoonName() {
        Optional<Moon> theNewMoon = dao.createMoon(positiveMoon);
        Assert.assertTrue(theNewMoon.isPresent());

        Optional<Moon> readmoon = dao.readMoon(positiveMoon.getMoonName());
        assertTrue(readmoon.isPresent());
        assertEquals("pluto", readmoon.get().getMoonName());
    }

    @Test
    public void readMoonAllMoons() {

        dao.createMoon(positiveMoon);
        dao.createMoon(existingMoon);

        List<Moon> moons = dao.readAllMoons();
        assertEquals(6, moons.size());
        System.out.println(moons.get(1).getMoonName());

    }

    @Test
    public void readMoonsByPlanet() {
        dao.createMoon(positiveMoon);
        dao.createMoon(existingMoon);

        List<Moon> moons = dao.readMoonsByPlanet(3);
        assertEquals(3, moons.size());
    }

    // this functionality is not in the final product to my knowledge
    @Test
    public void updateMoon() {

        Moon updatedMoon = new Moon(1, "lunaUpdated", 1);
        Optional<Moon> theUpdate = dao.updateMoon(updatedMoon);
        assertTrue(theUpdate.isPresent());
        assertEquals("lunaUpdated", theUpdate.get().getMoonName());
    }

    @Test(expected = MoonFail.class)
    public void updateMoonFail() {

        Moon updatedMoon = new Moon(1, null, 1);
        Optional<Moon> theUpdate = dao.updateMoon(updatedMoon);
        assertTrue(theUpdate.isEmpty());
        assertEquals(null, theUpdate.get().getMoonName());
    }

    @Test
    public void deleteMoonByName() {
        boolean result = dao.deleteMoon(dao.readMoon(1).get().getMoonName());
        assertTrue(result);
    }

    @Test
    public void deleteMoonByID() {
        boolean result = dao.deleteMoon(dao.readMoon(1).get().getMoonId());
        assertTrue(result);
    }
}
