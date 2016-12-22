package com.epam.carrental.models.chart;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Component;
import schedule.model.AbstractScheduleModel;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class ScheduleModel extends AbstractScheduleModel<CarResource, CarUsage> {

    final Multimap<CarResource, CarUsage> assigments = ArrayListMultimap.create();

    private ZonedDateTime start;
    private ZonedDateTime end;

    public ScheduleModel() {
        this.start = ZonedDateTime.now();
        this.end = ZonedDateTime.now();
    }

    @Override
    public List<CarResource> getResources() {
        return new ArrayList<>(assigments.keySet());
    }

    @Override
    public Collection<CarUsage> getEventsAssignedTo(CarResource car) {
        return assigments.get(car);
    }

    @Override
    public ZonedDateTime getEnd() {
        return end;
    }

    @Override
    public ZonedDateTime getStart() {
        return start;
    }

    public void assign(CarResource car, CarUsage carUsage) {
        if (carUsage.getStart().isBefore(start)) {
            this.start = carUsage.getStart();
        }
        if (carUsage.getEnd().isAfter(end)) {
            this.end = carUsage.getEnd();
        }
        assigments.put(car, carUsage);
        fireDataChanged(true, true, true);
    }

    private void putAll(Multimap<CarResource, CarUsage> toAssign) {
        toAssign.entries()
                .forEach(carResourceCarUsageEntry -> assign(carResourceCarUsageEntry.getKey(), carResourceCarUsageEntry.getValue()));
    }

    public void setDataAndRefreshSchedule(Multimap<CarResource, CarUsage> assigments) {
        this.assigments.clear();
        putAll(assigments);
        this.assigments.putAll(assigments);
        fireDataChanged(true, true, false);
    }
}
