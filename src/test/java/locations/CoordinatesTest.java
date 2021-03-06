package locations;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CoordinatesTest {

    @Test
    public void testCoordinatesKnowRowAndColumn() {
        Coordinates coordinates = new Coordinates(1, 2);
        assertThat(coordinates.getRow(), equalTo(1));
        assertThat(coordinates.getColumn(), equalTo(2));
    }

    @Test
    public void testCoordinateEqualityIsBasedOnRowAndColumn() {
        Coordinates coordinates1 = new Coordinates(1, 1);
        Coordinates coordinates2 = new Coordinates(1, 2);
        Coordinates coordinates3 = new Coordinates(1, 1);

        assertThat(coordinates1, equalTo(coordinates1));
        assertThat(coordinates1, not(equalTo(coordinates2)));
        assertThat(coordinates1, equalTo(coordinates3));

        assertThat(coordinates3, equalTo(coordinates1));
    }

    @Test
    public void testSameCoordinatesHashTheSame(){
        Coordinates coordinates1 = new Coordinates(1, 1);
        Coordinates coordinates2 = new Coordinates(1, 2);
        Coordinates coordinates3 = new Coordinates(1, 1);

        Set<Coordinates> distinctCoordinates = new HashSet<Coordinates>();
        distinctCoordinates.add(coordinates1);
        distinctCoordinates.add(coordinates2);
        distinctCoordinates.add(coordinates3);

        assertThat(distinctCoordinates.size(), equalTo(2));
        assertThat(distinctCoordinates, hasItem(coordinates1));
        assertThat(distinctCoordinates, hasItem(coordinates2));
    }
    @Test
    public void testCalculatingPositionChangeForMovingOneColumnToTheLeft() {
        Coordinates start = new Coordinates(1, 1);
        Coordinates end = new Coordinates(1, 0);

        assertThat(start.to(end), equalTo(new MoveDelta(0, -1)));
    }

    @Test
    public void testCalculatingDeltasForMovingOneColumnToTheRight() {
        Coordinates start = new Coordinates(1, 1);
        Coordinates end = new Coordinates(1, 2);

        assertThat(start.to(end), equalTo(new MoveDelta(0, 1)));
    }

    @Test
    public void testCalculatingDeltasForMovingOneColumnToTheUp() {
        Coordinates start = new Coordinates(1, 0);
        Coordinates end = new Coordinates(0, 0);

        assertThat(start.to(end), equalTo(new MoveDelta(-1, 0)));
    }

    @Test
    public void testCalculatingDeltasForMovingOneColumnDown() {
        Coordinates start = new Coordinates(1, 0);
        Coordinates end = new Coordinates(2, 0);

        assertThat(start.to(end), equalTo(new MoveDelta(1, 0)));
    }

    @Test
    public void testCalculatingDeltasForMovingDiagonalUpLeft() {
        Coordinates start = new Coordinates(1, 1);
        Coordinates end = new Coordinates(0, 0);

        assertThat(start.to(end), equalTo(new MoveDelta(-1, -1)));
    }

    @Test
    public void testCalculatingDeltasForMovingDiagonalUpRight() {
        Coordinates start = new Coordinates(1, 1);
        Coordinates end = new Coordinates(0, 2);

        assertThat(start.to(end), equalTo(new MoveDelta(-1, 1)));
    }

    @Test
    public void testCalculatingDeltasForMovingDiagonalDownLeft() {
        Coordinates start = new Coordinates(1, 1);
        Coordinates end = new Coordinates(2, 0);

        assertThat(start.to(end), equalTo(new MoveDelta(1, -1)));
    }

    @Test
    public void testCalculatingDeltasForMovingDiagonalDownRight() {
        Coordinates start = new Coordinates(1, 1);
        Coordinates end = new Coordinates(2, 2);

        assertThat(start.to(end), equalTo(new MoveDelta(1, 1)));
    }

    @Test
    public void testEqualityIsBasedOnRowAndColumn() {
        Coordinates coordinates = new Coordinates(0, 0);
        Coordinates matchingCoordinates = new Coordinates(0, 0);
        Coordinates differentCoordinates = new Coordinates(0, 1);

        assertTrue(coordinates.equals(matchingCoordinates));
        assertFalse(coordinates.equals(differentCoordinates));
    }
}
