package com.epam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class RangeImpl implements Range {

    private long lowerBound;
    private long upperBound;

    public RangeImpl(long lowerBound, long upperBound) {
        if (lowerBound > upperBound) {
            throw new IllegalArgumentException("It's recommended to have upper bound greater than lower bound by math law.");
        }
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public boolean isBefore(Range otherRange) {
        if (otherRange.getLowerBound() > this.getUpperBound()) {
            return true;
        }
        return false;
    }

    public boolean isAfter(Range otherRange) {
        if (otherRange.getUpperBound() < this.getLowerBound()) {
            return true;
        }
        return false;
    }

    public boolean isConcurrent(Range otherRange) {
        if (this.isAfter(otherRange) || this.isBefore(otherRange)){
            return false;
        }
        return true;
    }

    public long getLowerBound() {
        return this.lowerBound;
    }

    public long getUpperBound() {
        return this.upperBound;
    }

    public boolean contains(long value) {
        return this.getLowerBound() <= value && this.getUpperBound() >= value;
    }

    public List<Long> asList() {
        if (upperBound == lowerBound) {
            return Collections.singletonList(upperBound);
        }
        List<Long> result = new ArrayList<Long>();
        for (long i = lowerBound; i < upperBound; i++) {
            result.add(i);
        }
        return result;
    }

    public Iterator<Long> asIterator() {
        return asList().iterator();
    }
}
