package com.epam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class JUnitTest {

    private Range range;
    private long lowerBound = 1;
    private long upperBound = 7;

    @BeforeEach
    public void initDriver() throws Throwable {
        range = new RangeImpl(lowerBound, upperBound);
    }

    @Test
    public void isBeforeReturnsTrueIfThisRangeIsBeforeOther() {
        Range otherRange = new RangeImpl(8, 9);

        assertThat(range.isBefore(otherRange), is(true));
    }

    @Test
    public void isBeforeReturnsFalseIfThisRangeIsAfterOther() {
        Range otherRange = new RangeImpl(5, 6);

        assertThat(range.isBefore(otherRange), is(false));
    }

    @Test
    public void isBeforeReturnsFalseIfThisRangeCrossedWithOther() {
        Range otherRange = new RangeImpl(5, 9);

        assertThat(range.isBefore(otherRange), is(false));
    }

    @Test
    public void isAfterReturnsTrueIfThisRangeIsAfterOther() {
        Range otherRange = new RangeImpl(-8, -5);

        assertThat(range.isAfter(otherRange), is(true));
    }

    @Test
    public void isAfterReturnsFalseIfThisRangeIsBeforeOther() {
        Range otherRange = new RangeImpl(10, 11);

        assertThat(range.isAfter(otherRange), is(false));
    }

    @Test
    public void isAfterReturnsFalseIfThisRangeCrossedWithOther() {
        Range otherRange = new RangeImpl(3, 8);

        assertThat(range.isBefore(otherRange), is(false));
    }

    @Test
    public void ConstructorThrowsIllegalArgumentExceptionIfUpperBoundIsLessThanLowerBound() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            public void execute() throws Throwable {
                new RangeImpl(9, 1);
            }
        });
    }

    @Test
    public void containsReturnsTrueIfRangeContainsValue() {
        assertThat(range.contains(5), is(true));
    }

    @Test
    public void containsReturnsFalseIfRangeNotContainsValue() {
        assertThat(range.contains(Long.MIN_VALUE), is(false));
    }


    @Test
    public void containsReturnsTrueIfNumberIsOnTheRangeLowerBound() {
        assertThat(range.contains(1), is(true));
    }

    @Test
    public void containsReturnsTrueIfNumberIsOnTheRangeUpperBound() {
        assertThat(range.contains(7), is(true));
    }

    @Test
    public void asListReturnListWithAllLongsThoseAreInRange() {
        List<Long> expected = new ArrayList<Long>();
        for (long i = lowerBound; i < upperBound; i++) {
            expected.add(i);
        }

        List<Long> actual = range.asList();

        assertThat(actual.equals(expected), is(true));
    }

    @Test
    public void asListReturnsSingletonListIfRangeIsConsistsOfOnlyOneElement() {
        Range singletonRange = new RangeImpl(5, 5);

        List<Long> expected = Collections.singletonList(5L);

        List<Long> actual = singletonRange.asList();

        assertThat(actual.equals(expected), is(true));
    }

    @Test
    public void asIteratorReturnsIteratorThatGoesThroughAllValues() {
        Iterator<Long> longIt = range.asIterator();
        for (long i = lowerBound; i < upperBound; i++) {
            assertThat(longIt.next(), is(i));
        }
    }


    @Test
    public void asIteratorReturnsIteratorForOneElementIfUpperBoundEqualsToLowerBound() {
        long rangeValue = 8;
        range = new RangeImpl(rangeValue, rangeValue);
        Iterator<Long> longIt = range.asIterator();
        assertThat(longIt.next(), is(rangeValue));
        assertThat(longIt.hasNext(), is(false));
    }

    @Test
    public void isConcurrentReturnsTrueIfRangesAreCrossed() {
        Range otherRange = new RangeImpl(lowerBound + 2, upperBound + 2);

        assertThat(range.isConcurrent(otherRange), is(true));
    }

    @Test
    public void isConcurrentReturnsTrueIfRangesAreInTouchOnLowerBound() {
        Range otherRange = new RangeImpl(lowerBound - 5, lowerBound);

        assertThat(range.isConcurrent(otherRange), is(true));
    }

    @Test
    public void isConcurrentReturnsTrueIfRangesAreInTouchOnUpperBound() {
        Range otherRange = new RangeImpl(upperBound, upperBound + 5);

        assertThat(range.isConcurrent(otherRange), is(true));
    }

    @Test
    public void isConcurrentReturnsTrueIfOneRangeIsInTheOther() {
        Range otherRange = new RangeImpl(lowerBound + 2, upperBound - 2);

        assertThat(range.isConcurrent(otherRange), is(true));
    }

    @Test
    public void isConcurrentReturnsFalseRangesHaveNoCommonPoints() {
        Range otherRange = new RangeImpl(upperBound + 2, upperBound + 7);

        assertThat(range.isConcurrent(otherRange), is(false));
    }
}
