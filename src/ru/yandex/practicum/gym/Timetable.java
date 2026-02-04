package ru.yandex.practicum.gym;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Timetable {

    private final Map<DayOfWeek, NavigableMap<TimeOfDay, List<TrainingSession>>> sessions = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        sessions.putIfAbsent(day, new TreeMap<>());
        NavigableMap<TimeOfDay, List<TrainingSession>> daySessions = sessions.get(day);

        daySessions.putIfAbsent(time, new ArrayList<>());
        daySessions.get(time).add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        NavigableMap<TimeOfDay, List<TrainingSession>> daySessions = sessions.get(dayOfWeek);
        if (daySessions == null) {
            return Collections.emptyList();
        }

        List<TrainingSession> result = new ArrayList<>();
        for (List<TrainingSession> timeSlots : daySessions.values()) {
            result.addAll(timeSlots);
        }
        return Collections.unmodifiableList(result);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        NavigableMap<TimeOfDay, List<TrainingSession>> daySessions = sessions.get(dayOfWeek);
        if (daySessions == null) {
            return Collections.emptyList();
        }

        List<TrainingSession> timeSessions = daySessions.get(timeOfDay);
        if (timeSessions == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(timeSessions);
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Long> counts = new HashMap<>();
        for (NavigableMap<TimeOfDay, List<TrainingSession>> daySessions : sessions.values()) {
            for (List<TrainingSession> timeSlots : daySessions.values()) {
                for (TrainingSession session : timeSlots) {
                    counts.merge(session.getCoach(), 1L, Long::sum);
                }
            }
        }

        List<CounterOfTrainings> result = new ArrayList<>();
        for (Map.Entry<Coach, Long> entry : counts.entrySet()) {
            result.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }
        Collections.sort(result);
        return result;
    }
}
