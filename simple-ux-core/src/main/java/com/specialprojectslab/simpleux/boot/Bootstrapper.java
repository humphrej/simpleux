package com.specialprojectslab.simpleux.boot;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Ordering;
import com.google.inject.Inject;

import java.util.Set;

public class Bootstrapper {

    private final ImmutableSortedSet<BootStage> upOrderedStages;
    private final ImmutableSortedSet<BootStage> downOrderedStages;

    @Inject
    public Bootstrapper(Set<BootStage> stages) {

        Function<BootStage, Integer> getPriorityFunction = new Function<BootStage, Integer>() {
            public Integer apply(BootStage from) {
                return from.getPriority();
            }
        };

        Ordering<BootStage> upOrdering = Ordering.natural().onResultOf(getPriorityFunction);
        Ordering<BootStage> downOrdering = upOrdering.reverse();

        upOrderedStages = ImmutableSortedSet.orderedBy(
                upOrdering).addAll(stages).build();

        downOrderedStages = ImmutableSortedSet.orderedBy(
                downOrdering).addAll(stages).build();

    }

    public void boot() {
        for( BootStage l : upOrderedStages) {
            l.getService().start();
        }
    }

    public void shutdown() {
        for( BootStage l : downOrderedStages) {
            l.getService().stop();
        }
    }
}
